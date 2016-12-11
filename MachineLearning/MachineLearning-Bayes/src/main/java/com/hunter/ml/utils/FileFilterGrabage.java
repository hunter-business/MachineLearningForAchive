package com.hunter.ml.utils;

public class FileFilterGrabage {
	public static String filter(String str){
		String replaceStr="";
		//利用规则去过滤数据，规则有限不是最好的过滤器
		replaceStr=str.replace("“", "").replace("”", "").replace("，", "").replace("、", "")
				.replace("。", "").replace("[|", "").replace("|]", "").replace("《", "")
				.replace("？", "").replace("！", "").replace("》", "").replace("）", "")
				.replace("@", "").replace("#", "").replace("&", "").replace("*", "").replace("\n", "")
				.replace("~", "").replace("（", "").replace("：", "").replace("  ", " ").replace(":", "")
				.replace("?", "").replace(":?", "").replace("!", "").replace("【", "").replace("】", "")
				.replace("★", "").replace("；", "")
				//中文字过滤
				.replace("了", "").replace("的", "").replace("嗯", "").replace("作者", "")
				.replace("记者", "").replace("分享", "").replace("了", "").replace("图", "")
				.replace("地", "").replace("得", "").replace("恩", "").replace("图文", "");
		return replaceStr;
	}
}
