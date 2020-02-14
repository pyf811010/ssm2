package cn.tycoding.mapper;

import cn.tycoding.pojo.TmpOxygen;
import cn.tycoding.pojo.TmpOxygenKey;

public interface TmpOxygenMapper {
    int deleteByPrimaryKey(TmpOxygenKey key);

    int insert(TmpOxygen record);

    int insertSelective(TmpOxygen record);

    TmpOxygen selectByPrimaryKey(TmpOxygenKey key);

    int updateByPrimaryKeySelective(TmpOxygen record);

    int updateByPrimaryKey(TmpOxygen record);
}