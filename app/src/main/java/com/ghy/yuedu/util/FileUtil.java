package com.ghy.yuedu.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by GHY on 2015/5/18.
 * 文件管理工具类
 */
public class FileUtil {

    private static String SDCardRoot = null;//SD卡根目录
    private static String YueDuRootPath = null;//YueDu文件夹根目录
    private static String FolderPath = null;//YueDu文件夹子目录

    /*
    * 创建文件夹
    * */
    public static String createFilePath(String filePath) {
        if (getSDCardRoot() != null) {
            File dirFile = new File(createRootPath() + filePath);// SDCard/.../YueDu/filePath
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            FolderPath = dirFile + File.separator;// SDCard/.../YueDu/filePath/
        }
        return FolderPath;
    }

    /*
    * 创建一个YueDu根目录
    * */
    private static String createRootPath() {
        File rootFile = new File(getSDCardRoot() + "YueDu");// SDCard/.../YueDu
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        YueDuRootPath = rootFile + File.separator;// SDCard/.../YueDu/
        return YueDuRootPath;
    }

    /*
    * 创建文件，参数为：保存路径和文件名
    * */
    public static File createFile(String path, String fileName) {
        File file = null;
        if (createFilePath(path) != null) {
            file = new File(FolderPath + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private static String getSDCardRoot() {
        return isSDCardExist() ? Environment.getExternalStorageDirectory() + File.separator : null;
    }

    /*
    * 向文件中写入数据
    * */
    public static boolean writeDataToFile(String folderPath, String fileName, String s) {

        File file = createFile(folderPath, fileName);// SDCard/.../YueDu/Cache/jsonCache.txt

        if (file == null || s == null) {
            return false;
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(s);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /*
    * 从文件中读取数据
    * */
    public static String readDataFromFile(String folderPath, String fileName) {

        File file = createFile(folderPath, fileName);// SDCard/.../YueDu/Cache/jsonCache.txt

        StringBuffer sb = new StringBuffer();
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /*
    * 升级时需要获取apk安装包的大小
    * 把apk安装包放到手机SD卡根目录下，执行此方法，查看log获取
    * */
    public static void getYueDuApkSize() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "YueDu1.1.apk";

        File file = new File(path);

        if (file.exists()) {
            long size = file.length();
            Log.i("apkSize---->>>>", size + "");
        } else {
            Log.i("apkSize---->>>>", "Not find Apk");
        }
    }
}
