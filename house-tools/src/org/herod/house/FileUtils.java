/*
 * 香港摩比科技有限公司 版权所有
 *
 * www.mobi-inf.com
 */
package org.herod.house;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * 文件操作工具集合.
 * 
 * @author Xiong Zhijun
 * 
 */
public class FileUtils {

	/**
	 * 根据图片url获取本地存放的路径。
	 * 
	 * @param context
	 * @param imageUrl
	 * @return
	 */
	public static File getLocalPictureFile(Context context, String imageUrl) {
		if (imageUrl == null) {
			return new File(StringUtils.EMPTY);
		}
		File pictureFilesDir = getLocalStorageDir(context,
				Environment.DIRECTORY_PICTURES);
		return new File(pictureFilesDir, imageUrl.replace("/", "_").replace(
				"\\", "_"));
	}

	/**
	 * 获取文件类型本地文件存储目录。
	 * 
	 * @param context
	 * @param fileType
	 * @return
	 */
	public static File getLocalStorageDir(Context context, String fileType) {
		String sdStatus = Environment.getExternalStorageState();
		if (sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(fileType);
		} else {
			return context.getDir(fileType, Context.MODE_WORLD_WRITEABLE);
		}
	}
}
