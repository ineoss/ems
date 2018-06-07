package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/4/21.
 */
@Entity
@Table(name = "department", schema = "ems", catalog = "")
public class DepartmentEntity {
    private Integer id;
    private String deptname;
    private Integer leader;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "deptname")
    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Basic
    @Column(name = "leader")
    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }
}
