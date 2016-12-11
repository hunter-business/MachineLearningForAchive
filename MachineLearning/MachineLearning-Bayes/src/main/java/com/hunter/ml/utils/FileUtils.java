package com.hunter.ml.utils;

import java.io.*;

public class FileUtils {
	/**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功�?
     *
     * @param  res 字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile,true));
            char buf[] = new char[1024];         //字符缓冲
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                    bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                        bufferedReader.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
            }
        }
        return flag;
    }
    /**
     * 文本文件转换为指定编码的字符�?
     *
     * @param file  文本文件
     * @param encoding 编码类型
     * @return 转换后的字符�?
     * @throws IOException
     */
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
                if (encoding == null || "".equals(encoding.trim())) {
                        reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                } else {
                        reader = new InputStreamReader(new FileInputStream(file),encoding);
                }
                //将输入流写入输出�?
                char[] buffer = new char[1024];
                int n = 0;
                while (-1 != (n = reader.read(buffer))) {
                        writer.write(buffer, 0, n);
                }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        if (reader != null)
            try {
                    reader.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        //返回转换结果 
        if (writer != null)
            return writer.toString();
        else return null;
    }

}
