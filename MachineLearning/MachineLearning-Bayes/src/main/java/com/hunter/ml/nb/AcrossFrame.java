package com.hunter.ml.nb;

import com.hunter.ml.utils.FileUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 主要用来执行贝叶斯算法类
 * Created by Ken.Yu on 2016/12/3.
 */
public class AcrossFrame {
    //使用交叉验证，将分区的数据做成全局
    private static List<String> partition = new ArrayList<String>();
    //    private static List<String> partition1 = new ArrayList<String>();
//    private static List<String> partition2 = new ArrayList<String>();
//    private static List<String> partition3 = new ArrayList<String>();
//    private static List<String> partition4 = new ArrayList<String>();
//    private static List<String> partition5 = new ArrayList<String>();
//    private static List<String> partition6 = new ArrayList<String>();
//    private static List<String> partition7 = new ArrayList<String>();
//    private static List<String> partition8 = new ArrayList<String>();
//    private static List<String> partition9 = new ArrayList<String>();
    //读取的文件路径
    private static String path = "F:/DataTest/Bayes/";
    //输出文件路径
    private static String GDFLPath = "F:/DataTest/output/GDFL/";
    private static String JRDTPath = "F:/DataTest/output/JRDT/";
    private static String ZDZZPath = "F:/DataTest/output/ZDZZ/";
    private static String GJYWPath = "F:/DataTest/output/GJYW/";
    private static String HGJJPath = "F:/DataTest/output/HGJJ/";
    private static String SCHQPath = "F:/DataTest/output/SCHQ/";
    private static String GJZCPath = "F:/DataTest/output/GJZC/";
    private static String HEQSPath = "F:/DataTest/output/HEQS/";
    private static String HGDTPath = "F:/DataTest/output/HGDT/";

    private static int countfile = 0;
    //类别中出现的词做全局
    private static Map<String, String> GDFLXjYiMap = new HashMap<String, String>();
    private static Map<String, String> JRDTXjYiMap = new HashMap<String, String>();
    private static Map<String, String> ZDZZXjYiMap = new HashMap<String, String>();
    private static Map<String, String> GJYWXjYiMap = new HashMap<String, String>();
    private static Map<String, String> HGJJXjYiMap = new HashMap<String, String>();
    private static Map<String, String> SCHQXjYiMap = new HashMap<String, String>();
    private static Map<String, String> GJZCXjYiMap = new HashMap<String, String>();
    private static Map<String, String> HEQSXjYiMap = new HashMap<String, String>();
    private static Map<String, String> HGDTXjYiMap = new HashMap<String, String>();
    private static String GD;
    private static String JR;
    private static String ZD;
    private static String GJ;
    private static String HG;
    private static String SC;
    private static String GJZ;
    private static String HE;
    private static String HGD;
    private static int Accumulate = 0;

    public static void main(String[] args) {
        try {
            //注意：启动此main方法之前请先执行FilterWords中的main方法
            //读取文件，分区
            AcrossFrame.readfile(path);
            //交叉验证框架
            AcrossFrame.modeling(partition);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取文件夹下的所有文件
     */
    public static void readfile(String filepath) {
//    	int partition=0;
        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("没有读取到文件，请验证！");
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        //第一个是文件绝对路径第二个是而文件名
                        String path = readfile.getPath();
                        String name = readfile.getName();
//                    	partition=AcrossFrame.partition(name,10);
                        //将不同的partition封装到集合中
//                    	AcrossFrame.par2List(partition,path);
                        partition.add(path);
                    } else if (readfile.isDirectory()) {
                        //文件递归读取
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
    }


    /**
     * 封装建模的方法
     */
    public static void modeling(List<String> lists) {
        int Traing = 0;
        int Yi = 0;
        List<List<String>> listfile = new ArrayList<List<String>>();
        listfile.add(partition);
        AcrossFrame.priorProbability(Traing, listfile);
        //初始化Traing=0
        Traing = countfile;
    }

    /**
     * 计算概率
     */
    public static void priorProbability(int countYi, List<List<String>> Yi) {
        int GDFL = 0;
        int JRDT = 0;
        int ZDZZ = 0;
        int GJYW = 0;
        int HGJJ = 0;
        int SCHQ = 0;
        int GJZC = 0;
        int HEQS = 0;
        int HGDT = 0;
        List<String> GDFLList = new ArrayList<String>();
        List<String> JRDTList = new ArrayList<String>();
        List<String> ZDZZList = new ArrayList<String>();
        List<String> GJYWList = new ArrayList<String>();
        List<String> HGJJList = new ArrayList<String>();
        List<String> SCHQList = new ArrayList<String>();
        List<String> GJZCList = new ArrayList<String>();
        List<String> HEQSList = new ArrayList<String>();
        List<String> HGDTList = new ArrayList<String>();

        for (List<String> yi : Yi) {
            //统计各个类的文件数
            for (String filepath : yi) {
                Boolean GDF = filepath.contains("GDFL");
                Boolean JRD = filepath.contains("JRDT");
                Boolean ZDZ = filepath.contains("ZDZZ");
                Boolean GJY = filepath.contains("GJYW");
                Boolean HGJ = filepath.contains("HGJJ");
                Boolean SCH = filepath.contains("SCHQ");
                Boolean GJZ = filepath.contains("GJZC");
                Boolean HEQ = filepath.contains("HEQS");
                Boolean HGD = filepath.contains("HGDT");

                //计算的文件数，并将所有的文件归类
//  				if(GDF==true){GDFL+=1;GDFLList.add(filepath);}
                if (JRD == true) {
                    JRDT += 1;
                    JRDTList.add(filepath);
                }
                if (ZDZ == true) {
                    ZDZZ += 1;
                    ZDZZList.add(filepath);
                }
                if (GJY == true) {
                    GJYW += 1;
                    GJYWList.add(filepath);
                }
                if (HGJ == true) {
                    HGJJ += 1;
                    HGJJList.add(filepath);
                }
                if (SCH == true) {
                    SCHQ += 1;
                    SCHQList.add(filepath);
                }
                if (GJZ == true) {
                    GJZC += 1;
                    GJZCList.add(filepath);
                }
                if (HEQ == true) {
                    HEQS += 1;
                    GJZCList.add(filepath);
                }
                if (HGD == true) {
                    HGDT += 1;
                    HGDTList.add(filepath);
                }
            }
        }
        //计算出P(Yi)
        DecimalFormat df = new DecimalFormat("0.0000");//格式化小数
		float GDFLYi= (float)GDFL/countYi;
		GD = df.format(GDFLYi);
        float JRDTYi = (float) JRDT / countYi;
        JR = df.format(JRDTYi);
        float ZDZZYi = (float) ZDZZ / countYi;
        ZD = df.format(ZDZZYi);
        float GJYWYi = (float) GJYW / countYi;
        GJ = df.format(GJYWYi);
        float HGJJYi = (float) HGJJ / countYi;
        HG = df.format(HGJJYi);
        float SCHQYi = (float) SCHQ / countYi;
        SC = df.format(SCHQYi);
        float GJZCYi = (float) GJZC / countYi;
        GJZ = df.format(GJZCYi);
		float HEQSYi= (float)HEQS/countYi;
		HE = df.format(HEQSYi);
		float HGDTYi= (float)HGDT/countYi;
		HGD = df.format(HGDTYi);
        //----------------------------------------
        //计算P(Xj|Yi)=count(Xi,Yi)/count(Yi)
		Map<String,Integer> mapGDFL=AcrossFrame.WordCounts(GDFLList);
        Map<String, Integer> mapJRDT = AcrossFrame.WordCounts(JRDTList);
        Map<String, Integer> mapZDZZ = AcrossFrame.WordCounts(ZDZZList);
        Map<String, Integer> mapGJYW = AcrossFrame.WordCounts(GJYWList);
        Map<String, Integer> mapHGJJ = AcrossFrame.WordCounts(HGJJList);
        Map<String, Integer> mapSCHQ = AcrossFrame.WordCounts(SCHQList);
        Map<String, Integer> mapGJZC = AcrossFrame.WordCounts(GJZCList);
        Map<String, Integer> mapHEQS = AcrossFrame.WordCounts(HEQSList);
		Map<String,Integer> mapHGDT=AcrossFrame.WordCounts(HGDTList);
		for(Map.Entry<String,Integer> entry: mapGDFL.entrySet()) {
			 String GDFLKey=entry.getKey();
			 int GDFLValue=entry.getValue();
			 //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
			 float XjYi= (float)GDFLValue+1/GDFL+2;
			 String bXjYi = df.format(XjYi);
			 GDFLXjYiMap.put(GDFLKey, bXjYi);
		}
        for (Map.Entry<String, Integer> entry : mapJRDT.entrySet()) {
            String JRDTKey = entry.getKey();
            int JRDTValue = entry.getValue();
            //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
            float XjYi = (float) JRDTValue + 1 / JRDT + 2;
            String aXjYi = df.format(XjYi);
            JRDTXjYiMap.put(JRDTKey, aXjYi);
        }
        for (Map.Entry<String, Integer> entry : mapZDZZ.entrySet()) {
            String ZDZZKey = entry.getKey();
            int ZDZZValue = entry.getValue();
            //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
            float XjYi = (float) ZDZZValue + 1 / ZDZZ + 2;
            String sXjYi = df.format(XjYi);
            ZDZZXjYiMap.put(ZDZZKey, sXjYi);
        }
        for (Map.Entry<String, Integer> entry : mapGJYW.entrySet()) {
            String GJYWKey = entry.getKey();
            int GJYWValue = entry.getValue();
            //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
            float XjYi = (float) GJYWValue + 1 / GJYW + 2;
            String sXjYi = df.format(XjYi);
            GJYWXjYiMap.put(GJYWKey, sXjYi);
        }
        for (Map.Entry<String, Integer> entry : mapHGJJ.entrySet()) {
            String HGJJKey = entry.getKey();
            int HGJJValue = entry.getValue();
            //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
            float XjYi = (float) HGJJValue + 1 / HGJJ + 2;
            String sXjYi = df.format(XjYi);
            HGJJXjYiMap.put(HGJJKey, sXjYi);
        }
        for (Map.Entry<String, Integer> entry : mapSCHQ.entrySet()) {
            String SCHQKey = entry.getKey();
            int SCHQValue = entry.getValue();
            //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
            float XjYi = (float) SCHQValue + 1 / SCHQ + 2;
            String sXjYi = df.format(XjYi);
            SCHQXjYiMap.put(SCHQKey, sXjYi);
        }
        for (Map.Entry<String, Integer> entry : mapGJZC.entrySet()) {
            String GJZCKey = entry.getKey();
            int GJZCValue = entry.getValue();
            //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
            float XjYi = (float) GJZCValue + 1 / GJZC + 2;
            String sXjYi = df.format(XjYi);
            GJZCXjYiMap.put(GJZCKey, sXjYi);
        }
		for(Map.Entry<String,Integer> entry: mapHEQS.entrySet()) {
			 String HEQSKey=entry.getKey();
			 int HEQSValue=entry.getValue();
			 //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
			 float XjYi= (float)HEQSValue+1/HEQS+2;
			 String sXjYi = df.format(XjYi);
			 HEQSXjYiMap.put(HEQSKey, sXjYi);
		}
		for(Map.Entry<String,Integer> entry: mapHGDT.entrySet()) {
			 String HGDTKey=entry.getKey();
			 int HGDTValue=entry.getValue();
			 //平滑操作一下（优化点）效果会好很多，保证有些没有出现过的词也算进去了
			 float XjYi= (float)HGDTValue+1/HGDT+2;
			 String sXjYi = df.format(XjYi);
			 HGDTXjYiMap.put(HGDTKey, sXjYi);
		}
    }

    /**
     * 预测
     */
//  	public static void predictionList(List<String> lists){
//  		//真正要预测的集合
//  		AcrossFrame.prediction(partition);
//  	}

    /**
     * 真正做预测
     */
    public static void prediction(List<String> lists) {
        double GDFLXjYi = 0;
        double JRDTXjYi = 0;
        double ZDZZXjYi = 0;
        double GJYWXjYi = 0;
        double HGJJXjYi = 0;
        double SCHQXjYi = 0;
        double GJZCXjYi = 0;
        double HEQSXjYi = 0;
        double HGDTXjYi = 0;
        Accumulate = ++Accumulate;
        for (String path : lists) {
            String words[] = FileUtils.file2String(new File(path), "GBK").split(" ");
            for (int i = 0; i < words.length; i++) {
                //在GDFL中的概率
//  				if(GDFLXjYiMap.containsKey(words[i])){
//  					GDFLXjYi += Math.log(Float.parseFloat(GDFLXjYiMap.get(words[i])));
//  				}
                //在JRDT中的概率
                if (JRDTXjYiMap.containsKey(words[i])) {
                    JRDTXjYi += Math.log(Float.parseFloat(JRDTXjYiMap.get(words[i])));
                }
                //在ZDZZ中的概率
                if (ZDZZXjYiMap.containsKey(words[i])) {
                    ZDZZXjYi += Math.log(Float.parseFloat(ZDZZXjYiMap.get(words[i])));
                }
                if (GJYWXjYiMap.containsKey(words[i])) {
                    GJYWXjYi += Math.log(Float.parseFloat(GJYWXjYiMap.get(words[i])));
                }
                //在HGJJ中的概率
                if (HGJJXjYiMap.containsKey(words[i])) {
                    HGJJXjYi += Math.log(Float.parseFloat(HGJJXjYiMap.get(words[i])));
                }
                //在SCHQ中的概率
                if (SCHQXjYiMap.containsKey(words[i])) {
                    SCHQXjYi += Math.log(Float.parseFloat(SCHQXjYiMap.get(words[i])));
                }
                if (GJZCXjYiMap.containsKey(words[i])) {
                    GJZCXjYi += Math.log(Float.parseFloat(GJZCXjYiMap.get(words[i])));
                }
                //在HEQS中的概率
//  				if(HEQSXjYiMap.containsKey(words[i])){
//  					HEQSXjYi += Math.log(Float.parseFloat(HEQSXjYiMap.get(words[i])));
//  				}
                //在HGDT中的概率
//  				if(HGDTXjYiMap.containsKey(words[i])){
//  					HGDTXjYi += Math.log(Float.parseFloat(HGDTXjYiMap.get(words[i])));
//  				}
            }
            //指数计算的时候负数越大则概率就越小
            String type = AcrossFrame.compare(
//  					GDFLXjYi+Math.log(Float.parseFloat(GD)),
                    JRDTXjYi + Math.log(Float.parseFloat(JR)), ZDZZXjYi + Math.log(Float.parseFloat(ZD))
                    , GJYWXjYi + Math.log(Float.parseFloat(GJ)), HGJJXjYi + Math.log(Float.parseFloat(HG))
                    , SCHQXjYi + Math.log(Float.parseFloat(SC)), GJZCXjYi + Math.log(Float.parseFloat(GJZ))
//  					,HEQSXjYi+Math.log(Float.parseFloat(HE)),HGDTXjYi+Math.log(Float.parseFloat(HGD))
            );
            String str = FileUtils.file2String(new File(path), "UTF-8");
            String subPath = path.substring(18, path.length());
            if (type.equals("GDFL")) {
//  				FileUtils.string2File(str, GDFLPath+Accumulate+"/"+subPath);
            } else if (type.equals("JRDT")) {
                FileUtils.string2File(str, JRDTPath + Accumulate + "/" + subPath);
            } else if (type.equals("ZDZZ")) {
                FileUtils.string2File(str, ZDZZPath + Accumulate + "/" + subPath);
            } else if (type.equals("GJYW")) {
                FileUtils.string2File(str, GJYWPath + Accumulate + "/" + subPath);
            } else if (type.equals("HGJJ")) {
                FileUtils.string2File(str, HGJJPath + Accumulate + "/" + subPath);
            } else if (type.equals("SCHQ")) {
                FileUtils.string2File(str, SCHQPath + Accumulate + "/" + subPath);
            } else if (type.equals("GJZC")) {
                FileUtils.string2File(str, GJZCPath + Accumulate + "/" + subPath);
            } else if (type.equals("HEQS")) {
//  				FileUtils.string2File(str, HEQSPath+Accumulate+"/"+subPath);
            } else {
//  				FileUtils.string2File(str, HGDTPath+Accumulate+"/"+subPath);
            }
            //初始化值
            GDFLXjYi = 0;
            JRDTXjYi = 0;
            ZDZZXjYi = 0;
            GJYWXjYi = 0;
            HGJJXjYi = 0;
            SCHQXjYi = 0;
            GJZCXjYi = 0;
            HEQSXjYi = 0;
            HGDTXjYi = 0;
        }
        //调用评测框架
//		ValidationFrame.validation(lists,businessPath+Accumulate+"/",autoPath+Accumulate+"/",sportsPath+Accumulate+"/");
    }

    /**
     * 比较各个类概率哪类最大，并返回类型
     */
    public static String compare(
//  			double GDFL,
            double JRDT, double ZDZZ, double GJYW, double HGJJ,
            double SCHQ, double GJZC
//  			,double HEQS,double HGDT
    ) {
        String type = "";
        Map<String, Double> map = new HashMap<String, Double>();
//  		map.put("GDFL", GDFL);
        map.put("JRDT", JRDT);
        map.put("ZDZZ", ZDZZ);
        map.put("GJYW", GJYW);
        map.put("HGJJ", HGJJ);
        map.put("SCHQ", SCHQ);
        map.put("GJZC", GJZC);
//  		map.put("HEQS", HEQS);
//  		map.put("HGDT", HGDT);
        Collection<Double> c = map.values();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        Double dou = (Double) obj[5];
        Set<String> kset = map.keySet();
        for (String ks : kset) {
            if (dou.equals(map.get(ks))) {
                type = ks;
            }
        }
        return type;
    }

    /**
     * 分别计算在各个类文章中出现X单词的文章数
     */
    public static Map<String, Integer> WordCounts(List<String> list) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        //构建各个类的词典
        for (String path : list) {
            String file = FileUtils.file2String(new File(path), "GBK");
            String fileArr[] = file.split(" ");
            for (int i = 0; i < fileArr.length; i++) {
                map.put(fileArr[i], 0);
            }
        }
        //计算在各个类文章中出现X单词的文章数
        for (String path : list) {
            String file = FileUtils.file2String(new File(path), "GBK");
            String Arr[] = file.split(" ");
            for (int i = 0; i < Arr.length; i++) {
                if ("".equals(Arr[i])) map.remove(Arr[i]);
                if (map.containsKey(Arr[i])) {
                    String word = Arr[i];
                    int count = map.get(word) + 1;
                    map.put(word, count);
                }
            }
        }
        return map;
    }

    /**
     * 文件打乱分区
     */
    public static int partition(Object obj, int numPartitions) {
        int partition = 0;
        if (obj instanceof String) {
            String key = (String) obj;
            int hashCode = Math.abs(key.hashCode());
            partition = hashCode % numPartitions;
        } else {
            partition = obj.toString().length() % numPartitions;
        }
        return partition;
    }
}

