package sbgl3.yunda.entry;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 字符串,byte[],16进制的字符串互转
 * <li>创建人：何东
 * <li>创建日期：2016年6月17日
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 1.0
 */
public class HexStrUtil {
    
    /**
     * 字符串转换成十六进制字符串
     */
    public static String str2HexStr(String str) {
        
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }
    
    /**
     * 十六进制转换字符串
     */
    public static String hexStr2Str(String s) {
    	if (null != s) {
    		s = s.replaceAll(" ", "");
    	}
        byte[] baKeyword = new byte[s.length()/2];
        for(int i = 0; i < baKeyword.length; i++){
        try{
        	baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16));
        }catch(Exception e){
           e.printStackTrace();
        	}
        }
        try{
        	s = new String(baKeyword, "utf-8");//UTF-16le:Not
        }catch (Exception e1){
        	e1.printStackTrace();
        }
        return s;
    }
    
    /**
     * bytes转换成十六进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
    
    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }
    
    /**
     * 十六进制字符串转换成bytes
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }
    
    /**
     * String的字符串转换成unicode的String
     */
    public static String str2Unicode(String strText) throws Exception {
        char c;
        String strRet = "";
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128) {
                strRet += "//u" + strHex;
            } else {
                // 低位在前面补00
                strRet += "//u00" + strHex;
            }
        }
        return strRet;
    }
    
    /**
     * unicode的String转换成String的字符串
     */
    public static String unicode2Str(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }
    
    /**
     * <li>说明：十六进制转换为间隔2个空格的字符串
     * <li>创建人：黄杨
     * <li>创建日期：2017年7月25日
     * <li>修改人： 
     * <li>修改日期：
     * <li>修改内容：
     * @param oldHex 待转换的十六进制字符串
     * @return 转换后的十六进制
     */
    public static String hex2SpaceHex(String oldHex) {
    	String regex = "(.{2})";
    	oldHex = oldHex.replaceAll(regex, "$1 ");
    	return oldHex.trim();
    }
}
