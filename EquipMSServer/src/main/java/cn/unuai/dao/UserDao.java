package cn.unuai.dao;

import cn.unuai.entity.LoginEntity;
import cn.unuai.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by SunShuai on 2018/4/21.
 */
public interface UserDao extends JpaRepository<UserEntity, Long> {

    @Query(value = "select u.id ,u.truename,d.deptname " +
            "from user u,department d " +
            "where u.dept=d.id and  u.truename like ?1 and u.dept like ?2 limit ?3,?4", nativeQuery = true)
    public List getUserList(String name, String dept, int offset, int pageSize);//用户列表

    @Query(value = "select count(u.id) " +
            "from user u,department d " +
            "where u.dept=d.id and  u.truename like ?1 and u.dept like ?2", nativeQuery = true)
    public int getUserListCount(String name, String dept);//用户列表

    @Query("select u.id,u.truename,u.gender,u.phone,u.idCard,u.email,u.birthday,u.dept,u.role, d.deptname, r.rolename,u.avatar from UserEntity u,DepartmentEntity d,RoleEntity r where u.id=?1 and u.dept=d.id and u.role=r.id")
    public List getUserInfo(int id);

    public UserEntity save(UserEntity u);

    public void delete(UserEntity u);

    @Query("select count(1) from UserEntity u")
    public Long getUserCount();

}
