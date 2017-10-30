package com.heking.qsy.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.providers.ConnectionUtility;
import com.heking.qsy.providers.JSONdata;

public class Tool {

    private Context c;
    private LinearLayout layout;
    private int imgNub;
    private ArrayList<ImageView> list = new ArrayList<ImageView>();

    public Tool(Context c, LinearLayout layout, int imgNub) {
        this.c = c;
        this.layout = layout;
        this.imgNub = imgNub;
    }

    public static void ZXfile() {
        File file;
        File file2 = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            file = Environment.getExternalStorageDirectory();
            file2 = new File(file + "/PZhMessageList/");
            if (!file2.exists()) {
                file2.mkdirs();
            }
        }
        File ss = new File(file2 + "/LoginData.dll");
        ss.delete();
    }

    public static void ZXfile(String name) {
        File file;
        File file2 = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            file = Environment.getExternalStorageDirectory();
            file2 = new File(file + "/PZhMessageList/");
            if (!file2.exists()) {
                file2.mkdirs();
            }
        }
        File ss = new File(file2 + "/" + name);
        ss.delete();
    }

    public static File getfile(String name) {
        File file;
        File file2 = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            file = Environment.getExternalStorageDirectory();
            file2 = new File(file + "/PZhMessageList/");
            if (!file2.exists()) {
                file2.mkdirs();
            }
        }
        File ss = new File(file2 + "/" + name);
        return ss;
    }

    public void drawsImg() {
        layout.removeAllViews();
        LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        p.height = AppContext.ScrnParameter.screenWidth / 80;
        p.width = AppContext.ScrnParameter.screenWidth / 80;
        p.setMargins(5, 0, 0, 0);
        for (int i = 0; i < imgNub; i++) {
            ImageView img = new ImageView(c);
            img.setLayoutParams(p);
            if (i == 0) {
                img.setBackgroundResource(R.drawable.off_yuan);
            } else {
                img.setBackgroundResource(R.drawable.on_yuan);
            }
            layout.addView(img);
            list.add(img);
        }
    }

    public static void toActivity(Context context, Class<?> cla) {
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
    }

    public void setItem(int position) {
        for (int i = 0; i < list.size(); i++) {
            if (position == i) {
                list.get(i).setBackgroundResource(R.drawable.off_yuan);
            } else {
                list.get(i).setBackgroundResource(R.drawable.on_yuan);
            }
        }
    }

    /**
     * 将方形图片装换为圆形
     */
    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dstleft, dsttop, dstright, dstbottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dstleft = 0;
            dsttop = 0;
            dstright = width;
            dstbottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dstleft = 0;
            dsttop = 0;
            dstright = height;
            dstbottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dstleft, (int) dsttop, (int) dstright, (int) dstbottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            // 注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = context.getPackageManager().getPackageInfo(
                    "com.heking.qsy", 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "com.heking.qsy", 0).versionName;
        } catch (NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }

    /**
     * 得到一个请求，post或者是get请求
     *
     * @param url      请求地址
     * @param mess     请求方式
     * @param map      数据参集合
     * @param jsoNdata 回调接口
     * @param files    数据文件参数集合
     */
    public static void toHttpGEtandPost(final String url, final String mess, final Map<String, String> map,
                                        JSONdata jsoNdata, final Map<String, File> files) {
        new ConnectionUtility(url, mess, map, jsoNdata, files);
    }

    /**
     * 得到一个请求，post或者是get请求
     *
     * @param url      请求地址
     * @param mess     请求方式
     * @param map      数据参集合
     * @param jsoNdata 回调接口
     * @param files    数据文件参数集合
     * @param bool     true:登录 false:修改
     */
    public static void toHttpGEtandPost(final String url, final String mess,
                                        final Map<String, String> map, JSONdata jsoNdata,
                                        final Map<String, File> files, final boolean bool) {
        new ConnectionUtility(url, mess, map, jsoNdata, files, bool);
    }

    /**
     * 请求参数获取
     *
     * @param RequestMethod 企业状态
     * @param page          请求页
     * @param context
     * @param boo           是否请求全部，如果为true标识请求全部
     * @return 参数字符串
     */
    public static String getUrLString(String RequestMethod, int page,
                                      Context context, boolean boo) {
        String androidId = Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID);
        String mess = "RequestMethod=" + RequestMethod + "&Meid=" + androidId
                + "&Page=" + page + "&alls=" + boo;
        return mess;
    }

    public static void endActivity(final Context context) {
        ((TextView) ((Activity) context).findViewById(R.id.textview))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        ((Activity) context).finish();
                    }
                });
    }

    public static void changetitle(final Activity context, String str) {
        TextView title;
        title = (TextView) context.findViewById(R.id.textview01);
        if (title == null) {
            title = (TextView) context.findViewById(R.id.textview_01);
        }
        title.setText(str);
    }

    public static ArrayList<FirmTypeBean.Data> getData(
            ArrayList<FirmTypeBean.Data> datalist) {
        ArrayList<FirmTypeBean.Data> datas = new ArrayList<FirmTypeBean.Data>();
        for (FirmTypeBean.Data data : datalist) {
            if (data.getMonitors() != null && data.getMonitors().size() > 0) {
                data.setIoos(true);
            } else {
                data.setIoos(false);
            }

            if (data.getAnnualRating() != null
                    && data.getAnnualRating().size() > 0) {
                if (data.getAnnualRating().get(0).getRating() != null
                        && !data.getAnnualRating().get(0).getRating()
                        .trim().equals("")) {
                    if (data.getAnnualRating().get(0).getRating().trim()
                            .equals("优秀")) {
                        data.setmRating(1);
                    }
                    if (data.getAnnualRating().get(0).getRating().trim()
                            .equals("良好")) {
                        data.setmRating(2);
                    }
                    if (data.getAnnualRating().get(0).getRating().trim()
                            .equals("一般")) {
                        data.setmRating(3);
                    }
                    if (data.getAnnualRating().get(0).getRating().trim()
                            .equals("不予评级")) {
                        data.setmRating(4);
                    }
                }
            } else {
                data.setmRating(4);
            }
            datas.add(data);
        }
        return datas;

    }

}

