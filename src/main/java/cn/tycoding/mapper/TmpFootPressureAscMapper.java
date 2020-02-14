package cn.tycoding.mapper;

import cn.tycoding.pojo.TmpFootPressureAsc;
import cn.tycoding.pojo.TmpFootPressureAscKey;

public interface TmpFootPressureAscMapper {
    int deleteByPrimaryKey(TmpFootPressureAscKey key);

    int insert(TmpFootPressureAsc record);

    int insertSelective(TmpFootPressureAsc record);

    TmpFootPressureAsc selectByPrimaryKey(TmpFootPressureAscKey key);

    int updateByPrimaryKeySelective(TmpFootPressureAsc record);

    int updateByPrimaryKey(TmpFootPressureAsc record);
}