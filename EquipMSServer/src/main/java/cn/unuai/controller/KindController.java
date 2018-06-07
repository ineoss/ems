package cn.unuai.controller;

import cn.unuai.dao.KindDao;
import cn.unuai.util.Res;
import cn.unuai.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by SunShuai on 2018/4/25.
 */
@RestController
public class KindController {
    @Autowired
    KindDao kindDao;

    @GetMapping("/getEquipmentType")
    public Res getEquipmentType() {
        List queryList = kindDao.getEquipmentType();
        String keys[] = {"id", "name"};
        return new Res("success", Util.formatQueryResult(keys, queryList));
    }
    @GetMapping("/getEquipmentState")
    public Res getEquipmentState() {
        List queryList = kindDao.getEquipmentState();
        String keys[] = {"id", "name"};
        return new Res("success", Util.formatQueryResult(keys, queryList));
    }
}
