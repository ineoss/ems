package cn.unuai;

import cn.unuai.controller.EquipmentController;
import cn.unuai.entity.EquipmentEntity;
import cn.unuai.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EquipmentManageSystemApplicationTests {

    @Test
    public void contextLoads() {
        EquipmentEntity e = new EquipmentEntity();
        for (int i = 0; i < 20000; i++) {
            e.setState(Util.STATE_ZK);
            e.setImage("/image/default.jpg");
            e.setPos("仓库");
            e.setBuyDate("2018-05-05");
            e.setBuyDept(1001);
            e.setFactory(1);
            e.setName("戴尔笔记本");
            e.setEquipType(1007);
            e.setPrice(6000.0);
            e.setUseYear(20.0);
            e.setRepaireCycle(60.0);
            new EquipmentController().addEquipment(e);
        }
        System.out.println("添加完成");
    }

}
