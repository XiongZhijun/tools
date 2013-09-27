/*
 * 香港摩比科技有限公司 版权所有
 *
 * www.mobi-inf.com
 */
package org.herod.house;

import java.util.List;

/**
 * 字符串工具集合
 * 
 * @author Xiong Zhijun
 * 
 */
public abstract class StringUtils {

	/** 一个空格 */
	public static final String BLANK = " ";
	/** 空字符串，长度为0 */
	public static final String EMPTY = "";

	/**
	 * 判断字符串是否为空。
	 * <p>
	 * 字符串为空成立的条件：
	 * <ul>
	 * <li>
	 * 1、str == null；
	 * <li>
	 * 2、str.length == 0;
	 * <li>
	 * 3、str只包含空格等空字符。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为非空。
	 * 
	 * @param str
	 * @return
	 * @see {@link #isBlank(String)}
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	/**
	 * 连接list列表成一个新串，中间以空格隔开。
	 * 
	 * @param list
	 * @return
	 */
	public static String join(List<?> list) {
		return join(BLANK, list);
	}

	/**
	 * 连接list列表成一个新串，中间以sep字符串隔开。
	 * 
	 * @param sep
	 * @param list
	 * @return
	 */
	public static String join(String sep, List<?> list) {
		if (list == null || list.size() == 0) {
			return EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for (Object item : list) {
			if (item != null) {
				sb.append(item.toString()).append(sep);
			}
		}
		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - sep.length());
		} else {
			return EMPTY;
		}

	}

	/**
	 * 以空格为间隔，连接字符串成一个新串。
	 * 
	 * @param strs
	 * @return
	 */
	public static String join(String... strs) {
		if (strs.length == 0) {
			return EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str).append(BLANK);
		}
		return sb.substring(0, sb.length() - BLANK.length());
	}
}
