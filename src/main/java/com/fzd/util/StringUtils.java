package com.fzd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串助手类
 * @ClassName:  StringUtils   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: chenyang 
 * @date:   2017年7月18日 下午5:35:12   
 *
 */
public class StringUtils {

	/**
	 * 首字母转小写
	 * 
	 * @param s 待转换的字符�?
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 从字符串中提取浮点数
	 * @param str
	 * @return
     */
	public static Float getFloatFromStr(String str){
		Pattern pattern = Pattern.compile("\\d*[.]*\\d*");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			if (!"".equals(matcher.group()))
				return Float.parseFloat(matcher.group());
		}
		return 0.0f;
	}

	/**
	 * 从字符串中提取整数并拼接为字符串
	 * @param str
	 * @return
	 */
	public static Integer getNumberFromStr(String str){
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(str);
		try {
			Integer result = Integer.parseInt(matcher.replaceAll("").trim());
			return result;
		}catch (Exception e){

		}
		return null;
	}

	public static String getChineseFromStr(String str){
		Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
//[\u4E00-\u9FA5]是unicode2的中文区间
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll("").trim();
	}

	/**
	 * 从字符串中
	 * @param str
	 * @return
     */
	public static List<String> getDateString(String str){
		Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}");
		Matcher matcher = pattern.matcher(str);
		List<String> optPeriodList = new ArrayList<>();
		while (matcher.find()) {
			if (!"".equals(matcher.group()))
				optPeriodList.add(matcher.group());
		}
		return optPeriodList;
	}

	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}
		/**
         * @songwenju
         */
	public static void main(String[] args) {
		String reg = "fsdf";
		Integer regFloag = getNumberFromStr(reg);
		System.out.print(regFloag);
	}
}
