package com.yan.utils.html;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

/**
 * 按照指定的字数进行截取，如果是html内容则查找出所在标签，截取之后做标签的闭合处理
 * 
 * @author caoqiang
 * @date: 2018年6月11日 下午6:11:22
 */
public class HtmlSubUtils {
	/**
	 * 
	 * @param source
	 *            原始HTML内容
	 * @param keepLength
	 *            保留文本内容长度
	 * @return String 截取后的结果
	 */
	public static String subStr(String source, Integer keepLength) {
		source = source.replaceAll("\t|\r|\n", "").trim();
		String tmpSource = new String();
		tmpSource = Jsoup.parse(source).text(); // 纯文本内容
		System.out.println("原始内容：" + source);
		System.out.println("纯文本内容：" + tmpSource);
		// 获取所有的标签及所在位置放在map中
		String regx = "<.*?[/]{0,1}>";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(source);
		List<TagIndex> list = new ArrayList<TagIndex>();
		while (matcher.find()) {
			// 正则匹配，记录每次匹配的标签及其所在内容中的位置
			TagIndex tagIndex = new TagIndex();
			String currentTag = matcher.group();
			// System.out.println(matcher.start() + ":" + currentTag + "--" +
			// currentTag.length());
			tagIndex.setTag(currentTag);
			tagIndex.setIndex(matcher.start());
			list.add(tagIndex);
		}

		if (tmpSource.length() > keepLength) {
			tmpSource = tmpSource.substring(0, keepLength);
		}

		System.out.println(tmpSource);
		List<String> insertTags = new ArrayList<String>();
		if (!list.isEmpty()) {
			for (TagIndex tagIndex : list) {
				if (tagIndex.getIndex() >= tmpSource.length()) {
					break;
				} else {
					insertTags.add(tagIndex.getTag());
					tmpSource = tmpSource.substring(0, tagIndex.getIndex()) + tagIndex.getTag()
							+ tmpSource.substring(tagIndex.getIndex(), tmpSource.length());
				}
			}
		}
		List<String> closeTags = closeTag(insertTags);
		for (String string : closeTags) {
			tmpSource += string;
		}
		System.out.println(tmpSource);
		return null;
	}

	/**
	 * 分析已插入的标签列表，找到未闭合的标签列表，返回闭合标签列表，此处涉及列表中元素remove，使用了两个列表，效率可能有点低
	 * 
	 * @param List<String>
	 *            tags 已成功插入内容中的标签列表
	 * @return List<String> closeTag 需要拼接的闭合标签列表
	 */
	public static List<String> closeTag(List<String> tags) {
		List<String> unclosedTags = new ArrayList<String>();
		unclosedTags.addAll(tags);
		for (String tag : tags) {
			if (tag.matches("<.*?/>")) {
				// 自闭合标签从列表中移除
				unclosedTags.remove(tag);
				continue;
			}
			if (tag.matches("<.*?>")) {
				String tagClosed = "";
				if (tag.indexOf(" ") > 0) {
					tagClosed = "</" + tag.substring(1, tag.indexOf(" ")) + ">";
				} else {
					tagClosed = "</" + tag.substring(1, tag.length());
				}
				if (unclosedTags.contains(tagClosed)) {
					// 左标签，右标签同时存在，成对remove
					unclosedTags.remove(tag);
					unclosedTags.remove(tagClosed);
				}
			}
		}
		System.out.println(unclosedTags.size());
		List<String> closeTag = new ArrayList<String>();
		for (int i = unclosedTags.size() - 1; i >= 0; i--) {
			String unTag = unclosedTags.get(i);
			String tagClosed = "";
			if (unTag.indexOf(" ") > 0) {
				tagClosed = "</" + unTag.substring(1, unTag.indexOf(" ")) + ">";
			} else {
				tagClosed = "</" + unTag.substring(1, unTag.length());
			}
			closeTag.add(tagClosed);
		}

		return closeTag;

	}

	public static void main(String[] args) {
		try {
			String source = "<html><div><h1>html截取测试，长长长长长长长长长长长长长长长长长长长长长长长长长</h1></div></html>";
			// System.out.println(source);
			subStr(source, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
