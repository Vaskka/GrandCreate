package com.vaskka.api.chain.user.lib.face;

/**
 * 调用人脸验证后的接口
 */
public interface FaceVerifyCallback {
    public void afterVerify(String resultFaceToken);
}
