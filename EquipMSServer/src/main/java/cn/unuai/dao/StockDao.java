package cn.unuai.dao;

import cn.unuai.entity.StockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SunShuai
 */
public interface StockDao extends JpaRepository<StockEntity, Long> {

    @Modifying
    @Query(value = "insert into stock(equip_name,num) " +
            "values(?1,1) on duplicate key update num = num+?2", nativeQuery = true)
    public void addStockNum(String equipname, int num);

    @Query("select se from StockEntity se where se.equipName like ?1 order by se.equipName")
    public Page<List> getStockList(Pageable pageable, String equipname);

    @Query("select sum(num) from StockEntity")
    public Long getStockNum();

    @Query("select count(s.id) from StockEntity  s where s.num<s.min or s.num>s.max ")
    public int getOverNum();
}
