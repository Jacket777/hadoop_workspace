package com.kafka.monitor.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * 参考地址： http://blog.csdn.net/lk_blog/article/details/7685169
 * http://www.cnblogs.com/shanshouchen/articles/2616059.html
 * 
 * 	http://blog.csdn.net/magicboylinw/article/details/8468554
 * http://blog.csdn.net/kevin_luan/article/details/33316955
 * http://www.verydemo.com/demo_c161_i121079.html
 * 
 * @author Administrator
 * 
 */
public class JsonUtil {

	private static Gson gson;

	public static Object json2Object(String jsonStr, Class<?> beanClass) {
		Gson gson = getGsonInstance(false);
		return gson.fromJson(jsonStr, beanClass);
	}
	
	public static Object json2Object(String jsonStr, Class<?> beanClass, String dateFormatStr) {
		Gson gson = getGsonInstance(false, dateFormatStr);
		return gson.fromJson(jsonStr, beanClass);
	}

	public static <T> T json2Bean(String jsonStr, Class<T> beanClass) {
		Gson gson = getGsonInstance(false);
		return gson.fromJson(jsonStr, beanClass);
	}
	
	public static <T> T json2Bean(String jsonStr, Class<T> beanClass, String dateFormatStr) {
		Gson gson = getGsonInstance(false, dateFormatStr);
		return gson.fromJson(jsonStr, beanClass);
	}

	public static <T> List<T> jsonArr2ObjectList(String jsonStr) {
		Gson gson = getGsonInstance(false);
		return gson.fromJson(jsonStr, List.class);
	}
	
	public static List jsonArr2ObjectList(String jsonStr, String dateFormatStr) {
		Gson gson = getGsonInstance(false, dateFormatStr);
		return gson.fromJson(jsonStr, List.class);
	}
	
	/*
	 * 用法：
	 * java.lang.reflect.Type beanType = new TypeToken<List<Student>>() {}.getType();
       List<Student> stuList = JsonUtil.jsonArrToBeanList(jsonStr, beanType);
	 */
	public static <T> List<T> jsonArr2BeanList(String jsonStr, java.lang.reflect.Type beanType) {
		Gson gson = getGsonInstance(false);
		return gson.fromJson(jsonStr, beanType);
	}
	
	/*
	 * 用法：
	 * java.lang.reflect.Type beanType = new TypeToken<List<Student>>() {}.getType();
       List<Student> stuList = JsonUtil.jsonArrToBeanList(jsonStr, beanType);
	 */
	public static <T> List<T> jsonArr2BeanList(String jsonStr, java.lang.reflect.Type beanType, String dateFormatStr) {
		Gson gson = getGsonInstance(false, dateFormatStr);
		return gson.fromJson(jsonStr, beanType);
	}

	public static String bean2Json(Object obj) {
		Gson gson = getGsonInstance(false);
		return gson.toJson(obj);
	}
	
	public static String bean2Json(Object obj, String dateFormatStr) {
		Gson gson = getGsonInstance(false, dateFormatStr);
		return gson.toJson(obj);
	}
	
	private static synchronized Gson getGsonInstance(boolean createNew) {
		String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
		if (createNew) {
			// 注意这里的Gson的构建方式为GsonBuilder,区别于Gson gson = new Gson();
			Gson gson = new GsonBuilder()
					//.excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
					.enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
					.serializeNulls().setDateFormat(dateFormatStr)// 时间转化为特定格式
					.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
					.setPrettyPrinting() // 对json结果格式化.
					.setVersion(1.0) // 有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
										// @Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
										// @Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
					.disableHtmlEscaping()
					.create();

			return gson;
		} else if (gson == null) {
			gson = new GsonBuilder()
			        //.excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
					.enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
					.serializeNulls().setDateFormat(dateFormatStr)// 时间转化为特定格式
					.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
					.setPrettyPrinting() // 对json结果格式化.
					.disableHtmlEscaping()
					.setVersion(1.0) // 有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
										// @Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
										// @Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
					.create();
		}
		return gson;
	}

	private static synchronized Gson getGsonInstance(boolean createNew, String dateFormatStr) {
		if(dateFormatStr==null || dateFormatStr.trim().length()==0) {
			dateFormatStr = "yyyy-MM-dd HH:mm:ss";
		}
		if (createNew) {
			// 注意这里的Gson的构建方式为GsonBuilder,区别于Gson gson = new Gson();
			Gson gson = new GsonBuilder()
					//.excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
					.enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
					.serializeNulls().setDateFormat(dateFormatStr)// 时间转化为特定格式
					.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
					.setPrettyPrinting() // 对json结果格式化.
					.disableHtmlEscaping()
					.setVersion(1.0) // 有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
										// @Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
										// @Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
					.create();

			return gson;
		} else if (gson == null) {
			gson = new GsonBuilder()
			        //.excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
					.enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
					.serializeNulls().setDateFormat(dateFormatStr)// 时间转化为特定格式
					.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
					.setPrettyPrinting() // 对json结果格式化.
					.setVersion(1.0) // 有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
										// @Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
										// @Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
					.disableHtmlEscaping()
					.create();
		}
		return gson;
	}

	public static String bean2JsonExpose(Object obj) {
		String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
		Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
				.enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
				.serializeNulls().setDateFormat(dateFormatStr)// 时间转化为特定格式
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
				.setPrettyPrinting() // 对json结果格式化.
				.disableHtmlEscaping()
				.setVersion(1.0) // 有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
				// @Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
				// @Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
				.create();
		return gson.toJson(obj);
	}
}
