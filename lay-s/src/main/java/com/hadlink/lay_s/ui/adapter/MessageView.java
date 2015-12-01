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
import com.hadlink.lay_s.ui.pojo.model.WaitingAskBean;
import com.hadlink.library.adapter.views.BindableLayout;
import com.hadlink.library.widget.CircleImageView;
import com.hadlink.library.widget.badgeView.BadgeTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public class MessageView extends BindableLayout<WaitingAskBean> {
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

    @Override public void bind(WaitingAskBean message) {

        /**
         * view
         */
//        totalLayout.setVisibility(message.isFirst ? View.VISIBLE : View.GONE);
        moneyLayout.setVisibility(message.awardScore != 0 ? View.VISIBLE : View.GONE);
//        totalCount.setText(message.dataTotal + "");
        commonCount.setText(message.msgCount + "");
        score.setText(message.awardScore + "");
        String tabStr;
        if (message.awardScore != 0) {
            tabStr = String.valueOf(message.awardScore).length() > 2 ? "\u3000\u3000\u3000\u3000" : "\u3000\u3000\u3000";
        } else {
            tabStr = "";
        }
        content.setText(tabStr + message.questionContent);
        tag.setText(message.tagName);
        brand.setText(message.brandName);
        nickName.setText(message.nickName);
        tagLine.setVisibility(!TextUtils.isEmpty(message.brandName) ? View.VISIBLE : View.GONE);
        time.setText(message.createTime);
        Glide.with(mContext)
                .load(message.avatarUrl)
                .placeholder(R.mipmap.ic_my_head_image)
                .error(R.mipmap.ic_my_head_image)
                .crossFade()
                .into(head);
        gender.setImageResource(message.gender == 1 ? R.mipmap.male : message.gender == 2 ? R.mipmap.femal : 0);
        /**
         * click
         */
        rightNowAnswer.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                notifyItemAction(R.id.rightNowAnswer, message, v);
            }
        });
    }
}
