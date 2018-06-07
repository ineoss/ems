package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/5/20.
 */
@Entity
@Table(name = "repaire", schema = "ems", catalog = "")
public class RepaireEntity {
    private Integer id;
    private Integer equipId;
    private Integer handler;
    private String mark;
    private Double price;
    private String pos;
    private String repaireDate;
    private String success;

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

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "pos")
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Basic
    @Column(name = "repaire_date")
    public String getRepaireDate() {
        return repaireDate;
    }

    public void setRepaireDate(String repaireDate) {
        this.repaireDate = repaireDate;
    }

    @Basic
    @Column(name = "success")
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepaireEntity that = (RepaireEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (equipId != null ? !equipId.equals(that.equipId) : that.equipId != null) return false;
        if (handler != null ? !handler.equals(that.handler) : that.handler != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (pos != null ? !pos.equals(that.pos) : that.pos != null) return false;
        if (repaireDate != null ? !repaireDate.equals(that.repaireDate) : that.repaireDate != null) return false;
        if (success != null ? !success.equals(that.success) : that.success != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (equipId != null ? equipId.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        result = 31 * result + (repaireDate != null ? repaireDate.hashCode() : 0);
        result = 31 * result + (success != null ? success.hashCode() : 0);
        return result;
    }
}
