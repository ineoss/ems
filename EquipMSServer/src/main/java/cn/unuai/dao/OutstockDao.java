package cn.unuai.dao;

import cn.unuai.entity.OutstockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SunShuai on 2018/4/29.
 */
public interface OutstockDao extends JpaRepository<OutstockEntity, Long> {
    public OutstockEntity save(OutstockEntity ist);
}
