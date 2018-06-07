package cn.unuai.dao;

import cn.unuai.entity.BuyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SunShuai on 2018/5/10.
 */
public interface BuyDao extends JpaRepository<BuyEntity, Long> {
    @Query(value = "SELECT b.id, b. name AS planname, b.equip_name, b.equip_type, b.pl_date, k. name " +
            "FROM buy b, kind k " +
            "where b.pl_dept = ?1 AND b.equip_type = k.id AND (b.r_date IS NULL OR b.r_date = '') limit ?2,?3", nativeQuery = true)
    public List getBuyPlanList(int dept, int offset, int pageSize);//购置计划列表

    @Query(value = "SELECT count(b.id) " +
            "FROM buy b, kind k " +
            "where b.pl_dept =?1 AND b.equip_type = k.id AND (b.r_date IS NULL OR b.r_date = '')", nativeQuery = true)
    public int getBuyPlanListCount(int dept);//购置计划列表

    @Query(value = "select b.id,b.name as planname,b.equip_name,b.equip_type,k.name,b.pl_dept,d.deptname,b.pl_price,b.pl_num,b.pl_date,b.r_date " +
            "from buy b,department d,kind k where b.pl_dept=d.id and b.equip_type=k.id and b.id=?1", nativeQuery = true)
    public List getBuyPlanInfo(int id);

    @Query(value = "select 1 from buy b where b.id=?1 and (b.r_date is not null and b.r_date!='') and b.r_date!=''", nativeQuery = true)
    public Object isPlanEnd(int id);

    @Query(value = "select b.id, b.equip_name,b.equip_type,k.name,b.pl_dept,d.deptname ,b.r_date" +
            " from buy b,department d,kind k " +
            "where b.equip_name like ?1 and b.pl_dept= ?2 and b.pl_dept=d.id and b.equip_type=k.id and b.r_date is not null and b.r_date!='' order by b.r_date desc limit ?3,?4", nativeQuery = true)
    public List getBuyHistory(String equipname, int dept, int offset, int pageSize);

    @Query(value = "select count(b.id)" +
            " from buy b,department d,kind k " +
            "where b.equip_name like ?1 and b.pl_dept= ?2 and b.pl_dept=d.id and b.equip_type=k.id and b.r_date is not null and b.r_date!=''", nativeQuery = true)
    public int getBuyHistoryCount(String equipname, int dept);

    @Query(value = "select b.id,b.name as planname,b.equip_name,b.equip_type,k.name,b.pl_dept,d.deptname,b.pl_price,b.pl_num,b.pl_date,b.r_price,b.r_num,b.r_date,u.truename " +
            "from buy b,department d,kind k,user u where b.pl_dept=d.id and b.equip_type=k.id and b.handler=u.id and b.id=?1", nativeQuery = true)
    public List getBuyInfo(int id);
}
