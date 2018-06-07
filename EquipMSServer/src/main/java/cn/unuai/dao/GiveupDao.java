package cn.unuai.dao;

import cn.unuai.entity.GiveupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SunShuai on 2018/5/18.
 */
public interface GiveupDao extends JpaRepository<GiveupEntity, Long> {
    @Query(value = "select g.id giveupId,e.id equipId,e.asset_no,e.name equipName,g.giveup_date,u.truename " +
            "from giveup g,equipment e,user u " +
            "where g.equip_id=e.id and g.handler=u.id and e.name like ?1 order by g.giveup_date desc limit ?2,?3", nativeQuery = true)
    public List getGiveupList(String name, int offset, int pageSize);

    @Query(value = "select count(g.id) " +
            "from giveup g,equipment e " +
            "where g.equip_id=e.id  and e.name like ?1", nativeQuery = true)
    public int getGiveupListCount(String name);

    @Query(value = "select g.id,e.id equipId,e.asset_no,e.buy_date,e.use_year,e.price,e.image,e.name equipName,d.deptname,k.name kindName,g.giveup_date,g.return_price,u.truename,f.factoryname " +
            "from giveup g,equipment e,department d,kind k,user u,factory f " +
            "where g.equip_id=e.id and e.buy_dept= d.id and e.equip_type=k.id and g.handler=u.id and e.factory=f.id and g.id=?1", nativeQuery = true)
    public List getGiveupInfo(int id);

}
