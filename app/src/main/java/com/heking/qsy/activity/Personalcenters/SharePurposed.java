package com.heking.qsy.activity.Personalcenters;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.heking.qsy.R;
import com.heking.qsy.util.PopDialogView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SharePurposed implements OnClickListener {
    private TextView WeixinFriend, QQFriend, WeiBo;
    private View mShareView;
    private PopDialogView dialogView;
    private Context context;
    private int STATE;
    private String html;
    private View view;
    private int height;

    /**
     * 分享
     */
    public SharePurposed(Context context, int state) {
        this.context = context;
        this.STATE = state;
        iniShareView(context);
        DialogSet(context);
        dialogView.show();
    }

    /**
     * 分享
     */
    public SharePurposed(Context context, String html, int state) {
        this.context = context;
        this.STATE = state;
        this.html = html;
        iniShareView(context);
        DialogSet(context);
        dialogView.show();
    }

    /**
     * 分享
     */
    public SharePurposed(Context context, View view, int height, int state) {
        this.context = context;
        this.STATE = state;
        this.view = view;
        this.height = height;
        iniShareView(context);
        DialogSet(context);
        dialogView.show();
    }

    /**
     * 分享
     */
    public SharePurposed(Context context, String Text) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, Text + "\n\t\t\t\t\t【来自犍食药的分享】");
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "分享"));
    }

    private void iniShareView(Context context) {
        dialogView = new PopDialogView(context, true);

        mShareView = LayoutInflater.from(context).inflate(R.layout.share_purposed, null);

        WeixinFriend = (TextView) mShareView.findViewById(R.id.share_sdk_weixinhaoyou_view);
        QQFriend = (TextView) mShareView.findViewById(R.id.share_sdk_qq_view);
        WeiBo = (TextView) mShareView.findViewById(R.id.share_sdk_xinlangweibo_view);
        dialogView.setContentView(mShareView);
        WeixinFriend.setOnClickListener(this);
        QQFriend.setOnClickListener(this);
        WeiBo.setOnClickListener(this);

    }

    /**
     * 设置dialogView铺满横屏
     */

    private void DialogSet(Context context) {
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogView.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.share_sdk_qq_view:
                if (STATE == 0) {
                    ShareSDK.initSDK(context);
                    OnekeyShare oksqq = new OnekeyShare();
                    oksqq.setPlatform(QQ.NAME);
                    oksqq.setTitleUrl("http://117.173.38.55:84/File/Home/DownFile/92ed0999-a47a-4d0a-9956-518739443e51");
                    oksqq.setText("药安食美  健康犍为");
                    oksqq.show(context);
                    //TID:100 bug:1202 分享至QQ时页面下方显示一个黑色框，详见附件
                    //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
                if (STATE == 2) {
                    ShareSDK.initSDK(context);
                    OnekeyShare oksqq = new OnekeyShare();
                    oksqq.setPlatform(QQ.NAME);
                    oksqq.setViewToShare(view, height);
                    oksqq.show(context);
                }
                break;

            case R.id.share_sdk_weixinhaoyou_view:
                if (STATE == 0) {
                    ShareSDK.initSDK(context);
                    OnekeyShare oksweixinhaoyou = new OnekeyShare();
                    oksweixinhaoyou.setPlatform(Wechat.NAME);
                    oksweixinhaoyou.setText("药安食美 健康犍为 \n http://117.173.38.55:84/File/Home/DownFile/92ed0999-a47a-4d0a-9956-518739443e51");
                    oksweixinhaoyou.show(context);
                }
                if (STATE == 2) {
                    ShareSDK.initSDK(context);
                    OnekeyShare oksweixinhaoyou = new OnekeyShare();
                    oksweixinhaoyou.setPlatform(Wechat.NAME);
                    oksweixinhaoyou.setViewToShare(view, height);
                    oksweixinhaoyou.show(context);
                }
                break;
            case R.id.share_sdk_xinlangweibo_view:
                if (STATE == 0) {
                    ShareSDK.initSDK(context);
                    OnekeyShare oksxinlangweibo = new OnekeyShare();
                    oksxinlangweibo.setPlatform(SinaWeibo.NAME);
                    oksxinlangweibo.setText("药安食美 健康犍为 \n http://117.173.38.55:84/File/Home/DownFile/92ed0999-a47a-4d0a-9956-518739443e51");
                    oksxinlangweibo.show(context);
                }
                if (STATE == 2) {
                    ShareSDK.initSDK(context);
                    OnekeyShare oksxinlangweibo = new OnekeyShare();
                    oksxinlangweibo.setPlatform(SinaWeibo.NAME);
                    oksxinlangweibo.setViewToShare(view, height);
                    oksxinlangweibo.show(context);
                }
                break;
        }
    }
}
