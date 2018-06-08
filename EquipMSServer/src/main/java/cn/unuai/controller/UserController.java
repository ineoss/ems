package cn.unuai.controller;

import cn.unuai.dao.*;
import cn.unuai.entity.LoginEntity;
import cn.unuai.entity.UserEntity;
import cn.unuai.util.Res;
import cn.unuai.util.Util;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunShuai on 2018/4/21.
 */
@RestController
public class UserController {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginDao loginDao;
    @Autowired
    DepartmentDao departmentDao;
    @Autowired
    RoleDao roleDao;

    /**
     * 添加用户
     *
     * @param ue
     * @return
     */
    @Transactional
    @PostMapping("/addUser")
    public Res addUser(UserEntity ue) {
        try {
            ue.setAvatar("/avatar/default_avatar.jpg");
            UserEntity ru = userDao.save(ue);//保存用户
            String username = "";
            String truename = ue.getTruename();
            if (Util.isEnglish(truename)) {
                username = truename.split(" ")[0] + ru.getId();
            } else {
                username = Util.getPinyinHeader(truename) + ru.getId();
            }
            LoginEntity le = new LoginEntity();
            le.setUsername(username);
            le.setPassword(Util.md5("123456"));
            le.setUserid(ru.getId());
            loginDao.save(le);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }

    }

    @GetMapping("/getUserList")
    public Res getUserList(int pageSize, int pageNum, String dept, String name) {
        try {
            int offset = (pageNum - 1) * pageSize;
            Pageable pageable = new PageRequest(pageNum - 1, pageSize);
            List p = userDao.getUserList("%" + name + "%", "%" + dept + "%", offset, pageSize);
            int total = userDao.getUserListCount("%" + name + "%", "%" + dept + "%");
            int totalPage = Util.getTotalPage(total, pageSize);
            Map map = new HashMap();
            String keys[] = {"id", "truename", "deptname"};
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }

    }

    @GetMapping("/getUserInfo")
    public Res getUserInfo(int id) {
        try {
            List queryResult = userDao.getUserInfo(id);
            String keys[] = {"id", "truename", "gender", "phone", "idCard", "email", "birthday", "dept", "role", "deptname", "rolename", "avatar"};

            return new Res("success", Util.formatQueryResult(keys, queryResult).get(0));
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/delUser")
    public Res delUser(UserEntity ue) {
        try {
            userDao.delete(ue);
            loginDao.deleteByUserId(ue.getId());
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/modifyUser")
    public Res modifyUser(UserEntity ue) {
        try {
            userDao.saveAndFlush(ue);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @GetMapping("/getUserCount")
    public Res getUserCount() {
        try {
            Long count = userDao.getUserCount();
            return new Res("success", count);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }
}
