package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.TmpKand;
import cn.tycoding.pojo.TmpKandKey;

public interface TmpKandMapper {
    int deleteByPrimaryKey(TmpKandKey key);

    int insert(TmpKand record);

    int insertSelective(TmpKand record);

    TmpKand selectByPrimaryKey(TmpKandKey key);

    int updateByPrimaryKeySelective(TmpKand record);

    int updateByPrimaryKey(TmpKand record);
    
    int insertList(List<TmpKand> record);
    
    int selectMaxExpid();
}