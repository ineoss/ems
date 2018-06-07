package cn.unuai.dao;

import cn.unuai.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by SunShuai on 2018/4/15.
 */
public interface LoginDao extends JpaRepository<LoginEntity, Integer> {
    public LoginEntity save(LoginEntity le);

    @Query("select l from LoginEntity l where username=?1 and password=?2")
    public LoginEntity findOne(String username, String password);

    @Query("select le from LoginEntity le,UserEntity ue  where ue.idCard=?1 and le.userid=ue.id")
    public LoginEntity findUsernameByIdCard(String idCard);

    @Transactional
    @Modifying
    @Query("delete from LoginEntity where userid = ?1")
    public void deleteByUserId(int userid);
}
