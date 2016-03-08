/*
 *    Copyright (c) 2016, lyao. lomoliger@hotmail.com
 *
 *     Part of the code from the open source community,
 *     thanks stackOverflow & gitHub .
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.hadlink.lay_s.delegate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.hadlink.lay_s.R;
import com.hadlink.lay_s.datamanager.bean.ImageDetail;
import com.hadlink.lay_s.datamanager.net.response.ImageListResponse;
import com.hadlink.lay_s.presenter.ImageDetailPresenter;
import com.hadlink.lay_s.utils.UriHelper;
import com.hadlink.library.base.view.AppDelegate;
import com.hadlink.library.widget.XSwipeRefreshLayout;
import com.hadlink.library.widget.pla.PLAAdapterView;
import com.hadlink.library.widget.pla.PLAImageView;
import com.hadlink.library.widget.pla.PLALoadMoreListView;
import com.hadlink.rvhelpperlib.adapter.AdapterViewAdapter;
import com.hadlink.rvhelpperlib.adapter.ViewHolderHelper;
import com.liulishuo.qiniuimageloader.utils.PicassoLoader;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class ImageListDelegate extends AppDelegate {
    public static final int OFFSET_PAGE = UriHelper.PAGE_LIMIT;
    public static final int REFRESH_START_PAGE = 0;
    public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;

    LoadingCallBack loadingCallBack;
    XSwipeRefreshLayout xSwipeRefreshLayout;
    PLALoadMoreListView plaLoadMoreListView;
    int currentLoadNum = REFRESH_START_PAGE;
    AdapterViewAdapter<ImageDetail> adapter;
    Context ctx;

    @Override public int getRootLayoutId() {
        return R.layout.delegate_image_list;
    }

    @Override public void initWidget() {
        xSwipeRefreshLayout = get(R.id.fragment_images_list_swipe_layout);
        plaLoadMoreListView = get(R.id.fragment_images_list_list_view);
        ctx = plaLoadMoreListView.getContext();

        //config view
        xSwipeRefreshLayout.setColorSchemeColors(
                ctx.getResources().getColor(R.color.gplus_color_1),
                ctx.getResources().getColor(R.color.gplus_color_2),
                ctx.getResources().getColor(R.color.gplus_color_3),
                ctx.getResources().getColor(R.color.gplus_color_4));
        xSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                if (loadingCallBack != null) loadingCallBack.onRefresh();
            }
        });
        plaLoadMoreListView.setOnLoadMoreListener(new PLALoadMoreListView.OnLoadMoreListener() {
            @Override public void onLoadMore() {
                if (loadingCallBack != null) loadingCallBack.onLoadMore();
            }
        });
        plaLoadMoreListView.setOnItemClickListener(new PLAAdapterView.OnItemClickListener() {
            @Override public void onItemClick(PLAAdapterView<?> parent, View view, int position, long id) {
                Rect frame = new Rect();
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;

                int[] location = new int[2];
                view.getLocationOnScreen(location);
                location[1] += statusBarHeight;

                int width = view.getWidth();
                int height = view.getHeight();

                ImageDetail item = adapter.getItem(position);

                Bundle extras = new Bundle();
                extras.putString(ImageDetailPresenter.INTENT_IMAGE_URL_TAG, item.imageUrl);
                extras.putInt(ImageDetailPresenter.INTENT_IMAGE_X_TAG, location[0]);
                extras.putInt(ImageDetailPresenter.INTENT_IMAGE_Y_TAG, location[1]);
                extras.putInt(ImageDetailPresenter.INTENT_IMAGE_W_TAG, width);
                extras.putInt(ImageDetailPresenter.INTENT_IMAGE_H_TAG, height);

                Intent intent = new Intent(getActivity(), ImageDetailPresenter.class);
                intent.putExtras(extras);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        //adapter
        adapter = new AdapterViewAdapter<ImageDetail>(ctx, R.layout.item_image) {
            @Override protected void fillData(ViewHolderHelper viewHolderHelper, int position, ImageDetail model) {
                PLAImageView iv = viewHolderHelper.getView(R.id.iv);
                if (!TextUtils.isEmpty(model.thumbnailUrl)) {
                    PicassoLoader.createLoader(iv, model.thumbnailUrl)
                            .attach();
                    iv.setImageWidth(model.thumbnailWidth);
                    iv.setImageHeight(model.thumbnailHeight);
                }
            }
        };
        plaLoadMoreListView.setAdapter(adapter);
    }

    @Override public View getLoadingTargetView() {
        return get(R.id.fragment_images_list_swipe_layout);
    }

    public void setDatas(boolean refresh, ImageListResponse<ImageDetail> datas) {
        if (refresh) {
            adapter.setDatas(datas.getResult());
            xSwipeRefreshLayout.setRefreshing(false);
        } else {
            adapter.addMoreDatas(datas.getResult());
            plaLoadMoreListView.onLoadMoreComplete();
        }

        //check load more enable
        if (UriHelper.getInstance().calculateTotalPages(datas.totalNum) > currentLoadNum) {
            plaLoadMoreListView.setCanLoadMore(true);
        } else {
            plaLoadMoreListView.setCanLoadMore(false);
        }
    }

    public int getCurrentPageNum(boolean refresh) {
        if (refresh) {
            currentLoadNum = REFRESH_START_PAGE;
        } else {
            currentLoadNum++;
        }
        return currentLoadNum * OFFSET_PAGE;
    }

    public void setError(boolean refresh) {
        xSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override public void run() {
                xSwipeRefreshLayout.setRefreshing(false);
                plaLoadMoreListView.onLoadMoreComplete();
            }
        }, PAGE_LAZY_LOAD_DELAY_TIME_MS);

        //reset currentPage
        if (!refresh) {
            currentLoadNum--;
        }
    }

    public void setCallBack(LoadingCallBack loadingCallBack) {
        this.loadingCallBack = loadingCallBack;
    }

    public interface LoadingCallBack {
        void onRefresh();

        void onLoadMore();
    }
}
