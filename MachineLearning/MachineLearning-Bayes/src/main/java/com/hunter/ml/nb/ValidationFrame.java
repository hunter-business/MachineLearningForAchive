package com.hunter.ml.nb;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;

/**
 * 评测框架
 */
public class ValidationFrame {
	public static void validation(List<String> lists,String businessPath,String autoPath,String sportsPath){
		//实际类别变量
		int businessReallyFiles=0;
		int autoReallyFiles=0;
		int sportsReallyFiles=0;
		//实际类别
		for(String path:lists){
			if(path.contains("business")){
				businessReallyFiles+=1;
			}
			if(path.contains("auto")){
				autoReallyFiles+=1;
			}
			if(path.contains("sports")){
				sportsReallyFiles+=1;
			}
		}
		//分类器预测的值x
		//business
		double preBusinessCount= ValidationFrame.validation(businessPath, "business");
		double preBusinessInAutoCount= ValidationFrame.validation(autoPath, "business");
		double preBusinessInSportCount= ValidationFrame.validation(sportsPath, "business");
		//auto
		double preAutoCount= ValidationFrame.validation(autoPath, "auto");
		double preAutoInBusinessCount= ValidationFrame.validation(businessPath, "auto");
		double preAutoInSportsCount= ValidationFrame.validation(sportsPath, "auto");
		//sports
		double preSportsCount= ValidationFrame.validation(sportsPath, "sports");
		double preSportsInBusinessCount= ValidationFrame.validation(businessPath, "sports");
		double preSportsInAutoCount= ValidationFrame.validation(autoPath, "sports");
		//----------------------------------------
		double AccyracyBus=div(preBusinessCount+preAutoCount+preSportsCount,businessReallyFiles+autoReallyFiles+sportsReallyFiles);
		double PrecisionBus=div(preBusinessCount,preBusinessCount+preAutoInBusinessCount+preSportsInBusinessCount);
		double RecallBus=div(preBusinessCount,preBusinessCount+preBusinessInAutoCount+preBusinessInSportCount);
		double F1Bus=div(2*PrecisionBus*RecallBus,PrecisionBus+RecallBus);
		//----------------------------------------
		double PrecisionAuto=div(preAutoCount,preAutoCount+preSportsInAutoCount+preBusinessInAutoCount);
		double RecallAuto=div(preAutoCount,preAutoCount+preAutoInBusinessCount+preAutoInSportsCount);
		double F1Auto=div(2*PrecisionAuto*RecallAuto,RecallAuto+PrecisionAuto);
		//----------------------------------------
		double PrecisionSport=div(preSportsCount,preBusinessInSportCount+preAutoInSportsCount+preSportsCount);
		double RecallSport=div(preSportsCount,preSportsCount+preSportsInAutoCount+preSportsInBusinessCount);
		double F1Sport=div(2*PrecisionSport*RecallSport,RecallSport+PrecisionSport);
		//----------------------------------------
		NumberFormat fmt = NumberFormat.getPercentInstance();
		fmt.setMaximumFractionDigits(2);
		System.out.println();System.out.println();
		System.out.println("***********************************");
		System.out.println("            business  auto  sports ");
		System.out.println("business("+businessReallyFiles+")"+"\t"+preBusinessCount+"\t"+preBusinessInAutoCount+"\t"+preBusinessInSportCount);
		System.out.println("auto("+autoReallyFiles+")"+"\t"+preAutoInBusinessCount+"\t"+preAutoCount+"\t"+preAutoInSportsCount);
		System.out.println("sports("+sportsReallyFiles+")"+"\t"+preSportsInBusinessCount+"\t"+preSportsInAutoCount+"\t"+preSportsCount);
		System.out.println("-----------------------------------");
		System.out.println("准确度Accyracy："+fmt.format(AccyracyBus));
		System.out.println("精确率Pecision(business):"+fmt.format(PrecisionBus));
		System.out.println("召回率Recall(business):"+fmt.format(RecallBus));
		System.out.println("F1--2*P*R/(P+R)(business):"+fmt.format(F1Bus));
		System.out.println("-----------------------------------");
		System.out.println("精确率Precision(auto):"+fmt.format(PrecisionAuto));
		System.out.println("召回率Recall(auto):"+fmt.format(RecallAuto));
		System.out.println("F1--2*P*R/(P+R)(auto):"+fmt.format(F1Auto));
		System.out.println("-----------------------------------");
		System.out.println("精确率Precision(sports):"+fmt.format(PrecisionSport));
		System.out.println("召回率Recall(sports):"+fmt.format(RecallSport));
		System.out.println("F1--2*P*R/(P+R)(sports):"+fmt.format(F1Sport));
	}

	/**
	 * 封装通用方法
	 */
	public static int validation(String path,String regName){
		int count=0;
		File file = new File(path);
		if (!file.isDirectory()) {
			System.out.println("没有读取到文件，请验证！");
		} else if (file.isDirectory()) {
			//获取business文件夹本身含有business文件
			int fileCount = file.list().length;
			String[] filelist = file.list();
			for (int i = 0; i < fileCount; i++) {
				File readfile = new File(path + "\\" + filelist[i]);
				String name=readfile.getName();
				if(name.contains(regName)){
					count+=1;
				}
			}
		}
		return count;
	}
	public static Double div(double a, double b) {
		double x = a / b;
		return x;
	}
}
