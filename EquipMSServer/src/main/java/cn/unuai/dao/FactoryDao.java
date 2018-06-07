package cn.unuai.dao;

import cn.unuai.entity.FactoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SunShuai on 2018/4/25.
 */
public interface FactoryDao extends JpaRepository<FactoryEntity, Long> {
    @Query("select id,factoryname from FactoryEntity ")
    public List<FactoryEntity> getFactoryList();

    @Query(value = "select max(t.num),t.factoryname from( " +
            "SELECT count(e.id) num,f.factoryname factoryname " +
            "FROM equipment e,factory f " +
            "WHERE e.state = 2004 and f.id = e.factory " +
            "GROUP BY e.factory)t ", nativeQuery = true)
    public List getMaxGiveupFactory();

    @Query(value = "SELECT count(r.id) num, f.factoryname " +
            "FROM repaire r, factory f, equipment e " +
            "WHERE r.equip_id = e.id AND e.factory = f.id GROUP BY f.id,f.factoryname", nativeQuery = true)
    public List getMaxRepaireFactory();
}
