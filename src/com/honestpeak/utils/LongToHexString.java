package com.honestpeak.utils;

import com.honestpeak.constant.Constants;

/**
 * @ClassName: LongToHexString
 * @Description: long型转换成16进制 高低位 两位反序
 * @author Jeabev
 * @date 2016年12月20日 下午6:50:00
 */
public class LongToHexString {
	final static char[] digits = {
        '0' , '1' , '2' , '3' , '4' , '5' ,
        '6' , '7' , '8' , '9' , 'a' , 'b' ,
        'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
        'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
        'o' , 'p' , 'q' , 'r' , 's' , 't' ,
        'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };
	
	public static String toHexString(long i) {
        return toUnsignedString(i, 4);
    }
	
	private static String toUnsignedString(long i, int shift) {
        char[] buf = new char[64];
        char[] temp = new char[8];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int)(i & mask)];
            i >>>= shift;
        } while (i != 0);
        for(int j = 63, k=0; j>=charPos; j = j-2,k= k+2){
        	temp[k] = buf[j - 1] =='\u0000' ?'0' : buf[j - 1];
        	temp[k + 1] = buf[j] =='\u0000' ?'0' : buf[j];
        }
        
        return createString(new String(temp).trim());
    }
	
	/**
	 * @Title: createString
	 * @Description: 针对某些卡号不够长度的这种情况，写的末尾补0
	 * @param str
	 */
	private static String createString(String str) {
		int strlen = str.length();//输入的卡号长度
		//和定义的卡号长度比较，长度不够开始补0
		if(strlen<Constants.CL_STUDENTCARDCD){
			StringBuffer sb = new StringBuffer(str);
			for (int i = 0; i < (Constants.CL_STUDENTCARDCD-strlen); i++) {
				sb.append("0");
			}
			return sb.toString();
		}
		
		return str;
	}

	public static String toLocalStyleHexString(long i,int len){
		Long l = Long.parseLong(toUnsignedString(i, 4), 16);
		StringBuffer sb = new StringBuffer(l.toString());
		int foo = (len - sb.length());
		for (int j=0;j<foo;j++) {
			sb.insert(0, "0");
		}
		return sb.toString();
	}
	
}
