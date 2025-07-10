package com.controller;

import com.annotation.IgnoreAuth;
import com.entity.TeacherEntity;
import com.service.BaiduFaceService;
import com.service.TeacherService;
import com.utils.JwtUtils;
import com.utils.R;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/face")
public class FaceAuthController {

    @Autowired
    private BaiduFaceService faceService;

    @Value("${baidu.ai.group-id}")
    private String groupId;

    @Autowired
    private TeacherService teacherService;


    // 人脸登录
    @IgnoreAuth
    @PostMapping("/login")
    public R loginByFace(@RequestParam("file") MultipartFile file,String username) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        JSONObject result = faceService.searchFace(base64Image, groupId);
        System.out.println(result.getInt("error_code"));
        // 解析结果获取匹配的userId
        if (result.getInt("error_code") == 0) {
            JSONObject user = result.getJSONObject("result").getJSONArray("user_list").getJSONObject(0);
            System.out.println(user.getDouble("score"));
            if (user.getDouble("score") > 80) { // 相似度阈值
                String userId = user.getString("user_id");
                TeacherEntity teacher=teacherService.selectById(userId);
                if(username.equals(teacher.getT_username())){
                String token=JwtUtils.generateToken(Long.valueOf(userId),username,"teacher","teacher");
                return R.ok().put("data",teacher).put("token",token);
                }else{
                    return R.error("用户名错误");
                }
            }
        }
        return R.error(401,"人脸识别认证失败");
    }

    // 人脸注册
    @PostMapping("/register")
    public R registerFace(
            @RequestParam String token,
            @RequestParam("file") MultipartFile file) throws IOException { // Base64格式
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        Long userId=JwtUtils.getUserIdFromToken(token);
        TeacherEntity teacher=teacherService.selectById(userId);
        teacher.setCertificated(1);
        teacherService.updateById(teacher);
        JSONObject result = faceService.registerFace(String.valueOf(userId), base64Image, groupId);
        return R.ok(result.toMap());
    }
}