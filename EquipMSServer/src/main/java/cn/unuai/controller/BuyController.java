package cn.unuai.controller;

import cn.unuai.dao.BuyDao;
import cn.unuai.entity.BuyEntity;
import cn.unuai.util.Res;
import cn.unuai.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunShuai on 2018/5/10.
 */
@RestController
public class BuyController {
    @Autowired
    BuyDao buyDao;

    @Transactional
    @PostMapping("/addBuyPlan")
    public Res addBuyPlan(BuyEntity buyPlan) {
        try {
            buyDao.save(buyPlan);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @GetMapping("/getBuyPlanList")
    public Res getBuyPlanList(int pageNum, int pageSize, int dept) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List p = buyDao.getBuyPlanList(dept, offset, pageSize);
            int total = buyDao.getBuyPlanListCount(dept);
            int totalPage = Util.getTotalPage(total, pageSize);
            String keys[] = {"id", "name", "equipName", "equipType", "plDate", "typeName"};
            Map map = new HashMap();
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @GetMapping("/getBuyPlanInfo")
    public Res getBuyPlanInfo(int id) {
        try {
            List queryReuslt = buyDao.getBuyPlanInfo(id);
            String keys[] = {"id", "name", "equipName", "equipType", "typeName", "plDept", "deptName", "plPrice", "plNum", "plDate", "rDate"};
            return new Res("success", Util.formatQueryResult(keys, queryReuslt).get(0));
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/delBuyPlan")
    public Res delBuyPlan(BuyEntity b) {
        try {
            buyDao.delete(b);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/modifyBuyPlan")
    public Res modifyBuyPlan(BuyEntity buyPlan) {
        try {
            buyDao.saveAndFlush(buyPlan);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @GetMapping("/isPlanEnd")
    public Res isPlanEnd(int id) {
        try {
            Object queryResult = buyDao.isPlanEnd(id);
            if (null == queryResult) {
                return new Res("success", "notEnd");
            } else {
                return new Res("success", "end");
            }
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @GetMapping("/getBuyHistory")
    public Res getBuyHistory(int pageNum, int pageSize, String equipName, int dept) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List p = buyDao.getBuyHistory("%" + equipName + "%", dept, offset, pageSize);
            int total = buyDao.getBuyHistoryCount("%" + equipName + "%", dept);
            int totalPage = Util.getTotalPage(total, pageSize);
            String keys[] = {"id", "equipName", "equipType", "typeName", "plDept", "deptName", "rDate"};
            Map map = new HashMap();
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @GetMapping("/getBuyInfo")
    public Res getBuyInfo(int id) {
        try {
            List queryReuslt = buyDao.getBuyInfo(id);
            String keys[] = {"id", "name", "equipName", "equipType", "typeName", "plDept", "deptName", "plPrice", "plNum", "plDate", "rPrice", "rNum", "rDate", "handler"};
            return new Res("success", Util.formatQueryResult(keys, queryReuslt).get(0));
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/delBuy")
    public Res delBuy(BuyEntity b) {
        try {
            buyDao.delete(b);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }
}
