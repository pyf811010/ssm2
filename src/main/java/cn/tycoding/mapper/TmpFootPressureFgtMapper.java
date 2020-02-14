package cn.tycoding.mapper;

import cn.tycoding.pojo.TmpFootPressureFgt;
import cn.tycoding.pojo.TmpFootPressureFgtKey;

public interface TmpFootPressureFgtMapper {
    int deleteByPrimaryKey(TmpFootPressureFgtKey key);

    int insert(TmpFootPressureFgt record);

    int insertSelective(TmpFootPressureFgt record);

    TmpFootPressureFgt selectByPrimaryKey(TmpFootPressureFgtKey key);

    int updateByPrimaryKeySelective(TmpFootPressureFgt record);

    int updateByPrimaryKey(TmpFootPressureFgt record);
}