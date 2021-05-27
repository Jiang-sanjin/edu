package com.example.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;

import com.aliyun.oss.OSSClientBuilder;
import com.example.oss.util.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.joda.time.DateTime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    //预设图片文件格式
    private static String TYPESTR[] = {".png",".jpg",".bmp",".gif",".jpeg"};

    @Override
    public String upload(MultipartFile file) {
        OSS ossClient = null;
        String url = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(
                    ConstantPropertiesUtil.END_POINT,
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //判断文件格式
            boolean flag = false;
            for(String type : TYPESTR){   //循环 TYPESTR  判断文件格式
                if(StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),type)){
                    //  StringUtils.endsWithIgnoreCase  判断字符串以什么为结尾
                    flag = true;
                    break;
                }
            }
            if(!flag){
                return "图片格式不正确";
            }

            //判断文件内容   ImageIO 读图片
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image != null){
                System.err.println(String.valueOf(image.getHeight()));
                System.err.println(String.valueOf(image.getWidth()));
            } else{
                return "文件内容不正确";
            }

            //获取上传文件流的名称
            String filename = file.getOriginalFilename();
            //处理文件名字： lijin.shuai.jpg   仅最后一个为后缀名
            String ext = filename.substring(filename.lastIndexOf("."));   //后缀名
            String newName = UUID.randomUUID().toString() + ext;// 根据uuid设置名字
            String dataPath = new DateTime().toString("yyyy/MM/dd");  //时间路径
            String urlPath = ConstantPropertiesUtil.FILE_HOST + "/" + dataPath + "/" + newName;   //存储路径

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, urlPath, inputStream);
            url = "https://"+ConstantPropertiesUtil.BUCKET_NAME + "." + ConstantPropertiesUtil.END_POINT + "/" + urlPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return url;

    }
}
