package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesElectromyography;

public interface FilesElectromyographyMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesElectromyography record);

    int insertSelective(FilesElectromyography record);

    FilesElectromyography selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesElectromyography record);

    int updateByPrimaryKey(FilesElectromyography record);
}