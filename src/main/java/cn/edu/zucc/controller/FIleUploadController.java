package cn.edu.zucc.controller;

import cn.edu.zucc.common.AuthenticationInterceptor;
import cn.edu.zucc.common.R;
import cn.edu.zucc.common.UserLoginToken;
import cn.edu.zucc.domain.entity.Files;
import cn.edu.zucc.service.impl.FileServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.logging.Logger;

@Controller
@RequestMapping("/uploads")
public class FIleUploadController {
    @Autowired
    FileServiceImpl fileService;
    private static final Logger log= LoggerFactory.getLogger(FIleUploadController.class);
    @UserLoginToken
    @PostMapping("/upload1")
    @ResponseBody  //上传
    public Map<String,String> upload1(@RequestParam("file") MultipartFile file,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, ModelAndView modelAndView) throws IOException
    {
        log.info("[文件类型]-[{}]",file.getContentType());
        log.info("[文件名称]-[{}]",file.getName());
        log.info("[文件大小]-[{}]",file.getSize());
        //TODO将文件写入到指定目录
        file.transferTo(new File("D:\\"+file.getOriginalFilename()));
        Map<String,String> result=new HashMap<>(16);
        result.put("contentType",file.getContentType());
        result.put("fileName",file.getOriginalFilename());
        result.put("fileSize",file.getSize()+"");
        Files files=new Files();
        files.setFname(file.getOriginalFilename());
        files.setFsrc("D:\\"+file.getOriginalFilename());
        Date time= new java.sql.Date(new java.util.Date().getTime());
        files.setUploadtime(time);
        String token =httpServletRequest.getHeader("token");//从http请求中取出token
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        Long userId=jwt.getBody().get("id",Long.class); //这里的Long一定要大写
        files.setUid(userId);
        fileService.Upload(files);
        return result;
    }
    /*@UserLoginToken
    @RequestMapping(value="/download",method= RequestMethod.POST)
    @ResponseBody
    public R<String> downloadFile(@RequestParam String fileName, HttpServletRequest request, HttpServletResponse response)
    {
       // String fileName = "11.txt";

        if (fileName != null) {
            String token =request.getHeader("token");//从http请求中取出token
            Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
            Long userId=jwt.getBody().get("id",Long.class); //这里的Long一定要大写
            Files f=fileService.getFiles(fileName,userId);
            if(f==null)
            {
                return R.fail("你没有上传过这个文件,无权下载");
            }
            // 当前是从该工程的D:\\下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            String realPath = request.getServletContext().getRealPath("D://");
            File file = new File(realPath+fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;

                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return R.success("sss"+String.valueOf(os));
                    //System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return  R.success("success1");
    }*/
    @UserLoginToken
    @RequestMapping(value="/",method= RequestMethod.GET)
    @ResponseBody
    public R<List<Files>> getFilesList(HttpServletRequest request, HttpServletResponse response) //显示文件列表
    {
        String token =request.getHeader("token");//从http请求中取出token
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        Long userId=jwt.getBody().get("id",Long.class); //这里的Long一定要大写
        return R.data(fileService.getAllFiles(userId));
    }
    @UserLoginToken
    @RequestMapping(value="/{filename}",method= RequestMethod.DELETE)
    @ResponseBody
    public R<String> deleteFile(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response)  //删除文件
    {
        String token =request.getHeader("token");//从http请求中取出token
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        Long userId=jwt.getBody().get("id",Long.class); //这里的Long一定要大写
        fileService.deleteByFnameAndUid(filename,userId);
        return R.success("success");
    }
    @UserLoginToken
    @GetMapping("/{fileNames}")  //下载文件
    public R<String> download(@PathVariable("fileNames") String fileNames, HttpServletRequest request, HttpServletResponse response) {
        String token =request.getHeader("token");//从http请求中取出token
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        Long userId=jwt.getBody().get("id",Long.class); //这里的Long一定要大写
        Files f=fileService.getFiles(fileNames,userId);
        if(f==null)
        {
            return R.fail("fail");
        }
        try (
                //jdk7新特性，可以直接写到try()括号里面，java会自动关闭
                InputStream inputStream = new FileInputStream(new File("D:\\", fileNames /*+ ".txt"*/));
                OutputStream outputStream = response.getOutputStream()
        ) {
            //指明为下载
            response.setContentType("application/x-download");
            String fileName = "test.txt";
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);   // 设置文件名


            //把输入流copy到输出流
            IOUtils.copy(inputStream, outputStream);

            outputStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success("success");
    }
}
