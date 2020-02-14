package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesFootPressureFgt;

public interface FilesFootPressureFgtMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesFootPressureFgt record);

    int insertSelective(FilesFootPressureFgt record);

    FilesFootPressureFgt selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesFootPressureFgt record);

    int updateByPrimaryKey(FilesFootPressureFgt record);
}