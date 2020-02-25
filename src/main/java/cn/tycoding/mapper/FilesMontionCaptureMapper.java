package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesMontionCapture;
/*
 * 动补数据表
 */
public interface FilesMontionCaptureMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesMontionCapture record);

    int insertSelective(FilesMontionCapture record);

    FilesMontionCapture selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesMontionCapture record);

    int updateByPrimaryKey(FilesMontionCapture record);
    
    FilesMontionCapture selectByIdQuery(Integer idQuery);
}