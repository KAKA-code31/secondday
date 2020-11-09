

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestPOI {

	public static void main(String[] args) {
		InputStream is=null;
		try {
			is = new FileInputStream("C:\\wsenv\\40\\19软工学生名单.xlsx");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try {
			Workbook wb = new XSSFWorkbook(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				is.close();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}

	}

}
