package com.vaskka.api.chain.user.lib.face;

import com.vaskka.api.chain.user.lib.User;

public interface FaceRegisterCallback {

    /**
     * 执行前调用
     * @param user 用户对象
     * @param imgBase64 用户刚刚拍摄的人脸图片
     */
    public void beforeRegister(User user, String imgBase64);

    /**
     * 执行后调用
     * @param resultFaceToken 返回的face_token 结果
     */
    public void afterRegister(String resultFaceToken);
}
