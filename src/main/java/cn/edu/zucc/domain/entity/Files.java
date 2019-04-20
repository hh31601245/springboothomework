package cn.edu.zucc.domain.entity;

        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import java.io.Serializable;
        import java.util.Date;

@Entity
public class Files implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Fid;
    private Long Uid;
    private String Fname;
    private String Fsrc;
    private Date Uploadtime;

    public Long getFid() {
        return Fid;
    }

    public void setFid(Long fid) {
        Fid = fid;
    }

    public Long getUid() {
        return Uid;
    }

    public void setUid(Long uid) {
        Uid = uid;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getFsrc() {
        return Fsrc;
    }

    public void setFsrc(String fsrc) {
        Fsrc = fsrc;
    }

    public Date getUploadtime() {
        return Uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        Uploadtime = uploadtime;
    }
}

