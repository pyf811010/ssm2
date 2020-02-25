package cn.tycoding.util;

import java.lang.reflect.Field;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class ReflectUtil {

	public static void setValues(Object model, List<Object> valueList) {

		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		try {
			for (int j = 0; j < field.length; j++) { // 遍历所有属性
				String name = field[j].getName(); // 获取属性的名字

				name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法

				Method m = model.getClass().getMethod("get" + name);
				Double value = (Double) m.invoke(model);
				if (value == null) {
					m = model.getClass().getMethod("set" + name, Double.class);
					m.invoke(model, Double.parseDouble(valueList.get(j).toString()));
				}

			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}