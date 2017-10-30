package com.heking.qsy.yuying;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.content.Context;

public class YuYIng {
    private YUMessageData yymssage;

    public YUMessageData getYymssage() {
        return yymssage;
    }

    public void setYymssage(YUMessageData yymssage) {
        this.yymssage = yymssage;
    }

    public void setYuYing(Context context) {
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=57fda704");
        RecognizerDialog iatDialog = new RecognizerDialog(context, null);
        //2.设置听写参数，同上节
        //3.设置回调接口
        iatDialog.setListener(new RecognizerDialogListener() {

            public void onResult(RecognizerResult arg0, boolean arg1) {
                yymssage.onYYdata(JsonParser.parseIatResult(arg0.getResultString()));
            }

            public void onError(SpeechError arg0) {

            }
        });
        //4.开始听写
        iatDialog.show();

    }
}
