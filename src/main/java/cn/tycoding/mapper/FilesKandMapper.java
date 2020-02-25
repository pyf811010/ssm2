package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesKand;

/**
 * @author pyf lsy fdd
 *  动力学与运动学表
 */
public interface FilesKandMapper {


    int deleteByPrimaryKey(Integer expid);

    int insert(FilesKand record);

    int insertSelective(FilesKand record);

    FilesKand selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesKand record);

    int updateByPrimaryKey(FilesKand record);
    
    FilesKand selectByIdQuery(Integer idQuery);
    
    int selectMaxExpid();
}