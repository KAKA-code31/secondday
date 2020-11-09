package com.honestpeak.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码生成工具类
 * @author Administrator
 *
 */
public class EncoderHandlerUtil {
	
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	
	public static void encoderQRCoder(String content, int width, int height, HttpServletResponse response) {
		try {
			// 二维码图片格式
			String format = "png";

			// 设置二维码参数(编码内容，编码类型，图片宽度，图片高度,格式)
			//设置编码
			content = new String(content.getBytes("UTF-8"), "ISO-8859-1");
			
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, width, height);
			int b_width = bitMatrix.getWidth();
			int b_height = bitMatrix.getHeight();
			// 建立图像缓冲器
			BufferedImage image = new BufferedImage(b_width, b_height,
					BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < b_width; x++) {
				for (int y = 0; y < b_height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
				}
			}
			// 生成二维码
			ImageIO.write(image, format, response.getOutputStream()); // 二维码的名称
		} catch (WriterException | IOException e) {
			e.printStackTrace();
		}
								
	}
}
