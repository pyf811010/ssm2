package cn.tycoding.mapper;

import cn.tycoding.pojo.TmpSlotMachine;
import cn.tycoding.pojo.TmpSlotMachineKey;

public interface TmpSlotMachineMapper {
    int deleteByPrimaryKey(TmpSlotMachineKey key);

    int insert(TmpSlotMachine record);

    int insertSelective(TmpSlotMachine record);

    TmpSlotMachine selectByPrimaryKey(TmpSlotMachineKey key);

    int updateByPrimaryKeySelective(TmpSlotMachine record);

    int updateByPrimaryKey(TmpSlotMachine record);
}