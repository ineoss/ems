package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/4/29.
 */
@Entity
@Table(name = "outstock", schema = "ems", catalog = "")
public class OutstockEntity {
    private Integer id;
    private Integer equipId;
    private String outstockDate;
    private Integer outStockDept;
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
    @Column(name = "outstock_date")
    public String getOutstockDate() {
        return outstockDate;
    }

    public void setOutstockDate(String outstockDate) {
        this.outstockDate = outstockDate;
    }

    @Basic
    @Column(name = "out_stock_dept")
    public Integer getOutStockDept() {
        return outStockDept;
    }

    public void setOutStockDept(Integer outStockDept) {
        this.outStockDept = outStockDept;
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

        OutstockEntity that = (OutstockEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (equipId != null ? !equipId.equals(that.equipId) : that.equipId != null) return false;
        if (outstockDate != null ? !outstockDate.equals(that.outstockDate) : that.outstockDate != null) return false;
        if (outStockDept != null ? !outStockDept.equals(that.outStockDept) : that.outStockDept != null) return false;
        if (handler != null ? !handler.equals(that.handler) : that.handler != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (equipId != null ? equipId.hashCode() : 0);
        result = 31 * result + (outstockDate != null ? outstockDate.hashCode() : 0);
        result = 31 * result + (outStockDept != null ? outStockDept.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }
}
