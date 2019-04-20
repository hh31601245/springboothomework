package cn.edu.zucc.domain.entity;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //这里的Long也一定要大写
    @NotEmpty(message="注解声明：用户名不能为空")
    private String name;
    @Min(value=18,message = "注解声明：必须年满18周岁")
    private Integer age;
    @Pattern(regexp="[a-za-z0-9._%+-]+@[a-za-z0-9.-]+\\.[a-za-z]{2,4}", message="注解声明：邮件格式错误")
    private String email;
    @NotEmpty(message="注解声明：密码不能为空")
    //@Size(min=6,max=18,message = "注解声明：密码长度在6到18之间")
    private String password;
    @NotEmpty(message="注解声明：权限不能为空")
    private String role;
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
/*private Integer monthincome;
    private Integer yearincome;

    public Integer getMonthincome() {
        return monthincome;
    }

    public void setMonthincome(Integer monthincome) {
        this.monthincome = monthincome;
    }

    public Integer getYearincome() {
        return yearincome;
    }

    public void setYearincome(Integer yearincome) {
        this.yearincome = yearincome;
    }*/

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}