package com.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Component
public class AliOSSUtils {
    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    @Value("${aliyun.accessKeyId}") // 从配置注入
    private String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    private String bucketName = "edusys2025";

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        String originalFilename = multipartFile.getOriginalFilename();
        // 1. 创建ObjectMetadata并设置关键头信息
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType()); // 设置MIME类型
        metadata.setContentDisposition("inline"); // ★★★ 关键设置：强制预览模式 ★★★

        // 2. 上传文件时附加元数据
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(
                bucketName,
                originalFilename,
                inputStream,
                metadata // 传入元数据设置
        );

        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + originalFilename;

        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }

    public void downloadFile(String fileName, HttpServletResponse response) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 设置响应头
            response.setHeader("Content-Disposition", "attachment; filename=\"" +
                    URLEncoder.encode(fileName, "UTF-8") + "\"");
            response.setContentType("application/octet-stream");

            // 流式传输
            try (OSSObject ossObject = ossClient.getObject(bucketName, fileName);
                 InputStream in = ossObject.getObjectContent()) {

                OutputStream out = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        } finally {
            ossClient.shutdown();
        }
    }
}
