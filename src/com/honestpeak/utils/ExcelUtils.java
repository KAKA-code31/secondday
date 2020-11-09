package com.honestpeak.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.honestpeak.result.Result;

/**
 * @ClassName: ExcelUtils
 * @Description: poi操作EXCEL工具类
 * @author Jeabev
 * @date 2016年9月8日 上午9:34:30
 */
public class ExcelUtils {
	/**
	 * @Title: readExcel
	 * @Description: 读取Excel，并将excel中的数据，按照给定规则，生成对应的json数据信息，方便后期json转对象，
	 *               并保存到数据库中。<br>
	 *               注意点: <br>
	 *               1：excel文件第一行必须是表头，用来验证数据项是否正确。<br>
	 *               2：不支持一次性导入多个工作表。<br>
	 *               3：不支持2007版本以上的excel导入
	 * @param is
	 *            上传的excel文件输入流
	 * @param rule
	 *            Map<Integer, String>验证规则。"规则: [0:'学号:xh']"
	 *            ,0表示工作表列数下标(0开始)，'学号'代表列表头，'xh'代表类字段。
	 * @return
	 * @throws IOException
	 * @Deprecated 由于初始阶段未考虑文件读取2007版本以上的excel文件，导致文件部分功能不完善。已不建议使用。详见新方法
	 *             {@link ExcelUtils#readExcel(InputStream, boolean, Map)}
	 */
	@Deprecated
	public static Result readExcel(InputStream is, Map<Integer, String> rule) throws IOException {
		List<String> datas = null;
		// 判断文件输入流是否为空
		if (is == null) {// 不存在直接返回空值
			return new Result(false, "工作表文件不存在！");
		}

		if (rule == null || rule.size() == 0) {
			return new Result(false, "验证规则不存在！");
		}

		if (rule.get(0).indexOf(":") == -1) {
			return new Result(false, "验证规则不符合规则！");
		}

		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		// 验证数据合法性
		if (!validityWorkbook(hssfWorkbook, rule)) {
			return new Result(false, "请检查Excel中的数据，数据不正确！");
		}

		// excel文件中的数据读取
		datas = readExcelData(hssfWorkbook, rule);

		if (datas == null || datas.size() == 0) {
			return new Result(false, "请检查Excel中的数据，数据读取失败！");
		}

		return new Result(true, "excel读取成功", datas);
	}

	/**
	 * @Title: readExcel
	 * @Description: 读取Excel，并将excel中的数据，按照给定规则，生成对应的json数据信息，方便后期json转对象，
	 *               并保存到数据库中。<br>
	 *               注意点: <br>
	 *               1：excel文件第一行必须是表头，用来验证数据项是否正确。<br>
	 *               2：不支持一次性导入多个工作表。<br>
	 *               3：加入2007版本excel数据读取功能(new)
	 * @param is
	 *            上传的excel文件输入流
	 * @param isHighExcel
	 *            是否为2007以上版本的excel(true表示是2007版本以上的excel，文件后缀名为xlsx；
	 *            false表示2003版excel，文件后缀名为xls)
	 * @param rule
	 *            Map<Integer, String>验证规则。"规则: [0:'学号:xh']"
	 *            ,0表示工作表列数下标(0开始)，'学号'代表列表头，'xh'代表类字段。
	 * @return
	 * @throws IOException
	 */
	public static Result readExcel(InputStream is, boolean isHighExcel, Map<Integer, String> rule) throws IOException {
		List<String> datas = null;
		// 判断文件输入流是否为空
		if (is == null) {// 不存在直接返回空值
			return new Result(false, "工作表文件不存在！");
		}
		if (rule == null || rule.size() == 0) {
			return new Result(false, "验证规则不存在！");
		}

		if (rule.get(0).indexOf(":") == -1) {
			return new Result(false, "验证规则不符合规则！");
		}

		Workbook wb = null;
		if (isHighExcel) {// 根据不同excel类型，创建不同类型的 Workbook 对象
			wb = new XSSFWorkbook(is);
		} else {
			wb = new HSSFWorkbook(is);
		}

		// 验证数据合法性
		if (!validityWorkbook(wb, rule)) {
			return new Result(false, "请检查Excel中的数据，数据不正确！");
		}

		// excel文件中的数据读取
		datas = readExcelData(wb, rule);

		if (datas == null || datas.size() == 0) {
			return new Result(false, "请检查Excel中的数据，数据读取失败！");
		}

		return new Result(true, "excel读取成功", datas);
	}

	/**
	 * @Title: readExcelData
	 * @Description: 通过给定规则 rule，读取excel中的数据信息，并返回封装的json结果List
	 * @param workbook
	 *            POI工作表
	 * @param rule
	 *            验证规则。"规则: [0:'学号:xh']",0表示工作表列数下标(0开始)，'学号'代表列表头，'xh'代表类字段。
	 * @return List
	 */
	private static List<String> readExcelData(Workbook workbook, Map<Integer, String> rule) {
		List<String> datas = null;
		// 循环工作表
		for (int sheets = 0; sheets < workbook.getNumberOfSheets(); sheets++) {
			// 找到一个不为空的工作表
			Sheet sheet = workbook.getSheetAt(sheets);
			if (sheet == null) {
				continue;// 跳过本次循环
			}
			// 初始化返回数据集对象
			datas = new ArrayList<String>();

			// 循环行，这里跳过第一行(表头)
			for (int row = 1; row <= sheet.getLastRowNum(); row++) {
				Row r = sheet.getRow(row);
				if (r == null) {
					continue;
				}

				StringBuffer sb = new StringBuffer("{");
				// 循环列
				for (int cell = 0; cell < rule.size(); cell++) {
					Cell cellData = r.getCell(cell);
					sb.append("'" + rule.get(cell).substring(rule.get(cell).indexOf(":") + 1, rule.get(cell).length())
							+ "':'" + getValue(cellData) + "',");
				}

				if (',' == sb.charAt(sb.length() - 1)) {
					sb.delete(sb.lastIndexOf(","), sb.length());
				}

				sb.append("}");

				datas.add(sb.toString());
			}
			break;// 不允许一次性导入多个工作表
		}
		return datas;
	}

	/**
	 * @Title: validityWorkbook
	 * @Description: 通过给定规则 rule，验证POI工作表数据合法性
	 * @param hssfWorkbook
	 *            POI工作表
	 * @param rule
	 *            验证规则。"规则: [0:'学号:xh']",0表示工作表列数下标(0开始)，'学号'代表列表头，'xh'代表类字段。
	 * @return true:数据验证通过, false:数据验证不通过
	 */
	private static boolean validityWorkbook(Workbook workbook, Map<Integer, String> rule) {

		// 循环工作表
		for (int sheets = 0; sheets < workbook.getNumberOfSheets(); sheets++) {

			// 找到一个不为空的工作表
			Sheet sheet = workbook.getSheetAt(sheets);
			if (sheet == null) {
				continue;// 跳过本次循环
			}

			// 找到一行不为空的数据
			Row row = sheet.getRow(0);// 这里定死第一行，避免后期读取数据存在问题

			/*
			 * for(int row = 0; row < hssfSheet.getLastRowNum(); row++){ hssfRow
			 * = hssfSheet.getRow(row); if(hssfRow == null){
			 * continue;//跳过本行，向下寻找，直到找到一行不为空 } }
			 */

			// 验证(表头)，判断第一行中的每一列数据与规则是否相符
			for (int cel = 0; cel < rule.size(); cel++) {
				String r = rule.get(cel);// rule，验证规则
				String d = getValue(row.getCell(cel));// 待验证数据
				if (r == null || d == null || !r.substring(0, r.indexOf(":")).equals(d)) {
					return false;// 返回错误，数据验证不通过
				}
			}

			// 以上验证全部通过说明数据正确
			return true;
		}
		// 工作表全部为空
		return false;
	}

	@SuppressWarnings("static-access")
	private static String getValue(Cell cell) {
		if (cell == null){
			return "";
		} else if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			//DecimalFormat df = new DecimalFormat("0");// 避免科学计数法
			return String.valueOf(cell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(cell.getStringCellValue());
		}
	}

}
