package com.honestpeak.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @ClassName: FileOperateUtil
 * @Description: 文件操作工具类
 * @author Jeabev
 * @date 2016年8月27日 下午3:28:26
 */
public class FileOperateUtil {
	public static String FILEDIR = PropertyUtils.getRoot();

	/**
	 * @Title: upload
	 * @Description: 上传，给定文件上传请求并给定文件保存的目录（如：/upload/docs）,此方法会自动获取系统路径
	 * @param request
	 *            上传文件请求
	 * @param fileDir
	 *            上传文件的文件目录
	 * @throws IOException
	 */
	public static List<String> upload(HttpServletRequest request, String fileDir) throws IOException {

		List<String> filePaths = new ArrayList<>();
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();
		File file = new File(FILEDIR + fileDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();
			if (mFile.getSize() != 0 && !"".equals(mFile.getName())) {
				Map<String, String> map = initFilePath(fileDir, mFile.getOriginalFilename());
				write(mFile.getInputStream(), new FileOutputStream(map.get("systemPath") + map.get("filePath")));//
				filePaths.add(map.get("filePath"));
			}
		}
		return filePaths;
	}

	/**
	 * @Title: initFilePath
	 * @Description: 初始化文件上传目录
	 * @param filedir文件保存系统目录
	 * @param name
	 * @return
	 */
	private static Map<String, String> initFilePath(String filedir, String name) {
		String dir = getFileDir(name) + "";
		File file = new File(FILEDIR + filedir + File.separator + dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		Long num = new Date().getTime();
		Double d = Math.random() * num;
		String filePath = (filedir + File.separator + dir + File.separator + num + d.longValue() + "_" + name)
				.replaceAll(" ", "-");
		String systemPath = FILEDIR.replaceAll(" ", "-");
		Map<String, String> map = new HashMap<>();
		map.put("filePath", filePath);
		map.put("systemPath", systemPath);
		return map;// (file.getPath() + File.separator + num + d.longValue() +
					// "_" + name).replaceAll(" ", "-");
	}

	private static int getFileDir(String name) {
		return name.hashCode() & 0xf;
	}

	/**
	 * @Title: download
	 * @Description: 文件下载类
	 * @param downloadfFileName
	 * @param out
	 */
	public static void download(String downloadfFileName, ServletOutputStream out) {
		try {
			FileInputStream in = new FileInputStream(new File(FILEDIR + File.separator + downloadfFileName));
			write(in, out);
		} catch (FileNotFoundException e) {
			try {
				FileInputStream in = new FileInputStream(new File(
						FILEDIR + File.separator + new String(downloadfFileName.getBytes("iso-8859-1"), "utf-8")));
				write(in, out);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写入数据
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void write(InputStream in, OutputStream out) throws IOException {
		try {
			byte[] buffer = new byte[1024];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
			}
			try {
				out.close();
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * @Title: getFileRealName
	 * @Description: 通过上传路径获取文件原始文件名<br>
	 *               注意：若文件名不存在或者不合系统文件规范，或者是该文件不是由本工具类上传的，则返回入参path
	 * @return
	 */
	public static String getFileRealName(String path) {
		if (path != null && path.length() > 0 && !path.trim().equals("") && path.indexOf("_") > -1) {
			return path.substring(path.indexOf("_") + 1);
		}
		return path;
	}

	/**
	 * 给定文件名删除文件
	 * 
	 * @param downloadfFileName
	 * @return
	 */
	public static boolean delete(String downloadfFileName) {
		File file = new File(FILEDIR + downloadfFileName);
		if (file.exists()) {
			file.delete();
			return true;
		}
		return false;

	}

	// 得到某一目录下的所有文件夹
	public static List<File> visitAll(File root) {
		List<File> list = new ArrayList<File>();
		File[] dirs = root.listFiles();
		if (dirs != null) {
			for (int i = 0; i < dirs.length; i++) {
				if (dirs[i].isDirectory()) {
					System.out.println("name:" + dirs[i].getPath());
					list.add(dirs[i]);
				}
				visitAll(dirs[i]);
			}
		}
		return list;
	}

	/**
	 * 删除空的文件夹
	 * 
	 * @param list
	 */
	public static void removeNullFile(List<File> list) {
		for (int i = 0; i < list.size(); i++) {
			File temp = list.get(i);
			// 是目录且为空
			if (temp.isDirectory() && temp.listFiles().length <= 0) {
				temp.delete();
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();// "删除单个文件"+name+"成功！"
			return true;
		} // "删除单个文件"+name+"失败！"
		return false;
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 */

	public static boolean deletefile(String delpath) throws FileNotFoundException, IOException {
		try {

			File file = new File(delpath);
			if (!file.isDirectory()) {
				System.out.println("1");
				file.delete();
			} else if (file.isDirectory()) {
				System.out.println("2");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						System.out.println("path=" + delfile.getPath());
						System.out.println("absolutepath=" + delfile.getAbsolutePath());
						System.out.println("name=" + delfile.getName());
						delfile.delete();
						deleteFile(delfile.getName());
						System.out.println("删除文件成功");
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "\\" + filelist[i]);
					}
				}
				file.delete();

			}

		} catch (FileNotFoundException e) {
			System.out.println("deletefile()  Exception:" + e.getMessage());
		}
		return true;
	}
}