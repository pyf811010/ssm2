package cn.tycoding.mapper;

import cn.tycoding.pojo.Query;

public interface QueryMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(Query record);

    int insertSelective(Query record);

    Query selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(Query record);

    int updateByPrimaryKey(Query record);
    
    int selectMaxExpid();
}