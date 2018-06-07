package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/4/21.
 */
@Entity
@Table(name = "role", schema = "ems", catalog = "")
public class RoleEntity {
    private Integer id;
    private String rolename;
    private String might;
    private String enable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rolename")
    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Basic
    @Column(name = "might")
    public String getMight() {
        return might;
    }

    public void setMight(String might) {
        this.might = might;
    }

    @Basic
    @Column(name = "enable")
    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

}
