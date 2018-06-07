package cn.unuai.dao;

import cn.unuai.entity.RepaireEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SunShuai
 */
public interface RepaireDao extends JpaRepository<RepaireEntity, Long> {
    @Query(value = "select id,asset_no,name,pos,last_repaire from equipment " +
            "where (TO_DAYS(?1)-TO_DAYS(last_repaire)>=repaire_cycle or repaire_cycle-(TO_DAYS(?1)-TO_DAYS(last_repaire))<=?2 ) and name like ?3 limit ?4,?5", nativeQuery = true)
    public List getNeedOverhaul(String day, int error, String name, int offset, int pageSize);

    @Query(value = "select count(id) from equipment " +
            "where (TO_DAYS(?1)-TO_DAYS(last_repaire)>=repaire_cycle or repaire_cycle-(TO_DAYS(?1)-TO_DAYS(last_repaire))<=?2 ) and name like ?3 ", nativeQuery = true)
    public int getNeedOverhaulCount(String day, int error, String name);

    @Query(value = "select r.id,e.asset_no,e.name,d.deptname,r.repaire_date from repaire r,equipment e,department d where r.equip_id=e.id and e.buy_dept=d.id and e.asset_no like ?1 order by r.repaire_date desc limit ?2,?3", nativeQuery = true)
    public List getRepaireHistory(String assetNo, int offset, int pageSize);

    @Query(value = "select count(r.id) from repaire r,equipment e where r.equip_id=e.id  and e.asset_no like ?1", nativeQuery = true)
    public int getRepaireHistoryCount(String assetNo);

    @Query("select r.id,e.assetNo,e.name,e.image,u.truename, r.repaireDate,r.pos,r.mark,r.success ,d.deptname,k.name " +
            "from RepaireEntity r,EquipmentEntity e,UserEntity u,DepartmentEntity d,KindEntity k " +
            "where r.equipId=e.id and r.handler=u.id and k.id=e.equipType and d.id=e.buyDept and r.id=?1")
    public List getRepaireInfo(int id);
}
