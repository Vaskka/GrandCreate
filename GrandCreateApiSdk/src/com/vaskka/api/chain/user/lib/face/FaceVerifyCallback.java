package com.vaskka.api.chain.user.lib.face;

/**
 * 调用人脸验证后的接口
 */
public interface FaceVerifyCallback {

    /**
     * 调用之前
     * @param faceToken  标准face_token
     * @param imageBase64 用户拍摄的图像
     */
    public void beforeVerify(String faceToken, String imageBase64);


    /**
     * 调用之后
     * @param resultFaceToken 返回的结果
     */
    public void afterVerify(String resultFaceToken);
}
