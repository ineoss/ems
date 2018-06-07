package cn.unuai.dao;

import cn.unuai.entity.InstockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SunShuai on 2018/4/28.
 */
public interface InstockDao extends JpaRepository<InstockEntity, Long> {
    public InstockEntity save(InstockEntity ist);
}
