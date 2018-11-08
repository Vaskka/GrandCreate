package com.vaskka.api.chain.user.lib.face;

/**
 * @program: GrandCreateApiSdk
 * @description: Face baidu face api
 * @author: Vaskka
 * @create: 2018/11/4 9:22 PM
 **/

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.util.Base64Util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

public class Face {
    AipFace client;
    public static final String APP_ID = "11771849";
    public static final String API_KEY = "FvOlaFZfkeWajZIHcLYHKgIx";
    public static final String SECRET_KEY = "I8U0AWElwHguhiKGB5VGHZPsKrfYODSo";
    private static Face instance;

    private Face() {
    }

    public static Face getInstance() {
        if (instance == null) {
            instance = new Face();
        }

        return instance;
    }

    public void initService() {
        this.client = new AipFace("11771849", "FvOlaFZfkeWajZIHcLYHKgIx", "I8U0AWElwHguhiKGB5VGHZPsKrfYODSo");
        this.client.setConnectionTimeoutInMillis(2000);
        this.client.setSocketTimeoutInMillis(60000);
    }

    protected void initUserGroup() {
        HashMap<String, String> options = new HashMap();
        String groupId_SECURITY = "security";
        String groupId_NORMAL = "normal";
        JSONObject response1 = this.client.groupAdd(groupId_SECURITY, options);
        System.out.println(response1.toString(2));
        int t1 = Integer.parseInt(response1.get("error_code").toString());
        if (t1 == 0) {
            System.out.println("init security group,done!");
        } else if (t1 == 223101) {
            System.out.println("SECURITY GROUP EXISTS!");
        }

        JSONObject response2 = this.client.groupAdd(groupId_NORMAL, options);
        int t2 = Integer.parseInt(response2.get("error_code").toString());
        if (t2 == 0) {
            System.out.println("init normal group,done!");
        } else if (t2 == 223101) {
            System.out.println("NORMAL GROUP EXISTS!");
        }

        System.out.println(response2.toString(2));
    }

    public JSONObject addUser(String userId, String img, String imgType) {
        HashMap<String, String> options = new HashMap();
        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "NORMAL");
        String image = null;
        byte var7 = -1;
        switch(imgType.hashCode()) {
            case 84303:
                if (imgType.equals("URL")) {
                    var7 = 0;
                }
                break;
            case 1952093519:
                if (imgType.equals("BASE64")) {
                    var7 = 1;
                }
        }

        switch(var7) {
            case 0:
                image = this.URLtoBase64(img);
                break;
            case 1:
                image = img;
        }

        return this.client.addUser(image, "BASE64", "normal", userId, options);
    }

    public JSONObject updateUser(String userId, String img, String imgType) {
        HashMap<String, String> options = new HashMap();
        String image = null;
        byte var7 = -1;
        switch(imgType.hashCode()) {
            case 84303:
                if (imgType.equals("URL")) {
                    var7 = 0;
                }
                break;
            case 1952093519:
                if (imgType.equals("BASE64")) {
                    var7 = 1;
                }
        }

        switch(var7) {
            case 0:
                image = this.URLtoBase64(img);
                break;
            case 1:
                image = img;
        }

        return this.client.updateUser(image, "BASE64", "normal", userId, options);
    }

    public JSONObject deleteFace(String userId, String faceToken) {
        HashMap<String, String> options = new HashMap();
        return this.client.faceDelete(userId, "normal", faceToken, options);
    }

    /** @deprecated */
    protected void old_deleteUser(String userName) {
        HashMap<String, String> options = new HashMap();
        String groupId = "normal";
        JSONObject res = this.client.deleteUser(groupId, userName, options);
        System.out.println(res.toString(2));
    }

    public JSONObject deleteUser(String userId) {
        HashMap<String, String> options = new HashMap();
        String groupId = "normal";
        return this.client.deleteUser(groupId, userId, options);
    }

    /** @deprecated */
    protected void old_getUserFace(String userId) {
        HashMap<String, String> options = new HashMap();
        String groupId = "normal";
        JSONObject res = this.client.faceGetlist(userId, groupId, options);
        System.out.println(res.toString(2));
    }

    public JSONObject getUserFace(String userId) {
        HashMap<String, String> options = new HashMap();
        String groupId = "normal";
        return this.client.faceGetlist(userId, groupId, options);
    }

    public JSONObject faceVerify(String face1, String face1Type, String face2, String face2Type) {
        MatchRequest request1 = null;
        MatchRequest request2 = null;
        byte var14 = -1;
        switch(face1Type.hashCode()) {
            case -1974273673:
                if (face1Type.equals("FACE_TOKEN")) {
                    var14 = 2;
                }
                break;
            case 84303:
                if (face1Type.equals("URL")) {
                    var14 = 0;
                }
                break;
            case 1952093519:
                if (face1Type.equals("BASE64")) {
                    var14 = 1;
                }
        }

        switch(var14) {
            case 0:
                String face1base64 = this.URLtoBase64(face1);
                request1 = new MatchRequest(face1base64, "BASE64");
                break;
            case 1:
                request1 = new MatchRequest(face1, "BASE64");
                break;
            case 2:
                request1 = new MatchRequest(face1, "FACE_TOKEN");
        }

        var14 = -1;
        switch(face2Type.hashCode()) {
            case -1974273673:
                if (face2Type.equals("FACE_TOKEN")) {
                    var14 = 2;
                }
                break;
            case 84303:
                if (face2Type.equals("URL")) {
                    var14 = 0;
                }
                break;
            case 1952093519:
                if (face2Type.equals("BASE64")) {
                    var14 = 1;
                }
        }

        switch(var14) {
            case 0:
                String face2base64 = this.URLtoBase64(face2);
                request2 = new MatchRequest(face2base64, "BASE64");
                break;
            case 1:
                request2 = new MatchRequest(face2, "BASE64");
                break;
            case 2:
                request2 = new MatchRequest(face2, "FACE_TOKEN");
        }

        ArrayList<MatchRequest> requests = new ArrayList();
        if (request1 != null) {
            requests.add(request1);
        }

        if (request2 != null) {
            requests.add(request2);
        }

        return this.client.match(requests);
    }

    protected String URLtoBase64(String url) {
        String base64 = null;

        try {
            FileInputStream in = new FileInputStream(url);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            base64 = Base64Util.encode(data);
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return base64.isEmpty() ? "error" : base64;
    }

    /** @deprecated */
    protected void test_faceVerify(String face1, String face2) {
        try {
            FileInputStream in1 = new FileInputStream(face1);
            FileInputStream in2 = new FileInputStream(face2);
            byte[] data1 = new byte[in1.available()];
            byte[] data2 = new byte[in2.available()];
            in1.read(data1);
            in2.read(data2);
            in2.close();
            in1.close();
            String face1base64 = Base64Util.encode(data1);
            String face2base64 = Base64Util.encode(data2);
            MatchRequest req1 = new MatchRequest(face1base64, "BASE64");
            MatchRequest req2 = new MatchRequest(face2base64, "BASE64");
            ArrayList<MatchRequest> requests = new ArrayList();
            requests.add(req1);
            requests.add(req2);
            JSONObject res = this.client.match(requests);
            System.out.println(res.toString(2));
            JSONObject result = res.getJSONObject("result");
            Double score = Double.parseDouble(result.get("score").toString());
            System.out.println(score);
            if (score > 80.0D) {
                System.out.println("PASS!");
            } else {
                System.out.println("RETRY!");
            }
        } catch (FileNotFoundException var15) {
            var15.printStackTrace();
        } catch (IOException var16) {
            var16.printStackTrace();
        }

    }
}

