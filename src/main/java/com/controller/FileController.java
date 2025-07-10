package com.controller;

import java.io.IOException;

import com.service.ConfigService;
import com.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.annotation.IgnoreAuth;
import com.entity.EIException;
import com.utils.R;

import javax.servlet.http.HttpServletResponse;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
@SuppressWarnings({"unchecked","rawtypes"})
public class FileController{
	@Autowired
    private ConfigService configService;

	@Autowired
	private AliOSSUtils aliOSSUtils;


	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
    @IgnoreAuth
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new EIException("上传文件不能为空");
		}
		String url= aliOSSUtils.upload(file);
		return R.ok().put("url",url);
	}
	
	/**
	 * 下载文件
	 */
	@IgnoreAuth
	@RequestMapping("/download")
	public void download(@RequestParam String fileName, HttpServletResponse response) {
		try {
			aliOSSUtils.downloadFile(fileName, response);
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			// 可添加日志记录
		}
	}

	
}
