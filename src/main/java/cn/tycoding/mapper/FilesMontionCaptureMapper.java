package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesMontionCapture;

public interface FilesMontionCaptureMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesMontionCapture record);

    int insertSelective(FilesMontionCapture record);

    FilesMontionCapture selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesMontionCapture record);

    int updateByPrimaryKey(FilesMontionCapture record);
}