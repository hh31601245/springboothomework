package cn.edu.zucc.service;

import cn.edu.zucc.domain.entity.Files;

import java.util.List;

public interface FileService {
    /**
     * 文件上传
     * @param files
     */
    void Upload(Files files);
    /**
     * 文件下载
     * @param Fname
     * @param Uid
     */
    Files getFiles(String Fname,Long Uid);
    /**
     * 文件查询
     * @param uid
     */
    List<Files> getAllFiles(Long uid);
    /**
     * 文件删除
     * @param Fname
     * @param Uid
     */
    void deleteByFnameAndUid(String Fname,Long Uid);
}
