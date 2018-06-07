package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/4/28.
 */
@Entity
@Table(name = "instock", schema = "ems", catalog = "")
public class InstockEntity {
    private Integer id;
    private Integer equipId;
    private String instockDate;
    private Integer instockDept;
    private Integer handler;
    private String mark;

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
    @Column(name = "equip_id")
    public Integer getEquipId() {
        return equipId;
    }

    public void setEquipId(Integer equipId) {
        this.equipId = equipId;
    }

    @Basic
    @Column(name = "instock_date")
    public String getInstockDate() {
        return instockDate;
    }

    public void setInstockDate(String instockDate) {
        this.instockDate = instockDate;
    }

    @Basic
    @Column(name = "instock_dept")
    public Integer getInstockDept() {
        return instockDept;
    }

    public void setInstockDept(Integer instockDept) {
        this.instockDept = instockDept;
    }

    @Basic
    @Column(name = "handler")
    public Integer getHandler() {
        return handler;
    }

    public void setHandler(Integer handler) {
        this.handler = handler;
    }

    @Basic
    @Column(name = "mark")
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstockEntity that = (InstockEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (equipId != null ? !equipId.equals(that.equipId) : that.equipId != null) return false;
        if (instockDate != null ? !instockDate.equals(that.instockDate) : that.instockDate != null) return false;
        if (instockDept != null ? !instockDept.equals(that.instockDept) : that.instockDept != null) return false;
        if (handler != null ? !handler.equals(that.handler) : that.handler != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (equipId != null ? equipId.hashCode() : 0);
        result = 31 * result + (instockDate != null ? instockDate.hashCode() : 0);
        result = 31 * result + (instockDept != null ? instockDept.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }
}
