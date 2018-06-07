package cn.unuai.controller;

import cn.unuai.dao.FactoryDao;
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
public class FactoryController {
    @Autowired
    FactoryDao factoryDao;

    @GetMapping("/getFactoryList")
    public Res getFactoryList() {
        List queryList = factoryDao.getFactoryList();
        String keys[] = {"id", "factoryname"};
        return new Res("success", Util.formatQueryResult(keys, queryList));
    }

    @GetMapping("/getMaxGiveupFactory")
    public Res getMaxGiveupFactory() {
        List queryList = factoryDao.getMaxGiveupFactory();
        String keys[] = {"num", "factoryName"};
        return new Res("success", Util.formatQueryResult(keys, queryList).get(0));
    }

    @GetMapping("/getMaxRepaireFactory")
    public Res getMaxRepaireFactory() {
        List queryList = factoryDao.getMaxRepaireFactory();
        String keys[] = {"num", "factoryName"};
        return new Res("success", Util.formatQueryResult(keys, queryList).get(0));
    }
}
