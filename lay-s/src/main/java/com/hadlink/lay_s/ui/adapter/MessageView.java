package com.hadlink.lay_s.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hadlink.lay_s.R;
import com.hadlink.lay_s.ui.datamanager.bean.WaitingAskBean;
import com.hadlink.library.adapter.utils.ViewEventListener;
import com.hadlink.library.adapter.views.BindableFrameLayout;
import com.hadlink.library.widget.CircleImageView;
import com.hadlink.library.widget.badgeView.BadgeTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public class MessageView extends BindableFrameLayout<WaitingAskBean> {
    @Bind(R.id.totalCountDes1) TextView totalCountDes1;
    @Bind(R.id.totalCount) TextView totalCount;
    @Bind(R.id.totalCountDes2) TextView totalCountDes2;
    @Bind(R.id.totalLayout) LinearLayout totalLayout;
    @Bind(R.id.moneyIcon) ImageView moneyIcon;
    @Bind(R.id.score) TextView score;
    @Bind(R.id.moneyLayout) RelativeLayout moneyLayout;
    @Bind(R.id.content) TextView content;
    @Bind(R.id.tag) TextView tag;
    @Bind(R.id.tagLine) TextView tagLine;
    @Bind(R.id.brand) TextView brand;
    @Bind(R.id.time) TextView time;
    @Bind(R.id.head) CircleImageView head;
    @Bind(R.id.badge) BadgeTextView badge;
    @Bind(R.id.headLayout) FrameLayout headLayout;
    @Bind(R.id.nickName) TextView nickName;
    @Bind(R.id.gender) ImageView gender;
    @Bind(R.id.rightNowAnswer) TextView rightNowAnswer;
    @Bind(R.id.commonCount) TextView commonCount;
    @Bind(R.id.contentLayout) LinearLayout contentLayout;
    @Bind(R.id.bottom_line) TextView bottomLine;

    public MessageView(Context context) {
        super(context);
    }

    @Override public int getLayoutId() {
        return R.layout.item_wating_ask;
    }

    @Override public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override public void bind(WaitingAskBean item) {
        /**
         * view
         */
//        totalLayout.setVisibility(message.isFirst ? View.VISIBLE : View.GONE);
        moneyLayout.setVisibility(item.awardScore != 0 ? View.VISIBLE : View.GONE);
//        totalCount.setText(message.dataTotal + "");
        commonCount.setText(item.msgCount + "");
        score.setText(item.awardScore + "");
        String tabStr;
        if (item.awardScore != 0) {
            tabStr = String.valueOf(item.awardScore).length() > 2 ? "\u3000\u3000\u3000\u3000" : "\u3000\u3000\u3000";
        } else {
            tabStr = "";
        }
        content.setText(tabStr + item.questionContent);
        tag.setText(item.tagName);
        brand.setText(item.brandName);
        nickName.setText(item.nickName);
        tagLine.setVisibility(!TextUtils.isEmpty(item.brandName) ? View.VISIBLE : View.GONE);
        time.setText(item.createTime);
        Glide.with(context)
                .load(item.avatarUrl)
                .placeholder(R.mipmap.ic_my_head_image)
                .error(R.mipmap.ic_my_head_image)
                .crossFade()
                .into(head);
        gender.setImageResource(item.gender == 1 ? R.mipmap.male : item.gender == 2 ? R.mipmap.femal : 0);
        /**
         * click
         */
        rightNowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                notifyItemAction(R.id.rightNowAnswer, item, v);
            }
        });
    }


    @Override public void setViewEventListener(ViewEventListener<WaitingAskBean> viewEventListener) {

    }

    @Override public ViewEventListener<WaitingAskBean> getViewEventListener() {
        return null;
    }

    @Override public void notifyItemAction(int actionId, WaitingAskBean theItem, View view) {

    }
}
