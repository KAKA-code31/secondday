package com.honestpeak.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("back/notice")
public class NoticeController {
		
		@RequestMapping("/download")
		public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String filePath = request.getSession().getServletContext().getRealPath("upload"+File.separator+"template");
			File f = new File(filePath+File.separator+"OperatingInstruction.docx");
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms.word;charset=utf-8");
			try {
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(("OperatingInstruction" + ".docx").getBytes(), "iso-8859-1"));// 下载文件的名称
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = new BufferedInputStream(new FileInputStream(f));
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (final IOException e) {
				throw e;
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
		}
}
