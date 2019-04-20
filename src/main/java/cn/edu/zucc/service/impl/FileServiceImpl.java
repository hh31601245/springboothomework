package cn.edu.zucc.service.impl;

import cn.edu.zucc.domain.dao.FileRepository;
import cn.edu.zucc.domain.entity.Files;
import cn.edu.zucc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public void Upload(Files files) { //下载
        fileRepository.save(files);
    }

    @Override
    public Files getFiles(String Fname, Long Uid) {  //获取文件
        return fileRepository.findByUidAndFname(Uid,Fname);
    }


    @Override  //显示文件列表
    public List<Files> getAllFiles(Long uid) {
        return fileRepository.findByUid(uid);
    }

    @Override  //删除指定文件
    public void deleteByFnameAndUid(String Fname, Long Uid) {
        Files f=fileRepository.findByUidAndFname(Uid,Fname);
        fileRepository.delete(f);
    }


}
