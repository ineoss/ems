package cn.unuai.controller;

import cn.unuai.dao.*;
import cn.unuai.entity.*;
import cn.unuai.util.Res;
import cn.unuai.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunShuai on 2018/4/25.
 */
@RestController
public class EquipmentController {
    @Autowired
    EquipmentDao equipmentDao;
    @Autowired
    InstockDao instockDao;
    @Autowired
    OutstockDao outstockDao;
    @Autowired
    StockDao stockDao;
    @Autowired
    BorrowDao borrowDao;

    @Transactional
    @PostMapping("/addEquipment")
    public Res addEquipment(EquipmentEntity e) {
        try {
            //生成资产编号( 购置日期+分类号+部门编号+ +该类设备循环编号（1-999））
            String assetNo = "";
            int type = e.getEquipType();
            String date = e.getBuyDate().replace("-", "").substring(2);
            assetNo += date + type + e.getBuyDept();
            Integer max = equipmentDao.getMaxAssetNo(type);
            if (null == max) {
                assetNo += "001";
            } else {
                int t = max.intValue() + 1 > 999 ? 1 : max.intValue() + 1;
                //不足三位补0
                int len = 3 - ("" + t).length();
                for (int i = 0; i < len; i++) {
                    assetNo += "0";
                }
                assetNo += t;
            }

            e.setAssetNo(assetNo);
            e.setImage("/images/default.jpg");
            EquipmentEntity se = equipmentDao.save(e);
            if (e.getState() == Util.STATE_ZK) {
                stockDao.addStockNum(e.getName(), 1);
            }

            Map map = new HashMap();
            map.put("id", se.getId());
            map.put("assetNo", assetNo);
            return new Res("success", map);
        } catch (Exception ex) {
            System.err.println(ex);
            return new Res("exception", null);
        }
    }

    @Transactional
    @PostMapping("/inStock")
    public Res instock(int handler, String assetNo, int dept, String mark) {
        try {
            EquipmentEntity queryData = equipmentDao.findByAssetNo(assetNo);
            if (null != queryData) {
                if (queryData.getState() == Util.STATE_ZK) {
                    return new Res("fail", "设备已是【在库】状态，无法进行【入库】处理");
                }

                String instockDate = Util.getToday();
                InstockEntity ie = new InstockEntity();
                ie.setEquipId(queryData.getId());
                ie.setHandler(handler);
                ie.setInstockDate(instockDate);
                ie.setInstockDept(dept);
                ie.setMark(mark);
                instockDao.save(ie);
                queryData.setState(Util.STATE_ZK);
                queryData.setPos("仓库");
                equipmentDao.save(queryData);
                stockDao.addStockNum(queryData.getName(), 1);
                return new Res("success", "入库成功");
            } else {
                return new Res("fail", "资产编号错误");
            }
        } catch (Exception ex) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/outStock")
    public Res outstock(int handler, String assetNo, int dept, String mark, String pos) {
        try {
            EquipmentEntity queryData = equipmentDao.findByAssetNo(assetNo);
            if (null != queryData) {
                if (queryData.getState() != Util.STATE_ZK) {
                    return new Res("fail", "设备不是【在库】状态，无法进行【出库】处理");
                }
                String instockDate = Util.getToday();
                OutstockEntity oe = new OutstockEntity();
                oe.setEquipId(queryData.getId());
                oe.setHandler(handler);
                oe.setOutstockDate(instockDate);
                oe.setOutStockDept(dept);
                oe.setMark(mark);
                outstockDao.save(oe);
                queryData.setState(Util.STATE_SYZ);
                queryData.setPos(pos);
                equipmentDao.save(queryData);
                stockDao.addStockNum(queryData.getName(), -1);
                return new Res("success", "出库成功");
            } else {
                return new Res("fail", "资产编号错误");
            }
        } catch (Exception ex) {
            return new Res("error", null);
        }
    }

    @GetMapping("/getStockList")
    public Res getStockList(int pageSize, int pageNum, String name) {
        try {
            Pageable page = new PageRequest(pageNum - 1, pageSize);
            Page<List> p = null;
            p = stockDao.getStockList(page, "%" + name + "%");
            Map map = new HashMap();
            map.put("list", p.getContent());
            map.put("totalPage", p.getTotalPages());
            return new Res("success", map);
        } catch (Exception e) {
            return new Res("error", null);
        }

    }

    @GetMapping("/getEquipList")
    public Res getEquipList(int pageSize, int pageNum, String name, String state, String type) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List p = equipmentDao.getEquipList("%" + name + "%", "%" + state + "%", "%" + type + "%", offset, pageSize);
            int total = equipmentDao.getEquipListCount("%" + name + "%", "%" + state + "%", "%" + type + "%");
            int totalPage = Util.getTotalPage(total, pageSize);
            String keys[] = {"id", "name", "assetNo", "typeName", "stateName"};
            Map map = new HashMap();
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            return new Res("error", null);
        }

    }

    @GetMapping("/getEquipmentInfo")
    public Res getEquipmentInfo(int id) {
        try {
            String keys[] = {"id", "name", "assetNo", "factory", "factoryname", "buyDate", "pos", "price", "buyDept", "deptname", "useYear", "image", "equipType", "typename", "state", "statename", "repaireCycle"};
            HashMap info = (HashMap) Util.formatQueryResult(keys, equipmentDao.getEquipmentInfo(id)).get(0);
            int type = (int) info.get("equipType");
            Double age = equipmentDao.getAvgAge(type);//获取平均寿命
            if (age != null) {

                info.put("age", Util.keep2Point(age.doubleValue()));
            } else {
                info.put("age", "-");
            }
            return new Res("success", info);
        } catch (Exception e) {
            return new Res("exception", null);
        }
    }

    @Transactional
    @PostMapping("/delEquip")
    public Res delEquip(EquipmentEntity e) {
        try {
            equipmentDao.delete(e);
            stockDao.addStockNum(e.getName(), -1);
            return new Res("success", null);
        } catch (Exception ex) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/modifyEquip")
    public Res modifyEquip(EquipmentEntity e) {
        try {
            EquipmentEntity q = equipmentDao.getOne(e.getId());
            if (q.getState() == Util.STATE_ZK && e.getState() != Util.STATE_ZK) {//由在库状态改为不在库状态
                stockDao.addStockNum(e.getName(), -1);
            } else if (q.getState() != Util.STATE_ZK && e.getState() == Util.STATE_ZK) {//由不在库状态改为在库状态
                stockDao.addStockNum(e.getName(), 1);
            }
            equipmentDao.saveAndFlush(e);
            return new Res("success", null);
        } catch (Exception ex) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/modifyStock")
    public Res modifyStock(StockEntity stock) {
        try {
            stockDao.saveAndFlush(stock);
            return new Res("success", null);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @GetMapping("/getCanBorrowList")
    public Res getCanBorrowList(int pageNum, int pageSize, String name, int dept, String type) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List p = borrowDao.getCanBorrowList("%" + name + "%", dept, "%" + type + "%", offset, pageSize);
            int total = borrowDao.getCanBorrowListCount("%" + name + "%", dept, "%" + type + "%");
            int totalPage = Util.getTotalPage(total, pageSize);
            String keys[] = {"id", "equipName", "assetNo", "type", "dept", "deptName", "typeName"};
            Map map = new HashMap();
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/borrowEquip")
    public Res borrowEquip(BorrowEntity be) {
        try {
            int equipId = be.getEquipId();
            BorrowEntity queryResult = borrowDao.getByEquipId(equipId);
            if (null == queryResult) {
                be.setApplyDate(Util.getToday());
                borrowDao.save(be);
                return new Res("success", null);
            } else {
                return new Res("fail", "该设备已被借用");
            }

        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @GetMapping("/getBorrowAuditingList")
    public Res getBorrowAuditingList(int pageNum, int pageSize, int dept) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List p = borrowDao.getBorrowAuditingList(dept, offset, pageSize);
            int total = borrowDao.getBorrowAuditingListCount(dept);
            int totalPage = Util.getTotalPage(total, pageSize);
            String keys[] = {"id", "equipId", "equipName", "typeName", "applyDate"};
            Map map = new HashMap();
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @GetMapping("/getBorrowDetail")
    public Res getBorrowDetail(int id) {
        try {
            List queryResult = borrowDao.getBorrowDetail(id);
            String keys[] = {"id", "equipId", "equipName", "typeName", "userId", "truename", "deptName", "applyDate"};
            return new Res("success", Util.formatQueryResult(keys, queryResult).get(0));
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/modifyBorrow")
    public Res modifyBorrow(BorrowEntity be) {
        try {
            be.setAudiDate(Util.getToday());
            borrowDao.saveAndFlush(be);
            return new Res("success", null);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @GetMapping("/getBorrowedList")
    public Res getBorrowedList(int pageNum, int pageSize, String equipName) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List queryResult = borrowDao.getBorrowedList("%" + equipName + "%", offset, pageSize);
            int total = borrowDao.getBorrowedListCount("%" + equipName + "%");
            int totalPage = Util.getTotalPage(total, pageSize);
            System.err.println(total);
            System.err.println(totalPage);
            String keys[] = {"id", "equipId", "assetNo", "equipName", "typeName", "outDept", "inDept"};
            Map map = new HashMap();
            map.put("list", Util.formatQueryResult(keys, queryResult));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @GetMapping("/getBorrowedDetail")
    public Res getBorrowedDetail(int id) {
        try {
            List queryResult = borrowDao.getBorrowedDetail(id);
            String keys[] = {"id", "equipId", "assetNo", "equipName", "typeName", "userId", "truename", "deptName", "audiDate"};
            return new Res("success", Util.formatQueryResult(keys, queryResult).get(0));
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @Transactional
    @PostMapping("/returnEquip")
    public Res returnEquip(int id, String damage, String damageDesc, int equipId, int userid) {
        try {
            String rDate = Util.getToday();
            borrowDao.returnEquip(id, damage, damageDesc, rDate, userid);
            equipmentDao.setEquipmentState(equipId, Util.STATE_ZK);
            return new Res("success", null);
        } catch (Exception e) {
            System.err.println(e);
            return new Res("error", null);
        }
    }

    @GetMapping("/getTypeCount")
    public Res getTypeCount() {
        try {
            String keys[] = {"value", "name"};
            List queryResult = equipmentDao.getTypeCount();
            return new Res("success", Util.formatQueryResult(keys, queryResult));
        } catch (Exception e) {
            return new Res("exception", null);
        }
    }

    @GetMapping("/getMyBorrowApply")
    public Res getMyBorrowApply(int pageNum, int pageSize, int userid) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List p = borrowDao.getApplyByUserid(userid, offset, pageSize);
            int total = borrowDao.getApplyByUseridCount(userid);
            int totalPage = Util.getTotalPage(total, pageSize);
            String keys[] = {"assetNo", "equipName", "deptName", "audiDate", "agree"};
            Map map = new HashMap();
            map.put("list", Util.formatQueryResult(keys, p));
            map.put("totalPage", totalPage);
            return new Res("success", map);
        } catch (Exception e) {
            return new Res("exception", null);
        }
    }

    @GetMapping("/getEquipmentCountInfo")
    public Res getEquipmentCountByState() {
        try {
            int count = equipmentDao.getEquipmentCountByState("%");//总设备数量
            int stockCount = equipmentDao.getEquipmentCountByState("%" + Util.STATE_ZK);//在库设备数量
            int usingCount = equipmentDao.getEquipmentCountByState("%" + Util.STATE_SYZ);//在库设备数量
            float totalAsset = equipmentDao.getotalAssets();
            String usingRate = Util.keep2Point((float) usingCount / (float) count * 100) + "%";
            int overNum = stockDao.getOverNum();
            HashMap map = new HashMap();
            map.put("count", count);
            map.put("stockCount", stockCount);
            map.put("usingCount", usingCount);
            map.put("usingRate", usingRate);
            map.put("totalAsset", totalAsset);
            map.put("overNum", overNum);
            return new Res("success", map);
        } catch (Exception e) {
            return new Res("error", null);
        }
    }

}
