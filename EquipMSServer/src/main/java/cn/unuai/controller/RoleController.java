package cn.unuai.controller;

import cn.unuai.dao.RoleDao;
import cn.unuai.dao.RoleRepository;
import cn.unuai.dao.RoleRepositoryImpl;
import cn.unuai.util.Res;
import cn.unuai.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by SunShuai on 2018/4/21.
 */
@RestController
public class RoleController {
    @Autowired
    RoleDao roleDao;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/getRoleList")
    public Res getRoleList() {
        try {
            List queryResult = roleDao.getRoleList();
            String keys[] = {"id", "rolename"};
            return new Res("success", Util.formatQueryResult(keys, queryResult));
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @GetMapping("/getRoles")
    public List getRoles() {
        try {
            StringBuffer sb=new StringBuffer();
            sb.append(" select name from menu");
            List queryResult = roleRepository.getRoles(sb.toString());
            return queryResult;
        } catch (Exception e) {
            return null;
        }
    }

}
