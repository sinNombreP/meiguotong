package com.jeeplus.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.QiniuUtils;
import com.jeeplus.common.utils.UploadHelper;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 图片Controller
 * 
 * @author dong
 * @version 2017-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/img")
public class ImgController extends BaseController {

		// 上传图片
		@ResponseBody
		@RequestMapping(value = "uploadFile")
		public AjaxJson uploadFile(HttpServletRequest request, HttpServletResponse response, Model model) {

			AjaxJson ajaxJson = new AjaxJson();

			try {
				List<MultipartFile> multipartFiles = UploadHelper.getMultipartFileList(request);
				if (multipartFiles.size() == 0) {
					// 给出提示,不允许没选择文件点击上传
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("上传图片为空");
					return ajaxJson;
				}
				MultipartFile multipartFile = multipartFiles.get(0);
				String imgPath="img/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();

				if(multipartFile != null && multipartFile.getSize()>0) {
					String key =imgPath +"/"+CodeGenUtils.getPicId()+".jpg";
					String filePath=QiniuUtils.uploadFile(multipartFile.getInputStream(), key);
					if(filePath.equals("")){
						ajaxJson.setSuccess(false);
						ajaxJson.setMsg("图片上传失败");
					}else{
						ajaxJson.setSuccess(true);
						ajaxJson.setMsg("图片上传成功");
						filePath=QiniuUtils.QiniuUrl+ key;
						ajaxJson.put("filePath", filePath);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("因未知原因失败");
			}
			return ajaxJson;
		}
		
		
	
		/** 
		* @Title: uploadVideo 
		* @Description: 上传视频
		* @author 彭善智
		* @date 2018年9月3日上午11:36:59
		*/ 
		@ResponseBody
		@RequestMapping(value = "uploadVideo")
		public AjaxJson uploadVideo(HttpServletRequest request, HttpServletResponse response, Model model) {
			
			AjaxJson ajaxJson = new AjaxJson();
			
			try {
				List<MultipartFile> multipartFiles = UploadHelper.getMultipartFileList(request);
				if (multipartFiles.size() == 0) {
					// 给出提示,不允许没选择文件点击上传
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("上传视频为空");
					return ajaxJson;
				}
				MultipartFile multipartFile = multipartFiles.get(0);
				String imgPath="img/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
				
				if(multipartFile != null && multipartFile.getSize()>0) {
					String originalFilename = multipartFile.getOriginalFilename();
					String prefix=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
					String key = imgPath +"-"+CodeGenUtils.getPicId()+"."+prefix;
					String filePath=QiniuUtils.uploadFile(multipartFile.getInputStream(), key);
					if(filePath.equals("")){
						ajaxJson.setSuccess(false);
						ajaxJson.setMsg("视频上传失败");
					}else{
						ajaxJson.setSuccess(true);
						ajaxJson.setMsg("视频上传成功");
						filePath=QiniuUtils.QiniuUrl+ key;
						ajaxJson.put("filePath", filePath);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("因未知原因失败");
			}
			return ajaxJson;
		}
		
		
		
}