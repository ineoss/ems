package cn.unuai.dao;

import cn.unuai.entity.KindEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SunShuai on 2018/4/25.
 */
public interface KindDao extends JpaRepository<KindEntity, Long> {
    @Query("select id,name from KindEntity where flag='equip_type'")
    public List<KindEntity> getEquipmentType();
    @Query("select id,name from KindEntity where flag='equip_state'")
    public List<KindEntity> getEquipmentState();
}
