package cn.unuai.controller;

import cn.unuai.dao.EquipmentDao;
import cn.unuai.dao.GiveupDao;
import cn.unuai.entity.EquipmentEntity;
import cn.unuai.entity.GiveupEntity;
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
public class GiveupController {
    @Autowired
    GiveupDao giveupDao;
    @Autowired
    EquipmentDao equipmentDao;

    @Transactional
    @PostMapping("/addGiveup")
    public Res addGiveup(String assetNo, int handler, String mark, Double returnPrice) {
        try {
            EquipmentEntity queryData = equipmentDao.findByAssetNo(assetNo);
            if (null != queryData) {
                if (queryData.getState() == Util.STATE_YBF) {
                    return new Res("fail", "操作失败！该设备已是【已报废】状态");
                }
                GiveupEntity ge = new GiveupEntity();
                ge.setEquipId(queryData.getId());
                ge.setHandler(handler);
                ge.setMark(mark);
                ge.setGiveupDate(Util.getToday());
                ge.setReturnPrice(returnPrice);
                giveupDao.save(ge);
                equipmentDao.setEquipmentState(queryData.getId(), Util.STATE_YBF);
                return new Res("success", null);
            } else {
                return new Res("fail", "操作失败！资产编号错误");
            }
        } catch (Exception e) {
            System.err.println(e);
            return new Res("exception", null);
        }
    }

    @GetMapping("/getGiveupList")
    public Res getGiveupList(int pageNum, int pageSize, String name) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List p = giveupDao.getGiveupList("%" + name + "%", offset, pageSize);
            int total = giveupDao.getGiveupListCount("%" + name + "%");
            int totalPage = Util.getTotalPage(total, pageSize);
            Map map = new HashMap();
            String keys[] = {"id", "equipId", "assetNo", "equipName", "giveupDate", "handler"};
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }

    }

    @GetMapping("/getGiveupInfo")
    public Res getGiveupInfo(int id) {
        try {
            List queryResult = giveupDao.getGiveupInfo(id);
            String keys[] = {"id", "equipId", "assetNo", "buyDate", "useYear", "price", "image", "equipName", "deptName", "typeName", "giveupDate", "returnPrice", "handler", "factory"};
            return new Res("success", Util.formatQueryResult(keys, queryResult).get(0));
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @PostMapping("/delGiveupAndEquip")
    public Res delGiveupAndEquip(int id, int equipId) {
        try {
            GiveupEntity g = new GiveupEntity();
            g.setId(id);
            giveupDao.delete(g);
            EquipmentEntity e = new EquipmentEntity();
            e.setId(equipId);
            equipmentDao.delete(e);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }
}
