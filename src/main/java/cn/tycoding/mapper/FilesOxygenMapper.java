    
package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesOxygen;

public interface FilesOxygenMapper {

    int deleteByPrimaryKey(Integer expid);

    int insert(FilesOxygen record);

    int insertSelective(FilesOxygen record);

    FilesOxygen selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesOxygen record);

    int updateByPrimaryKey(FilesOxygen record);
    
    int selectMaxExpid();
}