package cn.unuai.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by SunShuai on 2018/6/6.
 */
public class RoleRepositoryImpl implements RoleRepositoryCustom {
    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List getRoles(String sql) {
        List list = entityManager.createNativeQuery(sql).getResultList();
        return list;
    }
}
