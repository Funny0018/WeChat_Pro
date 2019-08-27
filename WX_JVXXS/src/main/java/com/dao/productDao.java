package com.dao;

import com.entity.product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("productDao")
public interface productDao {
    public product selectByFid(@Param("fproductid") int fproductid);

    public List<product> selectByFids(Map fids);

    public List<product> selectByNone(Map<String, Object> map);

    public List<product> selectByType(Map<String, Object> map);

    public List<product> selectByNoneForWx(Map<String, Object> map);

    public List<product> selectByNoneForWxTeJia(Map<String, Object> map);

    public List<product> selectByTypeForWx(Map<String, Object> map);

    public List<product> selectByKeyAndType(Map<String, Object> map);

    public List<product> selectByKeyAndTypeTejia(Map<String, Object> map);

    public int selectCountByType(Map<String, Object> map);

    public int selectCountByKeyAndType(Map<String, Object> map);

    public int selectCountByKeyAndTypeTejia(Map<String, Object> map);

    public int selectCountByNone();

    public int selectCountByNoneTeJia();

    public List<product> selectByKey(Map<String, Object> map);

    public int selectCountByKey(@Param("key") String key);

    public int updateByFidForBack(product product);

    public int updateByFid(product product);

    public void add(product product);

    public int updateSalled(Map<String, Object> map);

    public int updatehot(Map<String, Object> map);

    public int updateSort(Map<String, Object> map);

    public int updateTop(Map<String, Object> map);

    public int updateType(Map<String, Object> map);

    public List<product> selectHotProduct();

    public List<product> selectTejiaByNone();
}
