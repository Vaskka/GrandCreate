package com.vaskka.grand.user.util;

import com.vaskka.grand.user.request.BaseRequestObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @program: User
 * @description: HttpUtil 操作http的请求
 * @author: Vaskka
 * @create: 2018/8/13 下午3:36
 **/

public class HttpUtil {

    private static HttpURLConnection getConnection(String url){
        HttpURLConnection connection = null;
        try {
            // 打开和URL之间的连接
            URL postUrl = new URL(url);
            connection = (HttpURLConnection) postUrl.openConnection();
            // 设置通用的请求属性
            connection.setDoOutput(false);//posp请求改为TRUE
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Accept-Charset", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * post
     * @param _url String url
     * @param obj BaseRequestObject obj
     * @return String json风格字符串
     */
    public static String post(String _url, BaseRequestObject obj) {

        try{
            //创建连接
            URL url = new URL(_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);

            //connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");

            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            out.write(obj.toString().getBytes(StandardCharsets.UTF_8));//这样可以处理中文乱码问题
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuilder sb = new StringBuilder("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get
     * @param _url 原始url
     * @param params get 参数
     * @return json风格字符串
     */
    public static String get(String _url, Map<String, String> params) {
        String line = "";

        StringBuilder httpResults = new StringBuilder();

        try {
            int count = 0;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (count == 0) {
                    _url += "?";
                }
                else {
                    _url += "&";
                }

                _url += (entry.getKey() + "=" + entry.getValue());
                count++;
            }

            HttpURLConnection urlConnection = getConnection(_url);
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            BufferedReader reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),StandardCharsets.UTF_8));
            //line=bf.readLine();
            while ((line = reader.readLine()) != null) {
                httpResults.append(line);
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResults.toString();
    }
}
