package com.easychat.service.impl;

import com.easychat.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Value("${pic.save.path}")
    private String save_path;
    @Value("${pic.save.ip}")
    private String ip;
    private static String [] suffixArray = {".jpeg", ".jpg", ".ico", ".gif", ".png"};
    private static List<String> suffixList = Arrays.asList(suffixArray);

    @Autowired
    private ResourceLoader resourceLoader;


    @Override
    public String uploadImage(String base64Data, String suffix) {
        log.info("==上传图片==");
        log.info("==接收到的数据=="+base64Data);


        if(base64Data==null||"".equals(base64Data)){
            return null;
        }
        if (!suffixList.contains(suffix)){
            return null;
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String tempFileName=uuid+suffix;
        String imgFilePath = save_path+ File.separator+ tempFileName;//新生成的图片
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64Data);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {
                    //调整异常数据
                    b[i]+=256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return "http://" + ip + ":9004/image/download/"+tempFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void download(String imageId, HttpServletResponse response) {
        response.setContentType("image/jpeg");//设置输出流内容格式为图片格式
        response.setCharacterEncoding("utf-8");//response的响应的编码方式为utf-8
        try {
            OutputStream outputStream = response.getOutputStream();//输出流
            File file = new File(save_path + File.separator + imageId);
            InputStream in = new FileInputStream(file);//字节输入流
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            in.close();
            outputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
