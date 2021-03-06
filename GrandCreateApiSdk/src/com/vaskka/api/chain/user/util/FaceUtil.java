package com.vaskka.api.chain.user.util;


import com.vaskka.api.chain.user.lib.face.Face;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @program: GrandCreateApiSdk
 * @description: FaceUtil 人脸识别api封装
 * @author: Vaskka
 * @create: 2018/11/4 9:01 PM
 **/

public class FaceUtil {


    /**
     * 将本地url指向image转化为base64
     * @param imgFile image的路径
     * @return base64 结果
     */
    public static String imageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理


        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将人脸信息注册在百度云上
     * @param imgBase64 拍摄的base64图像
     * @param userId uid
     * @return face_token
     */
    public static String addUserFace(String imgBase64, String userId) {
        Face f = Face.getInstance();
        f.initService();

        try {
            JSONObject o = f.addUser(userId, imgBase64, "BASE64");
            if (o.getString("error_msg").equals("SUCCESS")) {
                return o.getJSONObject("result").getString("face_token");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }


    /**
     * 验证两个人脸的相似度
     * @param faceToken 标准face_token
     * @param imgBase64 待验证的图像base64
     * @return 验证后的图像face_token (未通过返回null)
     */
    public static String verifyFace(String faceToken, String imgBase64) {
        Face f = Face.getInstance();
        f.initService();
        try {
            JSONObject o = f.faceVerify(faceToken, "FACE_TOKEN", imgBase64, "BASE64");
            if (o.getJSONObject("result").getDouble("score") >= ConstUtil.STD_FACE_SCORE) {
                return o.getJSONObject("result").getJSONArray("face_list").getJSONObject(1).getString("face_token");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return  null;
    }
}
