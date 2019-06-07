package com.jeeplus.common.utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xdwang
 * 
 * @create 2012-11-19 下午6:24:03
 * 
 * @email:xdwangiflytek@gmail.com
 * 
 * @description 上传帮助类
 * 
 */
public class UploadHelper {

    /**
     * @descrption 根据HttpServletRequest对象获取MultipartFile集合
     * @author xdwang
     * @create 2012-11-19下午5:11:41
     * @param request
     * @param maxLength
     *            文件最大限制
     * @param allowExtName
     *            不允许上传的文件扩展名
     * @return MultipartFile集合
     */
    public static List<MultipartFile> getFileSet(HttpServletRequest request,
            long maxLength, String[] allowExtName) {
        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch (Exception e) {
            return new LinkedList<MultipartFile>();
        }

        List<MultipartFile> files = new LinkedList<MultipartFile>();
        files = multipartRequest.getFiles("attach");
        // 移除不符合条件的
        for (int i = 0; i < files.size(); i++) {
            if (!validateFile(files.get(i), maxLength, allowExtName)) {
                files.remove(files.get(i));
                if (files.size() == 0) {
                    return files;
                }
            }
        }
        return files;
    }
    
    public static List<MultipartFile> getMultipartFileList(  
            HttpServletRequest request) {  
        List<MultipartFile> files = new ArrayList<MultipartFile>();  
        try {  
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(  
                    request.getSession().getServletContext());  
            if (request instanceof MultipartHttpServletRequest) {  
                // 将request变成多部分request  
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;  
                Iterator<String> iter = multiRequest.getFileNames();  
                // 检查form中是否有enctype="multipart/form-data"  
                if (multipartResolver.isMultipart(request) && iter.hasNext()) {  
                    // 获取multiRequest 中所有的文件名  
                    while (iter.hasNext()) {  
                        // 适配名字重复的文件  
                        List<MultipartFile> fileRows = multiRequest  
                                .getFiles(iter.next().toString());  
                        if (fileRows != null && fileRows.size() != 0) {  
                            for (MultipartFile file : fileRows) {  
                                if (file != null && !file.isEmpty()) {  
                                    files.add(file);  
                                }  
                            }  
                        }  
                    }  
                }  
            }  
        } catch (Exception ex) {  
//            log.error("解析MultipartRequest错误", ex);  
        }  
        return files;  
    }  

    /**
     * @descrption 保存文件
     * @author xdwang
     * @create 2012-11-19下午4:17:36
     * @param file
     *            MultipartFile对象
     * @param path
     *            保存路径，如“D:\\File\\”
     * @return 保存的全路径 如“D:\\File\\2345678.txt”
     * @throws Exception
     *             文件保存失败
     */
    public static String uploadFile(MultipartFile file, String path)
            throws Exception {

        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."))
                .toLowerCase();
        String lastFileName = UUID.randomUUID().toString() + extName;
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File temp = new File(path);
        if (!temp.isDirectory()) {
            temp.mkdir();
        }
        // 图片存储的全路径
        String fileFullPath = path + lastFileName;
        FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
        return fileFullPath;
    }

    /**
     * @descrption 验证文件格式，这里主要验证后缀名
     * @author xdwang
     * @create 2012-11-19下午4:08:12
     * @param file
     *            MultipartFile对象
     * @param maxLength
     *            文件最大限制
     * @param allowExtName
     *            不允许上传的文件扩展名
     * @return 文件格式是否合法
     */
    private static boolean validateFile(MultipartFile file, long maxLength,
            String[] allowExtName) {
        if (file.getSize() < 0 || file.getSize() > maxLength)
            return false;
        String filename = file.getOriginalFilename();

        // 处理不选择文件点击上传时，也会有MultipartFile对象，在此进行过滤
        if (filename == "") {
            return false;
        }
        String extName = filename.substring(filename.lastIndexOf("."))
                .toLowerCase();
        if (allowExtName == null || allowExtName.length == 0
                || Arrays.binarySearch(allowExtName, extName) != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
      * @method: replacePre
      * @Description: 去掉base64字符串前缀
      * @Author: 彭善智
      * @Date: 2019/3/21 12:39
      **/
    public static String replacePre(String file) {
        //允许的图片格式（可配置）
        String imgType = "jpg,png,jpeg";
        if (!StringUtils.isEmpty(imgType)) {
            String[] imgTypes = imgType.split(",");
            Pattern pattern;
            Matcher matcher;
            String regex;
            for (String v : imgTypes) {
                regex = MessageFormat.format("data:image/{0};base64,", v);
                pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(file);
                if (matcher.lookingAt()) {
                    return matcher.replaceFirst("");
                }
            }
        }
        return file;
    }

    /**
      * @method: checkFormat
      * @Description: 获取base64字符串后缀
      * @Author: 彭善智
      * @Date: 2019/3/21 12:37
      **/
    public static String checkFormat( byte[] bytes) {
        //允许的图片格式（可配置）
        String imgType = "jpg,png,jpeg";
        String suffix = "";
        try {
            //不带类似data:image/jpg;base64,前缀的解析
            ImageInputStream imageInputstream = new MemoryCacheImageInputStream(new ByteArrayInputStream(
                    bytes));
            //不使用磁盘缓存
            ImageIO.setUseCache(false);
            Iterator<ImageReader> it = ImageIO.getImageReaders(imageInputstream);
            if (it.hasNext()) {
                ImageReader imageReader = it.next();
                // 设置解码器的输入流
                imageReader.setInput(imageInputstream, true, true);

                // 图像文件格式后缀
                suffix = imageReader.getFormatName().trim().toLowerCase();
                imageInputstream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return suffix;
    }

}