package com.heking.qsy.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.heking.qsy.R;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.util.MyTextUtils;

import butterknife.ButterKnife;

/**
 * 项目重构(长期进行) creat by Lee
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * set activity title ,and do finish case showFinish==true;
     *
     * @param title
     * @param showFinish true,show the button to finishing activity;false,invisible the button.
     */
    public void setTitle(String title, boolean showFinish) {
        if (showFinish) {
            findViewById(R.id.textview)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            finish();
                        }
                    });
        }
        if (MyTextUtils.isEmpty(title)) {
            ((TextView) findViewById(R.id.textview_01)).setText(title);
        }
    }

    public void showToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    WaitDialog waitDialog;

    public void showWaitDialog() {
        showWaitDialog("正在加载...");
    }

    public void showWaitDialog(@NonNull String message) {
        if (waitDialog == null)
            waitDialog = new WaitDialog(this);
        waitDialog.setContent(message);
        waitDialog.show();
    }

    public void dismissWaitDialog() {
        if (waitDialog != null && waitDialog.isShowing())
            waitDialog.dismiss();
    }
}
