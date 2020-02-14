package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesFootPressureAsc;

public interface FilesFootPressureAscMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesFootPressureAsc record);

    int insertSelective(FilesFootPressureAsc record);

    FilesFootPressureAsc selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesFootPressureAsc record);

    int updateByPrimaryKey(FilesFootPressureAsc record);
}