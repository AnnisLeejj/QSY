package com.heking.qsy.providers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.heking.qsy.AppContext;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ConnectionUtility {
    private JSONdata jsoNdata;
    private Thread thread;
    private MyHandler handler;

    public ConnectionUtility(final String url, final String mess,
                             final Map<String, String> map, JSONdata jsoNdata,
                             final Map<String, File> files) {
        // "http://map.baidu.com/detail?qt=ninf&uid=d219bb1d51c2488bf40e77be"
        Log.v("ConnectionUtility", url);
        this.jsoNdata = jsoNdata;

        handler = new MyHandler();
        if (mess.equals("GET")) {
            toUtilityGet(url);
        }
        if (mess.equals("POST")) {
            new Thread(new Runnable() {
                public void run() {
                    Bundle bundle = new Bundle();
                    Message mMessage = handler.obtainMessage();
//					bundle.putString("json",submitPostData(url, map, "UTF-8"));
                    bundle.putString("json", post(url, map, files));
                    mMessage.setData(bundle);
                    handler.dispatchMessage(mMessage);
                }
            }).start();
        }
    }

    public ConnectionUtility(final String url, final String mess,
                             final Map<String, String> map, JSONdata jsoNdata,
                             final Map<String, File> files, final boolean bool) {
        // "http://map.baidu.com/detail?qt=ninf&uid=d219bb1d51c2488bf40e77be"
        this.jsoNdata = jsoNdata;

        handler = new MyHandler();
        if (mess.equals("GET")) {
            toUtilityGet(url);
        }
        if (mess.equals("POST")) {
            new Thread(new Runnable() {


                public void run() {
                    Bundle bundle = new Bundle();
                    Message mMessage = handler.obtainMessage();
//					bundle.putString("json",submitPostData(url, map, "UTF-8"));
                    bundle.putString("json", post(url, map, files, bool));
                    mMessage.setData(bundle);
                    handler.dispatchMessage(mMessage);
                }
            }).start();
        }
    }

    /**
     * GET 请求
     *
     * @param url
     * @return
     */
    private void toUtilityGet(final String url) {
        new Thread(new Runnable() {


            public void run() {
                String message = null;
                StringBuffer data = null;
                try {
                    URL mUrl = new URL(url);
                    HttpURLConnection mConnection = (HttpURLConnection) mUrl
                            .openConnection();
                    mConnection.setConnectTimeout(10000);
                    mConnection.setRequestMethod("GET");
                    mConnection.setRequestProperty("Cookie", AppContext.Cookie.Cookie);
                    mConnection.connect();
                    int responseCode = mConnection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.i("ConnectionUtility", "连接成功");
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(mConnection
                                        .getInputStream(), "UTF-8"));
                        data = new StringBuffer();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            data.append(line);
                        }
                    } else {
                        Log.i("ConnectionUtility", "连接失败1");
                        message = "连接失败";
                    }
                } catch (Exception e) {
                    Log.i("ConnectionUtility", "连接失败2");
                    e.printStackTrace();
                    message = "连接失败";
                }
                if (data != null) {
                    Log.i("ConnectionUtility", "ConnectionUtility+json" + data.toString());
                    message = data.toString();
                }

                Bundle bundle = new Bundle();
                Message mMessage = handler.obtainMessage();
                bundle.putString("json", message);
                mMessage.setData(bundle);
                handler.sendMessage(mMessage);
            }

        }).start();
    }

    private class MyHandler extends Handler {
        // 必须重写这个方法，用于处理message
        public void handleMessage(Message msg) {
            // 这里用于更新UI
            Bundle bundle = msg.getData();
            jsoNdata.httpResponse(bundle.getString("json"));
        }
    }

    public String post(String actionUrl, Map<String, String> params,
                       Map<String, File> files) {
        try {
            String BOUNDARY = java.util.UUID.randomUUID().toString();
            String PREFIX = "--", LINEND = "\r\n";
            String MULTIPART_FROM_DATA = "multipart/form-data";
            String CHARSET = "UTF-8";
            URL uri = new URL(actionUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
            httpURLConnection.setReadTimeout(60 * 1000);
            httpURLConnection.setDoInput(true);// 允许输入
            httpURLConnection.setDoOutput(true);// 允许输出
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST"); // Post方式
            httpURLConnection.setRequestProperty("connection", "keep-alive");
            httpURLConnection.setRequestProperty("Charsert", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                    + ";boundary=" + BOUNDARY);

            // 首先组拼文本类型的参数
            StringBuilder sb = new StringBuilder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINEND);
                    sb.append("Content-Disposition: form-data; name=\""
                            + entry.getKey() + "\"" + LINEND);
                    sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                    sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                    sb.append(LINEND);
                    sb.append(entry.getValue());
                    sb.append(LINEND);
                }
            }
            DataOutputStream outStream = new DataOutputStream(httpURLConnection.getOutputStream());
            outStream.write(sb.toString().getBytes());

            // 发送文件数据
            if (files != null) {
                for (Map.Entry<String, File> file : files.entrySet()) {
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1
                            .append("Content-Disposition: form-data; name=\"file\"; filename=\""
                                    + file.getKey() + "\"" + LINEND);
                    sb1.append("Content-Type: application/octet-stream; charset="
                            + CHARSET + LINEND);
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes());
                    InputStream is = new FileInputStream(file.getValue());
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }

                    is.close();
                    outStream.write(LINEND.getBytes());
                }
            }
            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();

            // 得到响应码
            int res = httpURLConnection.getResponseCode();


            InputStream inptStream = httpURLConnection.getInputStream();
            outStream.close();

            return dealResponseResult(inptStream);
        } catch (IOException e) {
            return "连接失败";
        }
    }

    public String post(String actionUrl, Map<String, String> params, Map<String, File> files, boolean bool) {
        try {
            String BOUNDARY = java.util.UUID.randomUUID().toString();
            String PREFIX = "--", LINEND = "\r\n";
            String MULTIPART_FROM_DATA = "multipart/form-data";
            String CHARSET = "UTF-8";
            URL uri = new URL(actionUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
            httpURLConnection.setReadTimeout(60 * 1000);
            httpURLConnection.setDoInput(true);// 允许输入
            httpURLConnection.setDoOutput(true);// 允许输出
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST"); // Post方式
            httpURLConnection.setRequestProperty("Cookie", AppContext.Cookie.Cookie);
            httpURLConnection.setRequestProperty("connection", "keep-alive");
            httpURLConnection.setRequestProperty("Charsert", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                    + ";boundary=" + BOUNDARY);
            // 首先组拼文本类型的参数
            StringBuilder sb = new StringBuilder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINEND);
                    sb.append("Content-Disposition: form-data; name=\""
                            + entry.getKey() + "\"" + LINEND);
                    sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                    sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                    sb.append(LINEND);
                    sb.append(entry.getValue());
                    sb.append(LINEND);
                }
            }
            DataOutputStream outStream = new DataOutputStream(httpURLConnection.getOutputStream());
            outStream.write(sb.toString().getBytes());

            // 发送文件数据
            if (files != null) {
                for (Map.Entry<String, File> file : files.entrySet()) {
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                            + file.getKey() + "\"" + LINEND);
                    sb1.append("Content-Type: application/octet-stream; charset="
                            + CHARSET + LINEND);
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes());
                    InputStream is = new FileInputStream(file.getValue());
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }

                    is.close();
                    outStream.write(LINEND.getBytes());
                }
            }
            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();

            // 得到响应码
            int res = httpURLConnection.getResponseCode();


            InputStream inptStream = httpURLConnection.getInputStream();
            if (bool) {
                getCookie(httpURLConnection);
            }
            outStream.close();

            return dealResponseResult(inptStream);
        } catch (IOException e) {
            Log.d("异常数据:", e.toString());
            return "连接失败";
        }
    }


    /*
     * Function : 处理服务器的响应结果（将输入流转化成字符串） Param : inputStream服务器的响应输入流
     */
    private String dealResponseResult(InputStream inputStream) {
        String resultData = null; // 存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    private void getCookie(HttpURLConnection conn) {
        //获取cookie
        String firstCookie;
        Map<String, List<String>> map = conn.getHeaderFields();
        if (map != null) {
            Set<String> set = map.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                if (key != null && key.equals("Set-Cookie")) {
                    List<String> list = map.get(key);
                    StringBuilder builder = new StringBuilder();
                    //		builder.append("eos_style_cookie=default; ");
                    for (String str : list) {
                        builder.append(str).toString();
                    }
                    AppContext.Cookie.Cookie = builder.toString().split(";")[0] + ";";
                }

            }
        }
    }

}
