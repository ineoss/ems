package cn.unuai.controller;

import cn.unuai.dao.RoleDao;
import cn.unuai.dao.RoleRepository;
import cn.unuai.dao.RoleRepositoryImpl;
import cn.unuai.entity.RoleEntity;
import cn.unuai.util.Res;
import cn.unuai.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @GetMapping("/getMights")
    public Res getMights(Integer role) {
        try {

            RoleEntity roleEntity = roleDao.findById(role);
            String mightsStr = roleEntity.getMight();
            StringBuffer sb = new StringBuffer();
            sb.append(" select * from menu m");
            if (mightsStr.equals("all")) {

            } else {
                String[] mights = mightsStr.split(",");
                sb.append(" where m.id= " + mights[0]);
                for (int i = 1; i < mights.length; i++) {
                    sb.append(" or m.id =  " + mights[i]);
                }
            }
            sb.append(" order by m.index");
            List queryResult = roleRepository.getRoles(sb.toString());
            List<Map> result = new ArrayList();
            for (int i = 0; i < queryResult.size(); i++) {
                Map menus = new HashMap();
                Object[] objects = (Object[]) queryResult.get(i);
                if (objects[3].toString().equals("root")) {//一级菜单
                    menus.put("id", objects[0].toString());
                    menus.put("name", objects[2].toString());
                    menus.put("page", objects[1].toString());
                    result.add(menus);
                }
            }
            for (int i = 0; i < result.size(); i++) {
                Map temp = result.get(i);
                List<Map> submenus = new ArrayList();
                for (int j = 0; j < queryResult.size(); j++) {
                    Object[] objects = (Object[]) queryResult.get(j);
                    if (objects[3].toString().equals(temp.get("id"))) {
                        Map submenu = new HashMap();
                        submenu.put("name", objects[2].toString());
                        submenu.put("page", objects[1].toString());
                        submenu.put("icon", objects[4].toString());
                        submenu.put("color", objects[5].toString());
                        submenus.add(submenu);
                    }
                }
                if (submenus.size() > 0) {
                    temp.put("submenus", submenus);
                }
            }
            return new Res("success", result);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("exception", null);
        }
    }

}
