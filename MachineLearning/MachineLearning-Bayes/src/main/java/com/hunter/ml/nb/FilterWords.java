package com.hunter.ml.nb;


import com.hunter.ml.utils.FileFilterGrabage;
import com.hunter.ml.utils.FileUtils;

import java.io.File;

public class FilterWords {
	public static void main(String[] args) {
		FilterWords.getFiles("F:/ML机器学习/data/data-分类","F:/ML机器学习/data/data-分类-filter/");
	}

	/**
	 * 获取文件
	 */
	public static void getFiles(String filepath,String output){
		File file = new File(filepath);
		if (!file.isDirectory()) {
			System.out.println("没有读取到文件，请验证！");
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					//获取文件的文件名
					String name=readfile.getName();
					//读取文件转成string
					String str= FileUtils.file2String(new File(filepath+"/"+name), "UTF-8");
					//过滤
					String cleanWords= FileFilterGrabage.filter(str);
					//输出成为新的文章，也可以load到内存中，但是考虑内存消耗不这么干
					FileUtils.string2File(cleanWords,output+name+"-filtered" );
				}
			}
		}
	}
}
