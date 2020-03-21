package cn.tycoding.util;

import cn.tycoding.pojo.Machine;
import cn.tycoding.pojo.Subjects;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

/**
 * @author 付东东
 * @data 2020-03-19 23:09
 * @description class FindMachineInfo
 */
public class FindSubjectInfo {

    public static Integer splitString(String text) {
    	Integer text1 = Integer.valueOf(text);
    	return text1;
    }

    public static List<String> findSubject(List<Subjects> list, Integer text) {
        List<String> subjects = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
        	Subjects subject = list.get(i);
            if (text.equals(subject.getSub_id())) {
                //定制subject输出:
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("受试者ID：");
                stringBuffer.append(subject.getSub_id());
                stringBuffer.append(", 受试者身份证号：");
                stringBuffer.append(subject.getIdentity_card());
                stringBuffer.append(", 受试者姓名：");
                stringBuffer.append(subject.getName());
                stringBuffer.append(", 受试者年龄：");
                stringBuffer.append(subject.getAge());
                stringBuffer.append(", 测试日期：");
                stringBuffer.append(subject.getTestdate());
                stringBuffer.append(", 受试者身高：");
                stringBuffer.append(subject.getHeight());
                stringBuffer.append(", 受试者体重：");
                stringBuffer.append(subject.getWeight());
                stringBuffer.append(", 备注：");
                stringBuffer.append(subject.getRemark());
                stringBuffer.append("\n");
                subjects.add(stringBuffer.toString());
            }
        }
        System.out.println(subjects);
        return subjects;
    }
}
