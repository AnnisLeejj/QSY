package com.heking.qsy.providers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpImage {
	 private final static String TAG = "IcsTestActivity";  
	    private final  String ALBUM_PATH;//= Environment.getExternalStorageDirectory() + "/download_test/";  
	    private ProgressDialog mSaveDialog = null;  
	    private Bitmap mBitmap;  
	    private String mFileName;  
	    private String mSaveMessage;
	    private ImageBitmap mImageBitmap;
	    private String path;
	    /**
	     * 异步加载图片
	     * @param mImageBitmap
	     * @param path
	     */
	public HttpImage(ImageBitmap mImageBitmap,String path,String ALBUM_PATH){
		this.path=path;
		this.mImageBitmap=mImageBitmap;
		this.ALBUM_PATH=ALBUM_PATH;
		if(ALBUM_PATH!=null){
			new Thread(connectNet).start();  
			new Thread(saveFileRunnable).start(); 
		}else{
	    new Thread(connectNet).start();  
		}
	}
	
	
	
    /** 
     *从网络获取图像 
     * @param path The path of image 
     * @return byte[] 
     * @throws Exception 
     */  
    public byte[] getImage(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(10 * 1000);  
        conn.setRequestMethod("GET");  
        InputStream inStream = conn.getInputStream();  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return readStream(inStream);  
        }  
        return null;  
    }  
  
    /** 
     * 从网络获取图像 
     * @param path The path of image 
     * @return InputStream 
     * @throws Exception 
     */  
    public InputStream getImageStream(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(10 * 1000);  
        conn.setRequestMethod("GET");  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return conn.getInputStream();  
        }  
        return null;  
    }  
    /** 
     * 得到的数据流
     * @param inStream 
     * @return byte[] 
     * @throws Exception 
     */  
    public static byte[] readStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1){  
            outStream.write(buffer, 0, len);  
        }  
        outStream.close();  
        inStream.close();  
        return outStream.toByteArray();  
    }  
  
    /** 
     * 保存文件 
     * @param bm 
     * @param fileName 
     * @throws IOException 
     */  
    public void saveFile(Bitmap bm, String fileName) throws IOException {  
        File dirFile = new File(ALBUM_PATH);  
        if(!dirFile.exists()){  
            dirFile.mkdir();  
        }  
        File myCaptureFile = new File(ALBUM_PATH + fileName);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
        bos.flush();  
        bos.close();  
    }  
  
    private Runnable saveFileRunnable = new Runnable(){  
        @Override  
        public void run() {  
            try {  
                saveFile(mBitmap, mFileName);  
                mSaveMessage = "图片保存成功！";  
            } catch (IOException e) {  
                mSaveMessage = "图片保存失败！";  
                e.printStackTrace();  
            }  
            messageHandler.sendMessage(messageHandler.obtainMessage());  
        }  
  
    };  
  
    private Handler messageHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            mSaveDialog.dismiss();  
            Log.d(TAG, mSaveMessage);  
        }  
    };  
  
    /* 
     * 连接网络 
     * 由于在4.0中不允许在主线程中访问网络，所以需要在子线程中访问 
     */  
    private Runnable connectNet= new Runnable(){  
        @Override  
        public void run() {  
            try {  
  
                //以下是取得图片的两种方法  
                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap  
                byte[] data = getImage(path);  
                if(data!=null){  
                    mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap  
                }
                ////////////////////////////////////////////////////////  
  
                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/  
                // mBitmap = BitmapFactory.decodeStream(getImageStream(path));  
                //********************************************************************/  
           
                // 发送消息，通知handler在主线程中更新UI  
                connectHanlder.sendEmptyMessage(0);  
                Log.d(TAG, "set image ...");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
  
        }  
  
    };  
  
    private Handler connectHanlder = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            Log.d(TAG, "display image");  
            // 更新UI，显示图片  
            if (mBitmap != null) {  
            	mImageBitmap.toBitmap(mBitmap);
            }  
        }  
    }; 
}
