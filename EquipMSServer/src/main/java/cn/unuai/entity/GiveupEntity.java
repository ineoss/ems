package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/5/18.
 */
@Entity
@Table(name = "giveup", schema = "ems", catalog = "")
public class GiveupEntity {
    private Integer id;
    private Integer equipId;
    private String giveupDate;
    private Integer handler;
    private String mark;
    private Double returnPrice;

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
    @Column(name = "giveup_date")
    public String getGiveupDate() {
        return giveupDate;
    }

    public void setGiveupDate(String giveupDate) {
        this.giveupDate = giveupDate;
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

        GiveupEntity that = (GiveupEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (equipId != null ? !equipId.equals(that.equipId) : that.equipId != null) return false;
        if (giveupDate != null ? !giveupDate.equals(that.giveupDate) : that.giveupDate != null) return false;
        if (handler != null ? !handler.equals(that.handler) : that.handler != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (equipId != null ? equipId.hashCode() : 0);
        result = 31 * result + (giveupDate != null ? giveupDate.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "return_price")
    public Double getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(Double returnPrice) {
        this.returnPrice = returnPrice;
    }
}
