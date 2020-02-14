package cn.tycoding.mapper;

import cn.tycoding.pojo.FilesSlotMachine;

public interface FilesSlotMachineMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesSlotMachine record);

    int insertSelective(FilesSlotMachine record);

    FilesSlotMachine selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesSlotMachine record);

    int updateByPrimaryKey(FilesSlotMachine record);
}