package cn.unuai.controller;

import cn.unuai.dao.EquipmentDao;
import cn.unuai.dao.RepaireDao;
import cn.unuai.entity.EquipmentEntity;
import cn.unuai.entity.RepaireEntity;
import cn.unuai.util.Res;
import cn.unuai.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunShuai
 */
@RestController
public class RepaireController {
    @Autowired
    RepaireDao repaireDao;
    @Autowired
    EquipmentDao equipmentDao;

    @Transactional
    @PostMapping("/addRepaire")
    public Res addRepaire(String assetNo, String mark, double price, String pos, int handler, String success, String date) {
        try {
            EquipmentEntity a = equipmentDao.findByAssetNo(assetNo);
            if (null == a) {
                return new Res("fail", "资产编号错误");
            }
            RepaireEntity r = new RepaireEntity();
            r.setEquipId(a.getId());
            r.setMark(mark);
            r.setPos(pos);
            r.setPrice(price);
            r.setHandler(handler);
            r.setRepaireDate(date);
            r.setSuccess(success);
            repaireDao.save(r);
            equipmentDao.setLastRepaireDate(date, assetNo);
            return new Res("success", null);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("exception", null);
        }
    }

    @GetMapping("/getNeedHaul")
    public Res getNeedHaul(int pageNum, int pageSize, String name) {
        try {
            Pageable pageable = new PageRequest(pageNum - 1, pageSize);
            String today = Util.getToday();
            int error = Util.REPAIRE_DATE_ERROR;
            String keys[] = {"id", "assetNo", "equipName", "pos", "lastRepaire"};
            int offset = (pageNum - 1) * pageSize;
            List p = repaireDao.getNeedOverhaul(today, error, "%" + name + "%", offset, pageSize);
            int total = repaireDao.getNeedOverhaulCount(today, error, "%" + name + "%");
            int totalPage = Util.getTotalPage(total, pageSize);
            Map map = new HashMap();
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            return new Res("exception", null);
        }
    }

    @GetMapping("/getRepaireHistory")
    public Res getRepaireHistory(int pageNum, int pageSize, String assetNo) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List result = repaireDao.getRepaireHistory("%" + assetNo + "%", offset, pageSize);
            Map map = new HashMap();
            String keys[] = {"id", "assetNo", "equipName", "dept", "repaireDate"};
            int total = repaireDao.getRepaireHistoryCount("%" + assetNo + "%");
            int totalPage = Util.getTotalPage(total, pageSize);
            map.put("list", Util.formatQueryResult(keys, result));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("exception", null);
        }
    }

    @GetMapping("/getRepaireInfo")
    public Res getRepaireInfo(int id) {
        try {
            List queryResult = repaireDao.getRepaireInfo(id);
            String keys[] = {"id", "assetNo", "equipName", "image", "handler", "repaireDate", "pos", "mark", "success", "deptName", "typeName"};

            return new Res("success", Util.formatQueryResult(keys, queryResult).get(0));
        } catch (Exception e) {
            System.err.println(e);
            return new Res("exception", null);
        }
    }

    @PostMapping("/delRepaire")
    public Res delRepaire(RepaireEntity r) {
        try {
            repaireDao.delete(r);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("exception", null);
        }
    }
}
