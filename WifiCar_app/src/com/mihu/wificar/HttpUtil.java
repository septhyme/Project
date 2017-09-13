package com.mihu.wificar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;

public class HttpUtil {
	public static String down = "http://bcs.91.com/pcsuite-dev/apk/9c5e9a93809424be8091570f4eb90620.apk";
	public static String zhaoni = "http://storage1.pgyer.com/M02/12/AB/wKgB7VU_oLWANpztAEfkko-RfPY123.apk?auth_key=f371979d790cda1765fd6f1b02573d98-1430842924";

	/** http下载 */
	public static boolean httpDownload(String httpUrl, String apkName) {
		File file = getSDPath();
		if (file == null) {
			return false;
		}
		String path = file.getAbsolutePath() + File.separator + apkName+".apk";
		// 下载网络文件
		int bytesum = 0;
		int byteread = 0;

		URL url = null;
		try {
			url = new URL(down);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		try {
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(path);

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static File getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {
			sdDir = Environment.getDownloadCacheDirectory();
		}
		return sdDir;

	}
}
