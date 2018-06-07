package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/5/10.
 */
@Entity
@Table(name = "buy", schema = "ems", catalog = "")
public class BuyEntity {
    private Integer id;
    private String name;
    private String equipName;
    private Integer equipType;
    private Double plPrice;
    private Integer plNum;
    private String plDate;
    private Double rPrice;
    private Integer rNum;
    private String rDate;
    private Integer handler;
    private Integer plDept;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "equip_name")
    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    @Basic
    @Column(name = "equip_type")
    public Integer getEquipType() {
        return equipType;
    }

    public void setEquipType(Integer equipType) {
        this.equipType = equipType;
    }

    @Basic
    @Column(name = "pl_price")
    public Double getPlPrice() {
        return plPrice;
    }

    public void setPlPrice(Double plPrice) {
        this.plPrice = plPrice;
    }

    @Basic
    @Column(name = "pl_num")
    public Integer getPlNum() {
        return plNum;
    }

    public void setPlNum(Integer plNum) {
        this.plNum = plNum;
    }

    @Basic
    @Column(name = "pl_date")
    public String getPlDate() {
        return plDate;
    }

    public void setPlDate(String plDate) {
        this.plDate = plDate;
    }

    @Basic
    @Column(name = "r_price")
    public Double getrPrice() {
        return rPrice;
    }

    public void setrPrice(Double rPrice) {
        this.rPrice = rPrice;
    }

    @Basic
    @Column(name = "r_num")
    public Integer getrNum() {
        return rNum;
    }

    public void setrNum(Integer rNum) {
        this.rNum = rNum;
    }

    @Basic
    @Column(name = "r_date")
    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    @Basic
    @Column(name = "handler")
    public Integer getHandler() {
        return handler;
    }

    public void setHandler(Integer handler) {
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuyEntity buyEntity = (BuyEntity) o;

        if (id != null ? !id.equals(buyEntity.id) : buyEntity.id != null) return false;
        if (name != null ? !name.equals(buyEntity.name) : buyEntity.name != null) return false;
        if (equipName != null ? !equipName.equals(buyEntity.equipName) : buyEntity.equipName != null) return false;
        if (equipType != null ? !equipType.equals(buyEntity.equipType) : buyEntity.equipType != null) return false;
        if (plPrice != null ? !plPrice.equals(buyEntity.plPrice) : buyEntity.plPrice != null) return false;
        if (plNum != null ? !plNum.equals(buyEntity.plNum) : buyEntity.plNum != null) return false;
        if (plDate != null ? !plDate.equals(buyEntity.plDate) : buyEntity.plDate != null) return false;
        if (rPrice != null ? !rPrice.equals(buyEntity.rPrice) : buyEntity.rPrice != null) return false;
        if (rNum != null ? !rNum.equals(buyEntity.rNum) : buyEntity.rNum != null) return false;
        if (rDate != null ? !rDate.equals(buyEntity.rDate) : buyEntity.rDate != null) return false;
        if (handler != null ? !handler.equals(buyEntity.handler) : buyEntity.handler != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (equipName != null ? equipName.hashCode() : 0);
        result = 31 * result + (equipType != null ? equipType.hashCode() : 0);
        result = 31 * result + (plPrice != null ? plPrice.hashCode() : 0);
        result = 31 * result + (plNum != null ? plNum.hashCode() : 0);
        result = 31 * result + (plDate != null ? plDate.hashCode() : 0);
        result = 31 * result + (rPrice != null ? rPrice.hashCode() : 0);
        result = 31 * result + (rNum != null ? rNum.hashCode() : 0);
        result = 31 * result + (rDate != null ? rDate.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "pl_dept")
    public Integer getPlDept() {
        return plDept;
    }

    public void setPlDept(Integer plDept) {
        this.plDept = plDept;
    }
}
