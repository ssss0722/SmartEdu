package com;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadImage() throws Exception {
        // 读取本地文件
        FileInputStream fis = new FileInputStream("D:/下载/springboot.jpg");
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                fis
        );

        mockMvc.perform(
                        multipart("/file/upload")
                                .file(mockFile)
                                .param("type", "image")
                )
                .andDo(result -> {
                    System.out.println("返回内容：" + result.getResponse().getContentAsString());
                })
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").exists());
    }
}
