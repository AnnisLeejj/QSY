package com.heking.qsy.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * 将assets文件夹下的数据库写入SD卡中
 * 
 * @author Dave
 */
public class WriteToSD {
	private static File file;
	private static File file2 = null;

	private static void setFileString() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			file = Environment.getExternalStorageDirectory();
			file2 = new File(file + "/PZhMessageList/");
			if (!file2.exists()) {
				file2.mkdirs();
			}
		}
	}

	public static void write(Context context, String fileName) {
		setFileString();
		InputStream inputStream;
		try {
			inputStream = context.getResources().getAssets().open(fileName);

			FileOutputStream fileOutputStream = new FileOutputStream(file2 + "/" + fileName);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, count);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			inputStream.close();
		} catch (IOException e) {
			Log.d("数据测试", ">>>>>>>>>>>>>>>>>>>>>>>>>");
			e.printStackTrace();
		}
	}

}