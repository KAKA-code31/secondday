package com.honestpeak.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FilePreview {
	/**
	 * @author PengCheng Bian
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws InvalidFormatException
	 */
	public static String convertExceltoHtml(String path) throws IOException,ParserConfigurationException, TransformerException,InvalidFormatException {
		HSSFWorkbook workBook = null;
		String content = null;
		StringWriter writer = null;
		File excelFile = new File(path);
		InputStream is = new FileInputStream(excelFile);;
		workBook = new HSSFWorkbook(is);
		try {
			ExcelToHtmlConverter converter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			converter.setOutputColumnHeaders(false);// 不显示列的表头
			converter.setOutputRowNumbers(false);// 不显示行的表头
			converter.processWorkbook(workBook);
 
			writer = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(new DOMSource(converter.getDocument()),
					new StreamResult(writer));
 
			content = writer.toString();
			writer.close();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
}
