package com.eleven.app.util;

/**
 * Created by eleven on 14-4-16.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WYUParser {


    /**
     *
     * @param html
     *            网页的源代码
     * @return List<ClassInfo> 返回一个课程信息的链表
     */
    static public List<ClassInfo> parseTimetable(String html) {

        //getCourseType(html);

        List<String> strList = new ArrayList<String>();
        List<ClassInfo> result = new ArrayList<ClassInfo>();
        String regex = "<td.*?>(.*?)</td>";

        Pattern pa = Pattern.compile(regex);
        Matcher ma = pa.matcher(html);
        String match, s;
        while (ma.find()) {
            match = ma.group(1);
            //s = match.replaceAll("<br>", " ");
            //System.out.println(match);
            strList.add(match);
        }
        // 行：课时；列：星期
        String[][] courses = new String[6][8];
        int size = strList.size();
        int p = 0;
        for (int i = 1; i <= 5 && p < size; i++) {
            for (int j = 1; j <= 7 && p < size; j++) {
                if (strList.get(p).compareTo("&nbsp;") != 0) {
                    courses[i][j] = strList.get(p);
                }
                p++;
            }
        }

        for (int i = 1; i <= 5; i++)
            for (int j = 1; j <= 7; j++) {
                if (courses[i][j] != null) {
                    courses[i][j] = courses[i][j].replaceAll("&nbsp;", "<br>");
                    courses[i][j].trim();
                    String[] info = courses[i][j].split("<br>");
                    //System.out.println("info length: " + info.length);
                    for (int k = 0; k < info.length; k += 4) {
                        result.add(new ClassInfo(info[k], info[k+1], info[k+2],
                                info[k+3], j, i));
                    }
                } else {
                    courses[i][j] = "无";
                    result.add(new ClassInfo(j, i));
                }
            }
        return result;
    }

    static public Map<String, Integer> getCourseType(String html) {
        Map<String, Integer> typeMap = new HashMap<String, Integer>();
        String regex = "<DIV align=center>(.*?)</DIV></TD>";
        Pattern pa = Pattern.compile(regex);
        Matcher ma = pa.matcher(html);
        String match, s;
        int except = 22;
        while (ma.find()) {
            match = ma.group(1);
            //s = match.replaceAll("<br>", " ");
            if (except == 0) {
                System.out.println(match);
            } else {
                except--;
            }
        }

        return typeMap;
    }

}
