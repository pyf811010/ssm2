package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesKand;

public interface FilesKandMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesKand record);

    int insertSelective(FilesKand record);

    FilesKand selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesKand record);

    int updateByPrimaryKey(FilesKand record);
}