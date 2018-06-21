package com.yan.utils.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * html 操作
 */
public class HtmlUtils {

    public static String getTextFromTHML(String htmlStr) {
        Document doc = Jsoup.parse(htmlStr);
        String text = doc.text();
        // remove extra white space
        StringBuilder builder = new StringBuilder(text);
        int index = 0;
        while(builder.length()>index){
            char tmp = builder.charAt(index);
            if(Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)){
                builder.setCharAt(index, ' ');
            }
            index++;
        }
        text = builder.toString().replaceAll(" +", " ").trim();
        return text;
    }

    /**
     * 判断页面是否可用
     * @param htmlStr
     * @return
     */
    public static boolean isAvaiable(String htmlStr){
        if (htmlStr == null || htmlStr.isEmpty()){
            return false;
        }

        if (htmlStr.contains("<embed") || htmlStr.contains("<video")
                || htmlStr.contains("<object") || htmlStr.contains("<img")){
            return true;
        }

        if (getTextFromTHML(htmlStr).length() < 5){
            return false;
        }else {
            return true;
        }
    }
}
