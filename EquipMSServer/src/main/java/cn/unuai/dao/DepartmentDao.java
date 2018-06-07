package cn.unuai.dao;

import cn.unuai.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by SunShuai on 2018/4/21.
 */
public interface DepartmentDao extends JpaRepository<DepartmentEntity, Long> {
    @Query("select id,deptname from DepartmentEntity ")
    public List<DepartmentEntity> getDeparementList();

    @Query("select deptname from DepartmentEntity where id=?1")
    public String getDeptName(int id);
}
