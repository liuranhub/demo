package com.unisinsight.demo.question;

public class SubStr {
    public static String minSubStr(String source, String target) {
        if(source == null || target == null){
            return null;
        }

        String minStr = "";
        int minLen = Integer.MAX_VALUE;
        for(int i = 0 ; i < source.length() ;i ++) {
            // 优化
            String subStr = method(source.substring(i), target);
            if (subStr == null ) {
                continue;
            }

            if (subStr.length() < minLen) {
                minStr = subStr;
                minLen = subStr.length();
            }
        }

        return minStr;
    }

    public static String method(String source, String target){
        if(source == null || target == null){
            return null;
        }
        int firstCharIndex = -1;
        int preCharIndex = -1;
        for(int i = 0 ;i < target.length() ;i ++) {
            int index = source.indexOf("" + target.charAt(i));
            if (index < 0) {
                return null;
            }
            if(i == 0) {
                firstCharIndex = index;
            }
            if(index <= preCharIndex) {
                return null;
            }
            preCharIndex = index;
        }
        return source.substring(firstCharIndex, preCharIndex + 1);
    }

    public static void main(String[] args) {
        System.out.println(minSubStr("BPDAUNZHGAHSIWBADNC", "BDN"));
    }
}
