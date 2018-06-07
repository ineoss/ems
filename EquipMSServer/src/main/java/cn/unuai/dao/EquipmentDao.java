package cn.unuai.dao;

import cn.unuai.entity.EquipmentEntity;
import cn.unuai.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SunShuai on 2018/4/26.
 */
public interface EquipmentDao extends JpaRepository<EquipmentEntity, Long> {
    public EquipmentEntity save(EquipmentEntity e);

    @Query(value = "select cast(max(SUBSTR(asset_no ,LENGTH(asset_no)-2)) as unsigned int) from equipment where equip_type=?1", nativeQuery = true)
    public Integer getMaxAssetNo(int type);

    @Query("select e from EquipmentEntity e where assetNo=?1")
    public EquipmentEntity findByAssetNo(String assetNo);

    @Modifying
    @Query("update EquipmentEntity e set e.state=?2 where e.id=?1")
    public void setEquipmentState(int id, int state);

    @Query(value =
            "SELECT e.id,e. NAME,e.asset_no," +
                    "( SELECT k. NAME FROM Kind k WHERE k.id = e.equip_type ) AS equipType," +
                    "( SELECT k. NAME FROM Kind k WHERE k.id = e.state ) AS state " +
                    "FROM Equipment e where e.name like ?1 and e.state like ?2 and e.equip_type like ?3 ORDER BY e.asset_no limit ?4,?5", nativeQuery = true)
    public List getEquipList(String name, String state, String type, int offset, int pageSize);

    @Query(value =
            "SELECT count(e.id)" +
                    "FROM equipment e where e.name like ?1 and e.state like ?2 and e.equip_type like ?3", nativeQuery = true)
    public int getEquipListCount(String name, String state, String type);

    @Query(value =
            "select e.id,e. name,e.asset_no,e.factory,f.factoryname, e.buy_date,e.pos,e.price,e.buy_dept,d.deptname,e.use_year,e.image,e.equip_type," +
                    "( select k. name from kind k where k.id = e.equip_type ) as typeName,e.state," +
                    "( select k. name from Kind k where k.id = e.state ) as stateName,e.repaire_cycle " +
                    "from equipment e, factory f,department d " +
                    "where e.id=?1 and e.factory=f.id and e.buy_dept=d.id", nativeQuery = true)
    public List getEquipmentInfo(int id);

    @Query(value = "select count(1),(select k2.name from kind k2 where k2.id=e.equip_type) typeName " +
            "from equipment e,kind k " +
            "where e.state=k.id " +
            "group by e.equip_type", nativeQuery = true)
    public List getTypeCount();

    @Query(value = "select count(1) from equipment where state like ?1", nativeQuery = true)
    public int getEquipmentCountByState(String state);

    @Query(value = "select sum(price) from EquipmentEntity e")
    public float getotalAssets();

    @Modifying
    @Query("update EquipmentEntity set lastRepaire = ?1 where assetNo=?2")
    public void setLastRepaireDate(String date, String assetNo);

    @Query(value = "select avg(TO_DAYS(g.giveup_date)-TO_DAYS(e.buy_date)) " +
            "from equipment e,giveup g " +
            "where e.id=g.equip_id and e.equip_type=?1", nativeQuery = true)
    public Double getAvgAge(int type);

    @Query("select e from EquipmentEntity  e")
    public EquipmentEntity getOne(int id);
}
