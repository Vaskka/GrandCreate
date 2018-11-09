package com.vaskka.api.chain.user.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @program: GrandCreateApiSdk
 * @description: ZipUtil zip with codec-apache
 * @author: Vaskka
 * @create: 2018/11/5 6:19 PM
 **/
@Deprecated
public class ZipUtil {

    /**
     * 把经过压缩过的base64串解码解压并写入磁盘中
     * @param base64 压缩过的base64串
     * @param fileName 文件名
     * @param path 路径地址
     */
    public static void decode(String base64, String fileName, String path) {
        //解码
        byte[] data = Base64.decodeBase64(base64);
        data = unGZip(data);
        writeFile(data, fileName, path);
    }

    /**
     * 压缩b64得到为压缩b64
     * @param zippedBase64
     * @return
     */
    public static String decode(String zippedBase64) {
        byte[] data = Base64.decodeBase64(zippedBase64);
        data = unGZip(data);
        return Base64.encodeBase64String(data);
    }

    /**
     * 二进制文件写入文件
     * @param data 二进制数据
     * @param fileName 文件名
     * @param path 路径地址
     */
    public static void writeFile(byte[] data, String fileName, String path) {
        try
        {
            String url = path + "//" + fileName;
            FileUtils.writeByteArrayToFile(new File(url), data);
        }
        catch (IOException e)
        {
            System.out.println("写文件出错" + e);
        }
    }

    /**
     * 解壓Gzip
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data){
        byte[] b = null;
        try{
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1)
            {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        }
        catch (Exception ex){
            System.out.println("解压数据流出错！！" + ex);
        }
        return b;
    }

    /**
     * 读取文件并压缩数据然后转Base64编码
     * @param pathName 图片的绝对路径地址
     * @return
     */
    public static String base64(String pathName) {
        byte[] data = getPicData(pathName);
        if (data == null) {
            return null;
        }
        byte[] zipData = gZip(data);
        return Base64.encodeBase64String(zipData);
    }

    /**
     * @description 获取图片的二进制数据
     * @param pathName 图片的绝对路径地址
     * @return
     */
    public static byte[] getPicData(String pathName) {
        byte[] data = null;
        try {
            FileInputStream fi = new FileInputStream(pathName);
            int length = fi.available();
            data = new byte[length];
            fi.read(data);
            fi.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    /***
     * @description 压缩GZip
     * @param data 要压缩的二进制数据
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 将原生b64压缩
     * @param rawBase64 原生b64
     * @return 压缩后的b64
     */
    public static String fromBase64GetZipBase64(String rawBase64) {
        byte[] bytes = Base64.decodeBase64(rawBase64);
        byte[] zipBytes = gZip(bytes);
        return Base64.encodeBase64String(zipBytes);
    }
}
