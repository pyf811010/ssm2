package cn.tycoding.mapper;

import cn.tycoding.pojo.TmpMotionCapture;
import cn.tycoding.pojo.TmpMotionCaptureKey;

public interface TmpMotionCaptureMapper {
    int deleteByPrimaryKey(TmpMotionCaptureKey key);

    int insert(TmpMotionCapture record);

    int insertSelective(TmpMotionCapture record);

    TmpMotionCapture selectByPrimaryKey(TmpMotionCaptureKey key);

    int updateByPrimaryKeySelective(TmpMotionCapture record);

    int updateByPrimaryKey(TmpMotionCapture record);
}