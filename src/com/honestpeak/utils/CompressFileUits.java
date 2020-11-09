package com.honestpeak.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.honestpeak.exception.ServiceException;

public class CompressFileUits {
	/**
	 * 解压到指定目录
	 * 
	 * @param zipPath
	 * @param descDir
	 * @author
	 */
	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile, "gbk"); // 解决乱码问题
		for (Enumeration entries = zip.getEntries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			;
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		zip.close();
		System.out.println("******************解压完毕********************");
	}

	/**
	 * 根据原始rar路径，解压到指定文件夹下.
	 * 
	 * @param srcRarPath
	 *            原始rar路径
	 * @param dstDirectoryPath
	 *            解压到的文件夹
	 */
	public static void unRarFile(String srcRarPath, String dstDirectoryPath) {
		if (!srcRarPath.toLowerCase().endsWith(".rar")) {
			System.out.println("非rar文件！");
			return;
		}
		File dstDiretory = new File(dstDirectoryPath, "gbk"); // 解决乱码问题
		if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
			dstDiretory.mkdirs();
		}
		Archive a = null;
		try {
			a = new Archive(new File(srcRarPath));
			if (a != null) {
				a.getMainHeader().print(); // 打印文件信息.
				FileHeader fh = a.nextFileHeader();
				while (fh != null) {
					if (fh.isDirectory()) { // 文件夹
						File fol = new File(dstDirectoryPath + File.separator + fh.getFileNameString());
						fol.mkdirs();
					} else { // 文件
						File out = new File(dstDirectoryPath + File.separator + fh.getFileNameString().trim());
						// System.out.println(out.getAbsolutePath());
						try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
							if (!out.exists()) {
								if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
									out.getParentFile().mkdirs();
								}
								out.createNewFile();
							}
							FileOutputStream os = new FileOutputStream(out);
							a.extractFile(fh, os);
							os.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					fh = a.nextFileHeader();
				}
				a.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws ServiceException
	 * @Title: getFileList @Description: 递归遍历指定文件夹下的文件 @param @param
	 *         strPath @param @return 设定文件 @return List<File> 返回类型 @throws
	 */
	public static List<File> getFileList(String strPath, String fileordernumber) throws ServiceException {
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		List<File> fileList = new ArrayList<File>();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath(), fileordernumber); // 获取文件绝对路径
					System.out.println("输出文件的绝对路径" + files[i].getAbsolutePath());
				} else if (fileName.endsWith("html")) { // 判断文件名是否以.avi结尾
					String strFileName = files[i].getAbsolutePath();
					System.out.println("------------" + strFileName + "+++++" + fileName);
					// uploadcompressDetailService.insertCompressDetailFile(fileordernumber,fileName,strFileName,new
					// Date());
					// freemarkerDetailService.insertFreeMarkerDetailFile(fileordernumber,fileName,strFileName,new
					// Date());
					fileList.add(files[i]);
				} else {
					continue;
				}
			}

		}
		return fileList;
	}

	/**
	 * 存放文件的数组
	 */
	private static Set<String> filelist = new HashSet<String>();

	/**
	 * 获取文件夹下的所有文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static Set<String> getFiles(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				/*
				 * 递归调用
				 */
				getFiles(file.getAbsolutePath());
				System.out.println("显示" + filePath + "下所有子目录及其文件" + file.getAbsolutePath());
			} else {
				String suffix = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1,
						file.getAbsolutePath().length());
				if ("xlsx".equals(suffix.toLowerCase()) || "xls".equals(suffix.toLowerCase())) {
					filelist.add(file.getAbsolutePath());
				}
				System.out.println("显示" + filePath + "下所有子目录" + file.getAbsolutePath());
			}
		}
		return filelist;
	}

	public static void main(String[] args) throws Exception {

		String filePath = "C:\\Users\\ZD\\Desktop\\班级名称\\班级名称.zip";
		String savePath = "C:\\Users\\ZD\\Desktop\\班级名称\\"; // 截取文件后缀 String
															// suffix
		String suffix = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		// System.out.println(suffix);
		if ("zip".equals(suffix.toLowerCase())) {
			unZipFiles(filePath, savePath);
		} else if ("rar".equals(suffix.toLowerCase())) {
			unRarFile(filePath, savePath);
		}

		Set<String> fileStr = getFiles(savePath);

		for (String str : fileStr) {
			// System.out.println(str);
			System.out.println(readExcelFile(str));
		}

	}

	/**
	 * 解压文件
	 * 
	 * @param filePathName
	 *            详细路径+文件名
	 */
	public static Set<String> unFile(String filePathName) {
		String suffix = filePathName.substring(filePathName.lastIndexOf(".") + 1, filePathName.length());
		String savePath = filePathName.substring(0, filePathName.lastIndexOf("/") + 1);
		if ("zip".equals(suffix.toLowerCase())) {
			try {
				unZipFiles(filePathName, savePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if ("rar".equals(suffix.toLowerCase())) {
			unRarFile(filePathName, savePath);
		}

		Set<String> fileStr = getFiles(savePath);
		return fileStr;
	}

	public static String[][] readExcelFile(String filename) {
		String[][] result = null;
		String fileToBeRead = filename;
		String suffix = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		// 创建对Excel工作簿文件的引用
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileToBeRead));
			result = readExcelData(workbook);
/*			if ("xlsx".equals(suffix.toLowerCase())) {
			} else {
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
				result = readExcelData(workbook);
			}
*/
		} catch (FileNotFoundException e) {
			System.out.println("文件没找到 : " + e);
		} catch (IOException e) {
			System.out.println("已运行IO异常: " + e);
		}
		return result;

	}

	/**
	 * 读取Excel数据
	 * 
	 * @param workbook
	 * @return
	 */
	public static String[][] readExcelData(Workbook workbook) {
		// 初始化返回数据集对象
		String[][] datas = new String[9][9];
		Sheet sheet = workbook.getSheetAt(0);

		// 总行：
		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCells = 0;
		// 得到Excel的列数(前提是有行数)
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		for (int r = 1; r < totalRows-2; r++) {
			Row row = sheet.getRow(r);
			if (row == null)
				continue;
			// 循环Excel的列
			for (int c = 0; c < totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						datas[r][c] = cell.getNumericCellValue() + "  ";
						break;
					case Cell.CELL_TYPE_STRING: // 字符串
						datas[r][c] = cell.getStringCellValue() +  "   ";
						break;
					case Cell.CELL_TYPE_BOOLEAN: // Boolean
						datas[r][c] = cell.getBooleanCellValue() +  "   ";
						break;
					case Cell.CELL_TYPE_FORMULA: // 公式
						datas[r][c] = cell.getCellFormula() +  "   ";
						break;
					case Cell.CELL_TYPE_BLANK: // 空值
						break;
					case Cell.CELL_TYPE_ERROR: // 故障
						break;
					default:
						System.out.print("未知类型   ");
						break;
					}
					
				} else {
					datas[r][c] = "0.0";
				}
				System.out.println("datas[" + r + "][" + c + "]" + datas[r][c]);
			}
		}
		
		return datas;
	}

}