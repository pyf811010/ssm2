package cn.tycoding.util;

import cn.tycoding.pojo.Machine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 付东东
 * @data 2020-03-19 23:09
 * @description class FindMachineInfo
 */
public class FindMachineInfo {

    public static List<Integer> splitString(String[] text) {
        String[] split = text[0].split("\\、");
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            integers.add(Integer.valueOf(split[i]));
        }
        return integers;
    }

    public static List<String> findMachine(List<Machine> list, List<Integer> integers) {
        List<String> machines = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            Machine machine = list.get(i);
            if (integers.contains(machine.getM_id())) {
                //定制machine输出:
                //"测试设备ID："+ machine.m_id + ", 设备名称：" + machine.name + ", 设备型号：" +
                // machine.type + ", 所属单位：" + machine.company + ", 布置场地：" + machine.place;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("测试设备ID：");
                stringBuffer.append(machine.getM_id());
                stringBuffer.append(", 设备名称：");
                stringBuffer.append(machine.getName());
                stringBuffer.append(", 设备型号：");
                stringBuffer.append(machine.getType());
                stringBuffer.append(", 所属单位：");
                stringBuffer.append(machine.getCompany());
                stringBuffer.append(", 布置场地：");
                stringBuffer.append(machine.getPlace());
                stringBuffer.append("\n");
                machines.add(stringBuffer.toString());
            }
        }
        System.out.println(machines);
        return machines;
    }
}
