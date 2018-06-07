package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/5/23.
 */
@Entity
@Table(name = "equipment", schema = "ems", catalog = "")
public class EquipmentEntity {
    private Integer id;
    private String name;
    private Integer factory;
    private String buyDate;
    private String pos;
    private Double price;
    private Integer buyDept;
    private Double useYear;
    private Integer equipType;
    private Integer state;
    private String assetNo;
    private String image;
    private String lastRepaire;
    private Double repaireCycle;

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
    @Column(name = "factory")
    public Integer getFactory() {
        return factory;
    }

    public void setFactory(Integer factory) {
        this.factory = factory;
    }

    @Basic
    @Column(name = "buy_date")
    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
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
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "buy_dept")
    public Integer getBuyDept() {
        return buyDept;
    }

    public void setBuyDept(Integer buyDept) {
        this.buyDept = buyDept;
    }

    @Basic
    @Column(name = "use_year")
    public Double getUseYear() {
        return useYear;
    }

    public void setUseYear(Double useYear) {
        this.useYear = useYear;
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
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "asset_no")
    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "last_repaire")
    public String getLastRepaire() {
        return lastRepaire;
    }

    public void setLastRepaire(String lastRepaire) {
        this.lastRepaire = lastRepaire;
    }

    @Basic
    @Column(name = "repaire_cycle")
    public Double getRepaireCycle() {
        return repaireCycle;
    }

    public void setRepaireCycle(Double repaireCycle) {
        this.repaireCycle = repaireCycle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentEntity that = (EquipmentEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (factory != null ? !factory.equals(that.factory) : that.factory != null) return false;
        if (buyDate != null ? !buyDate.equals(that.buyDate) : that.buyDate != null) return false;
        if (pos != null ? !pos.equals(that.pos) : that.pos != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (buyDept != null ? !buyDept.equals(that.buyDept) : that.buyDept != null) return false;
        if (useYear != null ? !useYear.equals(that.useYear) : that.useYear != null) return false;
        if (equipType != null ? !equipType.equals(that.equipType) : that.equipType != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (assetNo != null ? !assetNo.equals(that.assetNo) : that.assetNo != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (lastRepaire != null ? !lastRepaire.equals(that.lastRepaire) : that.lastRepaire != null) return false;
        if (repaireCycle != null ? !repaireCycle.equals(that.repaireCycle) : that.repaireCycle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (factory != null ? factory.hashCode() : 0);
        result = 31 * result + (buyDate != null ? buyDate.hashCode() : 0);
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (buyDept != null ? buyDept.hashCode() : 0);
        result = 31 * result + (useYear != null ? useYear.hashCode() : 0);
        result = 31 * result + (equipType != null ? equipType.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (assetNo != null ? assetNo.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (lastRepaire != null ? lastRepaire.hashCode() : 0);
        result = 31 * result + (repaireCycle != null ? repaireCycle.hashCode() : 0);
        return result;
    }
}
