package cn.edu.zucc.service;

import cn.edu.zucc.StudioDemoApplicationTests;
import cn.edu.zucc.domain.entity.Files;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class FileServiceTest  extends StudioDemoApplicationTests {
    @Autowired
    FileService fileService;
    @Test
    public void a()/*Upload*/
    {
        Files files=new Files();
        files.setUid(10L);
        files.setFsrc("D:\\11.txt");
        files.setFname("11.txt");
        files.setFid(10L);
        fileService.Upload(files);
        Assert.assertThat(files.getFid(),is(10L));
    }
    @Test
    public void b()/*getFiles*/
    {
        Files f=fileService.getFiles("11.txt",13L);
        Assert.assertThat(f.getFid(),is(3L));
    }
    @Test
    public void c()/*getAllFiles*/
    {
        List<Files> f=fileService.getAllFiles(13L);
        Files f1=f.get(0);
        Files f2=f.get(1);
        Assert.assertThat(f1.getFid(),is(2L));
        Assert.assertThat(f2.getFid(),is(3L));
    }
    @Test
    public void d()/*deleteByFnameAndUid*/
    {
        Files f=new Files();
        f.setUid(10L);
        f.setFsrc("D:\\11.txt");
        f.setFname("11.txt");
        f.setFid(10L);
        fileService.deleteByFnameAndUid("11.txt",10L);
        List<Files> fl=fileService.getAllFiles(10L);
        Assert.assertTrue(!fl.contains(f));
    }
}
