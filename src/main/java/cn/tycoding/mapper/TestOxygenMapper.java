package cn.tycoding.mapper;

import cn.tycoding.pojo.TestOxygen;
import cn.tycoding.pojo.TestOxygenKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface TestOxygenMapper {
    int deleteByPrimaryKey(TestOxygenKey key);

    int insert(TestOxygen record);

    int insertSelective(TestOxygen record);

    TestOxygen selectByPrimaryKey(TestOxygenKey key);

    int updateByPrimaryKeySelective(TestOxygen record);

    int updateByPrimaryKey(TestOxygen record);
    
    int insertList(List<TestOxygen> record);
    
    int selectMaxExpid();
}