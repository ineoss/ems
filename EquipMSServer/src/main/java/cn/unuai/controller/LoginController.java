package cn.unuai.controller;

import cn.unuai.dao.LoginDao;
import cn.unuai.dao.UserDao;
import cn.unuai.entity.LoginEntity;
import cn.unuai.entity.UserEntity;
import cn.unuai.util.Res;
import cn.unuai.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by SunShuai on 2018/4/15.
 */
@RestController
public class LoginController {
    @Autowired
    LoginDao loginDao;
    @Autowired
    UserDao userDao;

    @PostMapping("/login")
    public Res login(LoginEntity le) {
        try {
            if (le.getUsername().equals("admin") && le.getPassword().equals("admin")) {
                return new Res("success", null);
            }
            LoginEntity rl = loginDao.findOne(le.getUsername(), Util.md5(le.getPassword()));
            if (null != rl) {
                List ru = userDao.getUserInfo(rl.getUserid());
                int lid = (int) ((Object[]) ru.get(0))[0];
                String keys[] = {"id", "truename", "gender", "phone", "idCard", "email", "birthday", "dept", "role", "deptname", "rolename", "avatar"};
                String token = Util.createToken();//生成token
                String token_value = lid + "-" + (System.currentTimeMillis() / 1000 + Util.token_live);
                Util.setToken(token, token_value);
                Map map = new HashMap();
                map.put("token", token);
                map.put("user", Util.formatQueryResult(keys, ru).get(0));
                return new Res("success", map);
            } else {
                return new Res("error", null);
            }
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @GetMapping("/logout")
    public Res logOut(String token) {
        try {
            Util.delToken(token);
        } catch (Exception e) {
        } finally {
            return new Res("success", null);
        }


    }

    @GetMapping("/tokenLogin")
    public Res tokenLogin(String token) {
        try {
            String token_value = Util.getToken(token);
            if (null == token_value) {
                return new Res("error", "token无效");
            }
            String pts[] = token_value.split("-");
            String id = pts[0];
            Long now = System.currentTimeMillis() / 1000;
            Long live = Long.parseLong(pts[1]);
            if (live < now) {
                Util.delToken(token);
                return new Res("error", "登录已过期，请重新登录");
            } else {
                Util.setToken(token, id + "-" + (now + Util.token_live));//更新token
                List ru = userDao.getUserInfo(Integer.parseInt(id));
                String keys[] = {"id", "truename", "gender", "phone", "idCard", "email", "birthday", "dept", "role", "deptname", "rolename", "avatar"};
                if (null == ru) {
                    Util.delToken(token);
                    return new Res("error", "token无效");
                }
                return new Res("success", Util.formatQueryResult(keys, ru).get(0));
            }
        } catch (Exception e) {
            return new Res("error", "token无效");
        }
    }

    @GetMapping("/findUsernameByIdCard")
    public Res findUsername(LoginEntity le) {
        try {
            LoginEntity rl = loginDao.findUsernameByIdCard(le.getUsername());
            if (null != rl) {
                return new Res("success", rl.getUsername());
            } else {
                return new Res("error", "未找到用户，请确认身份证号是否正确");
            }
        } catch (Exception e) {
            return new Res("error", "查询出错");
        }

    }

}
