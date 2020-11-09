package com.honestpeak.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * MySQL数据库备份
 * 
 * @author Administrator
 *
 */
public class MySQLDatabaseBackup {

	/**
	 * Java代码实现MySQL数据库导出
	 * 
	 * @param hostIP
	 *            MySQL数据库所在服务器地址IP
	 * @param userName
	 *            进入数据库所需要的用户名
	 * @param password
	 *            进入数据库所需要的密码
	 * @param savePath
	 *            数据库导出文件保存路径
	 * @param fileName
	 *            数据库导出文件文件名
	 * @param databaseName
	 *            要导出的数据库名
	 * @return 返回true表示导出成功，否则返回false。
	 */
	/*public static boolean exportDatabaseTool(String userName, String password,
			String savePath, String databaseName) throws InterruptedException {
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {// 如果目录不存在
			saveFile.mkdirs();// 创建文件夹
		}
		if (!savePath.endsWith(File.separator)) {
			savePath = savePath + File.separator;
		}

		String dbFileName = databaseName + "_"
				+ StringUtil.getDateTimeToFileName() + ".sql";
		String strFilePath = savePath + dbFileName;

		String strCmd = "mysqldump -u " + userName + " -p" + password
				+ " --databases " + databaseName + " > " + strFilePath;
		String flag = execCommand(strCmd);
		if (flag == "success") {
			return true;
		}
		return false;
	}*/

	public static String execCommand(String strCmd) {
		String strRel = "success";
		try {
			Runtime runtime = Runtime.getRuntime();
			String[] command = null;
			if (!StringUtil.isWindows()) {
				command = new String[] { "/bin/bash", "-c", strCmd };
			} else {
				command = new String[] { "cmd", "/c", strCmd };
			}
			Process process = runtime.exec(command);

			// print out running result
			InputStreamReader in = new InputStreamReader(
					process.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String line = null;
			while ((line = br.readLine()) != null) {
			}
			br.close();
			in.close();
			strRel = (line != null) ? strRel + "," + line : "success";

			// print out error messages
			InputStreamReader in2 = new InputStreamReader(
					process.getErrorStream());
			BufferedReader br2 = new BufferedReader(in2);
			String line2 = null;
			while ((line2 = br2.readLine()) != null) {
			}
			br2.close();
			in2.close();
			strRel = (line2 != null) ? "error" + "," + line2 : strRel;

		} catch (Exception ex) {
			ex.printStackTrace();
			strRel = "error,Exception:" + ex.getMessage();
		}
		return strRel;
	}
}
