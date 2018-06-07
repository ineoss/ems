package cn.unuai.controller;

import cn.unuai.dao.DepartmentDao;
import cn.unuai.entity.DepartmentEntity;
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
public class DepartmentController {
    @Autowired
    private DepartmentDao deptDao;

    @GetMapping("/getDepartmentList")
    public Res getDepartmentList() {
        List queryResult = deptDao.getDeparementList();
        String keys[] = {"id", "deptname"};
        return new Res("success", Util.formatQueryResult(keys, queryResult));
    }
}
