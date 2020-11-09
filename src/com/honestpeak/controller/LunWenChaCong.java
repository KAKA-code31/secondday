package com.honestpeak.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.hwpf.extractor.WordExtractor;

import com.honestpeak.utils.checkduplicate.CosineSimilarAlgorithm;
import com.honestpeak.utils.checkduplicate.EditDistance;
import com.honestpeak.utils.checkduplicate.JianDanMoHu;

public class LunWenChaCong {

	// 创建输入流读取DOC文件
	public String[] readWord(String path,String name[])
	{

		FileInputStream in = null;
		String[] text = new String[name.length];

		// 对DOC文件进行提取
		try {
			for(int i=0;i<name.length;i++)
			{
				String AbsolutePath=path+"\\"+name[i];
				in = new FileInputStream(new File(AbsolutePath));

				// 创建WordExtractor
				WordExtractor extractor = null;
				extractor = new WordExtractor(in); 
				text[i]=extractor.getText();
			}

		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		return text;
	}


     
	//编辑距离算法求相似度
		public Object[] EDcalculate(String text[],String name[])
		{

			EditDistance ed=new EditDistance();//编辑距离算法

			// 定义集合的二维数组储存动态生成的学生报告相似的学生信息
			ArrayList TotalNameList = new ArrayList(new ArrayList()),  
					TotalSimilarity = new ArrayList(new ArrayList());

			for(int i=0;i<text.length;i++)
			{
				for(int j=1;j<text.length;j++)
				{
					if (i >= j)
						continue;
					char a[], b[];
					a = new char[text[i].length()];
					b = new char[text[j].length()];
					text[i].getChars(0, text[i].length(), a, 0);
					text[j].getChars(0, text[j].length(), b, 0);

					int result = ed.edit(a, b, a.length, b.length);
					float maxLength = text[i].length() > text[j].length() ? text[i]
							.length() : text[j].length();
							float similarity = 1 - (result / maxLength);
							// JOptionPane.showMessageDialog(null, "编辑距离:" + result, "结果",
							// JOptionPane.INFORMATION_MESSAGE);
							System.out.println(text[i] + "和" + text[j] + "两串的相似度是："
								+ similarity);

							if (similarity > 0.50) // 输出相似度超过%60的学生到新的Excel
							{
								java.text.NumberFormat percentFormat = java.text.NumberFormat
										.getPercentInstance();// 将相似度转化为百分比显示
								percentFormat.setMaximumFractionDigits(3);
								String pesimilarity = percentFormat.format(similarity);
								System.out.println(pesimilarity);

								TotalNameList.add(name[i]);
								TotalNameList.add(name[j]);
								TotalSimilarity.add(pesimilarity);

							}

				}
			}

			Object[] o = {  TotalNameList,TotalSimilarity };// 将存储报告相似学生信息的集合存储到Object中
			return o;
		}
	
	//根据班级编辑距离算法求相似度
		public Object[] EDcalculateByClass(String text[],String name[],int flag)
		{
			if(flag == -1){
				Object[] o = { };
				return o;
			}
			EditDistance ed=new EditDistance();//编辑距离算法

			// 定义集合的二维数组储存动态生成的学生报告相似的学生信息
			ArrayList TotalNameList = new ArrayList(new ArrayList()),  
					TotalSimilarity = new ArrayList(new ArrayList());
			for(int i = 0; i < text.length; i++){
				if(i == flag)
					continue;
				char a[], b[];
				a = new char[text[flag].length()];
				b = new char[text[i].length()];
				text[flag].getChars(0, text[flag].length(), a, 0);
				text[i].getChars(0, text[i].length(), b, 0);

				int result = ed.edit(a, b, a.length, b.length);
				float maxLength = text[flag].length() > text[i].length() ? text[flag]
						.length() : text[i].length();
						float similarity = 1 - (result / maxLength);
						// JOptionPane.showMessageDialog(null, "编辑距离:" + result, "结果",
						// JOptionPane.INFORMATION_MESSAGE);
						System.out.println(text[i] + "和" + text[flag] + "两串的相似度是："
							+ similarity);

						if (similarity > 0.50) // 输出相似度超过%60的学生到新的Excel
						{
							java.text.NumberFormat percentFormat = java.text.NumberFormat
									.getPercentInstance();// 将相似度转化为百分比显示
							percentFormat.setMaximumFractionDigits(3);
							String pesimilarity = percentFormat.format(similarity);
							System.out.println(pesimilarity);

							TotalNameList.add(name[i]);
							TotalSimilarity.add(pesimilarity);

						}
			}
			if(TotalNameList.size() == 0  && TotalSimilarity.size() == 0){
				Object[] o = { };
				return o;
			}
			Object[] o = {  TotalNameList,TotalSimilarity };// 将存储报告相似学生信息的集合存储到Object中
			return o;
		}
	
	//余弦定理求相似度
	public Object[] CalculateSimilarityAlgrithm(String text[],String name[]) throws IOException,
	RowsExceededException, WriteException {
		
		 CosineSimilarAlgorithm csa=new  CosineSimilarAlgorithm(); //余弦定理算法
		
		// 定义集合的二维数组储存动态生成的学生报告相似的学生信息
		ArrayList TotalNameList = new ArrayList(new ArrayList()), 
		TotalSimilaity = new ArrayList(new ArrayList());

		for (int i = 0; i < text.length; i++) // 计算相似度
		{
			for (int j = 1; j <text.length; j++) {
				if (i >= j)
					continue;
				double result= csa.getSimilarity(text[i], text[j]);
						System.out.println(text[i] + "和jkkl  " + text[j] + "两串的相似度是："
								+ result);

						if (result > 0.50) // 输出相似度超过%60的学生到新的Excel
						{
							java.text.NumberFormat percentFormat = java.text.NumberFormat
									.getPercentInstance();// 将相似度转化为百分比显示
							percentFormat.setMaximumFractionDigits(3);
							String pesimilarity = percentFormat.format(result);
							System.out.println(pesimilarity);

							 // 将报告相似的学生的信息加入到集合
							TotalNameList.add(name[i]);
							TotalNameList.add(name[j]);
							TotalSimilaity.add(pesimilarity);//相似度

						}
			}

		}
		Object[] o = { TotalNameList,TotalSimilaity };// 将存储报告相似学生信息的集合存储到Object中
		return o;

	}
	
	
	//余弦定理求相似度
		public Object[] CalculateSimilarityAlgrithmByClass(String text[],String name[],int flag) throws IOException,
		RowsExceededException, WriteException {
			if(flag == -1){
				Object[] o = { };
				return o;
			}
			CosineSimilarAlgorithm csa=new  CosineSimilarAlgorithm(); //余弦定理算法

			// 定义集合的二维数组储存动态生成的学生报告相似的学生信息
			ArrayList TotalNameList = new ArrayList(new ArrayList()),  
					TotalSimilarity = new ArrayList(new ArrayList());
			for(int i = 0; i < text.length; i++){
				if(i == flag)
					continue;
				double result= csa.getSimilarity(text[flag], text[i]);
				System.out.println(text[flag] + "和jkkl  " + text[i] + "两串的相似度是："
						+ result);

				if (result > 0.50) // 输出相似度超过%60的学生到新的Excel
				{
					java.text.NumberFormat percentFormat = java.text.NumberFormat
							.getPercentInstance();// 将相似度转化为百分比显示
					percentFormat.setMaximumFractionDigits(3);
					String pesimilarity = percentFormat.format(result);
					System.out.println(pesimilarity);

					 // 将报告相似的学生的信息加入到集合
					TotalNameList.add(name[i]);
					TotalSimilarity.add(pesimilarity);//相似度

				}
			}
			if(TotalNameList.size() == 0  && TotalSimilarity.size() == 0){
				Object[] o = { };
				return o;
			}
			Object[] o = {  TotalNameList,TotalSimilarity };// 将存储报告相似学生信息的集合存储到Object中
			return o;
		}
	
	
	
	//简单模糊匹配算法
		public Object[] CalculateSimilarityJianDanMoHu(String text[],String name[]) throws IOException,
		RowsExceededException, WriteException {
			
			 JianDanMoHu jd=new JianDanMoHu();//简单模糊匹配算法
			 
			// 定义集合的二维数组储存动态生成的学生报告相似的学生信息
			ArrayList TotalNameList = new ArrayList(new ArrayList()), 
			TotalSimilaity = new ArrayList(new ArrayList());


			for (int i = 0; i <text.length; i++) // 计算相似度
			{
				for (int j = 1; j < text.length; j++) {
					if (i >= j)
						continue;
					
					List<char[]> c,d;
					c=jd.bigram(text[i]);//将字符串转化为字符数组
					d=jd.bigram(text[j]);
					double result=jd.dice(c, d);
							System.out.println(text[i] + "和jkkl  " + text[j] + "两串的相似度是："
									+ result);

							if (result >= 0.50) // 输出相似度超过%60的学生到新的Excel
							{
								java.text.NumberFormat percentFormat = java.text.NumberFormat
										.getPercentInstance();// 将相似度转化为百分比显示
								percentFormat.setMaximumFractionDigits(3);
								String pesimilarity = percentFormat.format(result);
								System.out.println(pesimilarity);

								 // 将报告相似的学生的信息加入到集合
								TotalNameList.add(name[i]);
								TotalNameList.add(name[j]);
								TotalSimilaity.add(pesimilarity);//相似度

							}
				}

			}
			Object[] o = { TotalNameList,TotalSimilaity };// 将存储报告相似学生信息的集合存储到Object中
			return o;

		}
		
		//简单模糊匹配算法
		public Object[] CalculateSimilarityJianDanMoHuByClass(String text[],String name[],int flag) throws IOException,
		RowsExceededException, WriteException {
			if(flag == -1){
				Object[] o = { };
				return o;
			}
			 JianDanMoHu jd=new JianDanMoHu();//简单模糊匹配算法
			 
			// 定义集合的二维数组储存动态生成的学生报告相似的学生信息
			ArrayList TotalNameList = new ArrayList(new ArrayList()), 
			TotalSimilarity = new ArrayList(new ArrayList());


			for(int i = 0; i < text.length; i++){
				if(i == flag)
					continue;
					
					List<char[]> c,d;
					c=jd.bigram(text[flag]);//将字符串转化为字符数组
					d=jd.bigram(text[i]);
					double result=jd.dice(c, d);
							System.out.println(text[flag] + "和jkkl  " + text[i] + "两串的相似度是："
									+ result);

					if (result >= 0.50) // 输出相似度超过%60的学生到新的Excel
					{
						java.text.NumberFormat percentFormat = java.text.NumberFormat
								.getPercentInstance();// 将相似度转化为百分比显示
						percentFormat.setMaximumFractionDigits(3);
						String pesimilarity = percentFormat.format(result);
						System.out.println(pesimilarity);

						 // 将报告相似的学生的信息加入到集合
						TotalNameList.add(name[i]);
						TotalSimilarity.add(pesimilarity);//相似度

					}

			}
			if(TotalNameList.size() == 0  && TotalSimilarity.size() == 0){
				Object[] o = { };
				return o;
			}
			Object[] o = { TotalNameList,TotalSimilarity };// 将存储报告相似学生信息的集合存储到Object中
			return o;

		}
		
		//保存报告相似的学生信息
		public void Save(Object[] object,String path) throws IOException, WriteException {
			// 定义集合的二维数组储存动态生成的学生报告相似的学生信息
			// 从object中取出集合信息，object为从 CalculateSimilarity（Object[] objects）中获得的报告相似的学生信息
			ArrayList TotalNameList = (ArrayList) object[0];
			ArrayList TotalSimilaity = (ArrayList) object[1];

	        
			OutputStream os = new FileOutputStream(path);
			WritableWorkbook wwb = Workbook.createWorkbook(os);// 创建可写工作簿
			WritableSheet ws = wwb.createSheet("sheet1", 0);

			Label numlabel = new Label(0, 0, "学号");  //输出Excel的首行
			Label namelabel = new Label(1, 0, "姓名");
			Label Titlelabel = new Label(2, 0, "    报告    题目");
			Label Similarity = new Label(5, 0, "相似度");
			ws.addCell(numlabel);
			ws.addCell(namelabel);
			ws.addCell(Titlelabel);
			ws.addCell(Similarity);

			int k = 1; // k作为输出相似报告行位置
			for (int i = 0; i < TotalNameList.size(); i++) 

			{
				Label namelabel2 = new Label(0, k, (String) TotalNameList.get(i));// 创建写入位置和内容

				// k=k+2;
				// 将Label写入sheet中
				ws.addCell(namelabel2);
				k++;
			}
			int n=2;
			for(int j=0;j<TotalSimilaity.size();j++)
			{
				Label persimilarity=new Label(5,n,(String)TotalSimilaity.get(j)); 
				ws.addCell(persimilarity);
				n=n+2;
			}
			// 现在可以写了
			wwb.write();
			wwb.close();
			os.close();

		}

		//保存报告相似的学生信息
		public void SaveByClass(Object[] object,String path,int flag) throws IOException, WriteException {
			// 定义集合的二维数组储存动态生成的学生报告相似的学生信息
			// 从object中取出集合信息，object为从 CalculateSimilarity（Object[] objects）中获得的报告相似的学生信息
			ArrayList TotalNameList = (ArrayList) object[0];
			ArrayList TotalSimilaity = (ArrayList) object[1];
			String tempName = path.substring(path.lastIndexOf("\\")+1, path.lastIndexOf('.'));
	        
			OutputStream os = new FileOutputStream(path);
			WritableWorkbook wwb = Workbook.createWorkbook(os);// 创建可写工作簿
			WritableSheet ws = wwb.createSheet(tempName+"查重报告", 0);
	
			Label numlabel = new Label(0, 0, "学号           ");  //输出Excel的首行
			Label namelabel = new Label(1, 0, "姓名  ");
			Label Titlelabel = new Label(2, 0, "    报告题目");
			Label Similarity = new Label(5, 0, "相似度");
			ws.addCell(numlabel);
			ws.addCell(namelabel);
			ws.addCell(Titlelabel);
			ws.addCell(Similarity);
	
			int k = 1; // k作为输出相似报告行位置
			for (int i = 0; i < TotalNameList.size(); i++) 
	
			{
				Label namelabel2 = new Label(0, k, (String) TotalNameList.get(i));// 创建写入位置和内容
	
				// k=k+2;
				// 将Label写入sheet中
				ws.addCell(namelabel2);
				k++;
			}
			int n=1;
			for(int j=0;j<TotalSimilaity.size();j++)
			{
				Label persimilarity=new Label(5,n,(String)TotalSimilaity.get(j)); 
				ws.addCell(persimilarity);
				n=n+1;
			}
			// 现在可以写了
			wwb.write();
			wwb.close();
			os.close();
		}
}
