package cn.unuai.dao;

import cn.unuai.entity.DepartmentEntity;
import cn.unuai.entity.RoleEntity;
import cn.unuai.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SunShuai on 2018/4/21.
 */
public interface RoleDao extends JpaRepository<RoleEntity, Long> {
    @Query("select id,rolename from RoleEntity ")
    public List<RoleEntity> getRoleList();

    @Query("select rolename from RoleEntity where id=?1")
    public String getRoleName(int id);

    @Query("select e from RoleEntity e where e.id=?1")
    public RoleEntity findById(int id);
}
