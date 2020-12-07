package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author 张恒
 *
 */
public class StringUtil {
	/**
	 * 字符串匹配
	 * @return
	 */
	public static boolean matchSubStr(String parStr, String subStr) {
		Pattern p = Pattern.compile(subStr);
        Matcher m = p.matcher(parStr);
        return m.find();
	}
}
