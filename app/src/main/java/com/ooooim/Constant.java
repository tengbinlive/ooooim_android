package com.ooooim;

/**
 * 常量类.
 * 
 * 约定:
 * 1)Constant类里保存系统安装之后就一直保持不变的常量;
 * 2)App类里保存系统启动后可变的变量,变量的值一般在系统初始化时保存,和状态相关的量在过程中可变;
 * 3)SharedPeferences对象持久化App里部分的变量, 供App初始化时读取, 其他类统一读取App里的变量,
 * 不访问SharedPerferences, 如果以后更换持久化的方式,例如DB,则仅修改App类就可以.
 * 
 * @author bin.teng
 */
public class Constant {

	/** 调试模式(将会输出日志,自动解析到对应的测试环境API) */
	public static boolean DEBUG = true;

	public static final String DB_NAME = "ooooim_db";

	public static final int SOCKET_TIMEOUT = 5000;

	public static final String SHARED_PREFERENCES_FILE_NAME = "ooooim";

	public static final String FIR_API_TOKEN = "2ec6a8e27ad4af3a2e27011c62a5216f";

	/** 存储目录/文件 **/
	public static class Dir {
		/** 根目录 */
		public static final String ROOT_DIR = "/ooooim";
		/** 下载目录 */
		public static final String DOWNLOAD_DIR = ROOT_DIR + "/download";
		/** 缓存目录 */
		public static final String CACHE_DIR = ROOT_DIR + "/cache";
		/** 图片目录 */
		public static final String IMAGE_DIR = ROOT_DIR + "/images";
		/** 临时图片文件位置 */
		public static final String IMAGE_TEMP = ROOT_DIR + "/images/temp";
		/** 临时拍照文件位置 */
		public static final String CAMERA_TEMP = ROOT_DIR + "/images/camera_temp";
	}


	/** 微信相关常量 */
	public static class WeiXin {

		/** 微信-应用唯一标识 */
		public static final String APP_ID = "wxa91c0e9b1fcdea23";

	}

}
