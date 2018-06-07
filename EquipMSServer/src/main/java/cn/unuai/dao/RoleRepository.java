package cn.unuai.dao;

import cn.unuai.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by SunShuai on 2018/6/6.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, RoleRepositoryCustom {
}
