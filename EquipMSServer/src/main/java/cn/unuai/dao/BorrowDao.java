package cn.unuai.dao;

import cn.unuai.entity.BorrowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SunShuai
 */
public interface BorrowDao extends JpaRepository<BorrowEntity, Long> {
    @Query(value =
            "SELECT e.id, e.NAME,e.asset_no, e.equip_type, e.buy_dept, d.deptname, (select k1.name from kind k1 where k1.id = e.equip_type) " +
                    "FROM equipment e, department d " +
                    "WHERE  e.buy_dept=d.id   and e.state=2002 and e.id not in (select equip_id from borrow bo where bo.r_date is null) " +
                    "and  e. NAME LIKE ?1 and e.buy_dept != ?2 and e.equip_type like ?3 order by e.name limit ?4,?5", nativeQuery = true)
    public List getCanBorrowList(String name, int dept, String type, int offset, int pageSize);

    @Query(value =
            "SELECT count(e.id)" +
                    "FROM equipment e " +
                    "WHERE  e.state=2002 and e.id not in (select equip_id from borrow bo where bo.r_date is null) " +
                    "and  e. NAME LIKE ?1 and e.buy_dept != ?2 and e.equip_type like ?3 ", nativeQuery = true)
    public int getCanBorrowListCount(String name, int dept, String type);

    @Query("select b from BorrowEntity b where b.rDate is null and b.equipId=?1")
    public BorrowEntity getByEquipId(int equipId);

    @Query(value =
            "SELECT b.id, b.equip_id, e.name as equipName, k.name as typeName,b.apply_date " +
                    "FROM borrow b, equipment e,  kind k " +
                    "WHERE (b.audi_date IS NULL or b.audi_date='') and b.equip_id=e.id and e.equip_type=k.id and e.buy_dept=?1  order by b.apply_date limit ?2,?3", nativeQuery = true)
    public List getBorrowAuditingList(int dept, int offset, int pageSize);

    @Query(value =
            "SELECT count(b.id) " +
                    "FROM borrow b, equipment e " +
                    "WHERE (b.audi_date IS NULL or b.audi_date='') and b.equip_id=e.id  and e.buy_dept=?1", nativeQuery = true)
    public int getBorrowAuditingListCount(int dept);

    @Query(value =
            "select b.id,b.equip_id,e.name as equipName,k.name as typeName,b.user_id,u.truename,d.deptname,b.apply_date " +
                    "from borrow b,equipment e,kind k,user u ,department d " +
                    "where b.id=?1 and b.equip_id=e.id and e.equip_type=k.id and u.id=b.user_id and u.dept=d.id", nativeQuery = true)
    public List getBorrowDetail(int id);

    @Query(value = "SELECT b.id, b.equip_id, e.asset_no,e. NAME AS equipName, k. NAME AS typeName, " +
            " ( SELECT d2.deptname FROM department d2, equipment e2 WHERE d2.id = e2.buy_dept AND e2.id = b.equip_id )d1, " +
            " ( SELECT d1.deptname FROM department d1, USER u WHERE u.id = b.user_id AND d1.id = u.dept )d2 " +
            "FROM borrow b, equipment e, department d, kind k " +
            "WHERE (r_date IS NULL or r_date ='') AND (audi_date is not null and audi_date!='') AND e.id = b.equip_id AND d.id = e.buy_dept AND k.id = e.equip_type and  e.name like ?1 order by b.audi_date limit ?2,?3", nativeQuery = true)
    public List getBorrowedList(String equipName, int offset, int pageSize);

    @Query(value = "SELECT count(b.id) " +
            "FROM borrow b, equipment e " +
            "WHERE  b.equip_id=e.id and (b.r_date IS NULL or b.r_date ='') AND (b.audi_date is not null and b.audi_date!='')  and  e.name like ?1 ", nativeQuery = true)
    public int getBorrowedListCount(String equipName);

    @Query(value =
            "select b.id,b.equip_id,e.asset_no,e.name as equipName,k.name as typeName,b.user_id,u.truename,d.deptname,b.audi_date " +
                    "from borrow b,equipment e,kind k,user u ,department d " +
                    "where b.id=?1 and b.equip_id=e.id and e.equip_type=k.id and u.id=b.user_id and u.dept=d.id", nativeQuery = true)
    public List getBorrowedDetail(int id);

    @Modifying
    @Query("update BorrowEntity set damage=?2,damageDesc=?3,rDate=?4, r_handler=?5 where id=?1")
    public void returnEquip(int id, String damage, String damageDesc, String rDate, int r_handler);

    @Query(value = "select e.asset_no,e.name as equipName,d.deptname,b.audi_date ,b.agree " +
            "from borrow b, equipment e,department d " +
            "where b.equip_id=e.id and e.buy_dept=d.id and (b.r_date is null or b.r_date='') and b.user_id=?1 order by b.apply_date desc limit ?2,?3", nativeQuery = true)
    public List getApplyByUserid(int userid, int offset, int pageSize);

    @Query(value = "select count(b.id)" +
            "from borrow b " +
            "where  (b.r_date is null or b.r_date='') and b.user_id=?1 ", nativeQuery = true)
    public int getApplyByUseridCount(int userid);
}
