package cn.unuai.entity;

import javax.persistence.*;

/**
 * Created by SunShuai on 2018/5/15.
 */
@Entity
@Table(name = "borrow", schema = "ems", catalog = "")
public class BorrowEntity {
    private Integer id;
    private Integer equipId;
    private Integer userId;
    private String applyDate;
    private String audiDate;
    private Integer auditor;
    private String agree;
    private String mark;
    private String rDate;
    private String damage;
    private String damageDesc;
    private Integer rHandler;

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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "apply_date")
    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    @Basic
    @Column(name = "audi_date")
    public String getAudiDate() {
        return audiDate;
    }

    public void setAudiDate(String audiDate) {
        this.audiDate = audiDate;
    }

    @Basic
    @Column(name = "auditor")
    public Integer getAuditor() {
        return auditor;
    }

    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
    }

    @Basic
    @Column(name = "agree")
    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
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
    @Column(name = "r_date")
    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    @Basic
    @Column(name = "damage")
    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    @Basic
    @Column(name = "damage_desc")
    public String getDamageDesc() {
        return damageDesc;
    }

    public void setDamageDesc(String damageDesc) {
        this.damageDesc = damageDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BorrowEntity that = (BorrowEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (equipId != null ? !equipId.equals(that.equipId) : that.equipId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null) return false;
        if (audiDate != null ? !audiDate.equals(that.audiDate) : that.audiDate != null) return false;
        if (auditor != null ? !auditor.equals(that.auditor) : that.auditor != null) return false;
        if (agree != null ? !agree.equals(that.agree) : that.agree != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (rDate != null ? !rDate.equals(that.rDate) : that.rDate != null) return false;
        if (damage != null ? !damage.equals(that.damage) : that.damage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (equipId != null ? equipId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (applyDate != null ? applyDate.hashCode() : 0);
        result = 31 * result + (audiDate != null ? audiDate.hashCode() : 0);
        result = 31 * result + (auditor != null ? auditor.hashCode() : 0);
        result = 31 * result + (agree != null ? agree.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (rDate != null ? rDate.hashCode() : 0);
        result = 31 * result + (damage != null ? damage.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "r_handler")
    public Integer getrHandler() {
        return rHandler;
    }

    public void setrHandler(Integer rHandler) {
        this.rHandler = rHandler;
    }
}
