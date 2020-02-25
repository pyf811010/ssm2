package cn.tycoding.mapper;

import java.util.List;
import cn.tycoding.pojo.TmpMotionCapture;
import cn.tycoding.pojo.TmpMotionCaptureKey;

public interface TmpMotionCaptureMapper {
	int deleteByPrimaryKey(TmpMotionCaptureKey key);

    int insert(TmpMotionCapture record);

    int insertSelective(TmpMotionCapture record);

    TmpMotionCapture selectByPrimaryKey(TmpMotionCaptureKey key);
    
    int updateByPrimaryKeySelective(TmpMotionCapture record);

    int updateByPrimaryKey(TmpMotionCapture record);
    
    int insertList(List<TmpMotionCapture> record);
    
    int selectMaxExpid();

    List findByPage(int i, int rows);
    
    int getSum();
    
    List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	void del(String expid, String id);

	int add(TmpMotionCapture tmpMC);
	
	void truncate();
}