package com.utils;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.bean.BeanUtil;

import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * Mybatis-Plus工具类
 */
public class MPUtil {
	public static final char UNDERLINE = '_';

	public static EntityWrapper<?> likeOrEqWithAlias(EntityWrapper<?> wrapper, Object bean, String alias) {
		if (bean == null) return wrapper;

		Map<String, Object> map = BeanUtil.beanToMap(bean, true, true);
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			if (i > 0) wrapper.and();
			Map.Entry<String, Object> entry = it.next();
			String key = alias + "." + camelToUnderline(entry.getKey());

			if (entry.getValue() != null) {
				String value = entry.getValue().toString();
				if (value.contains("%")) {
					wrapper.like(key, value.replace("%", ""));
				} else {
					wrapper.eq(key, value);
				}
			}
			i++;
		}
		return wrapper;
	}

	// 新增方法：带表别名的between
	public static EntityWrapper<?> betweenWithAlias(EntityWrapper<?> wrapper, Map<String, Object> params, String alias) {
		for (String key : params.keySet()) {
			String columnName = "";
			if (key.endsWith("_start")) {
				columnName = key.substring(0, key.indexOf("_start"));
				if (StringUtils.isNotBlank(params.get(key).toString())) {
					wrapper.ge(alias + "." + columnName, params.get(key));
				}
			}
			if (key.endsWith("_end")) {
				columnName = key.substring(0, key.indexOf("_end"));
				if (StringUtils.isNotBlank(params.get(key).toString())) {
					wrapper.le(alias + "." + columnName, params.get(key));
				}
			}
		}
		return wrapper;
	}

	// 新增方法：带表别名的sort
	public static EntityWrapper<?> sortWithAlias(EntityWrapper<?> wrapper, Map<String, Object> params, String alias) {
		List<String> orderList = new ArrayList<>();
		List<String> sortList = new ArrayList<>();

		if (params.get("order") != null && StringUtils.isNotBlank(params.get("order").toString())) {
			orderList = Arrays.asList(params.get("order").toString().split(","));
		}
		if (params.get("sort") != null && StringUtils.isNotBlank(params.get("sort").toString())) {
			sortList = Arrays.asList(params.get("sort").toString().split(","));
		}

		if (orderList.size() == sortList.size()) {
			for (int i = 0; i < orderList.size(); i++) {
				String sortColumn = alias + "." + sortList.get(i);
				if (orderList.get(i).equalsIgnoreCase("desc")) {
					wrapper.orderDesc(Collections.singleton(sortColumn));
				} else {
					wrapper.orderAsc(Collections.singleton(sortColumn));
				}
			}
		}
		return wrapper;
	}

	
	//mybatis plus allEQ 表达式转换
		public static Map allEQMapPre(Object bean,String pre) {
		   Map<String, Object> map =BeanUtil.beanToMap(bean);
		  return camelToUnderlineMap(map,pre);
	   }

		//mybatis plus allEQ 表达式转换
		public static Map allEQMap(Object bean) {
		   Map<String, Object> map =BeanUtil.beanToMap(bean);
		   return camelToUnderlineMap(map,"");
	   }

		public static Wrapper allLikePre(Wrapper wrapper,Object bean,String pre) {
			   Map<String, Object> map =BeanUtil.beanToMap(bean);
			   Map result = camelToUnderlineMap(map,pre);
			 
			return genLike(wrapper,result);
		}
	
		public static Wrapper allLike(Wrapper wrapper,Object bean) {
			  Map result = BeanUtil.beanToMap(bean, true, true);			 
			return genLike(wrapper,result);
		}
	
	
		public static Wrapper genLike( Wrapper wrapper,Map param) {
			Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
			int i=0;
			while (it.hasNext()) {
				if(i>0) wrapper.and();
				Map.Entry<String, Object> entry = it.next();
				String key = entry.getKey();
				String value = (String) entry.getValue();
				wrapper.like(key, value);
				i++;
			}
			return wrapper;
		}
		
		public static Wrapper likeOrEq(Wrapper wrapper,Object bean) {
			  Map result = BeanUtil.beanToMap(bean, true, true);			 
			return genLikeOrEq(wrapper,result);
		}
		
		public static Wrapper genLikeOrEq( Wrapper wrapper,Map param) {
			Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
			int i=0;
			while (it.hasNext()) {
				if(i>0) wrapper.and();
				Map.Entry<String, Object> entry = it.next();
				String key = entry.getKey();
				if(entry.getValue().toString().contains("%")) {
					wrapper.like(key, entry.getValue().toString().replace("%", ""));
				} else {
					wrapper.eq(key, entry.getValue());
				}
				i++;
			}
			return wrapper;
		}
		
		public static Wrapper allEq(Wrapper wrapper,Object bean) {
			  Map result = BeanUtil.beanToMap(bean, true, true);			 
			return genEq(wrapper,result);
		}
	
	
		public static Wrapper genEq( Wrapper wrapper,Map param) {
			Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
			int i=0;
			while (it.hasNext()) {
				if(i>0) wrapper.and();
				Map.Entry<String, Object> entry = it.next();
				String key = entry.getKey();
				wrapper.eq(key, entry.getValue());
				i++;
			}
			return wrapper;
		}
	
	
		public static Wrapper between(Wrapper wrapper,Map<String, Object> params) {
			for(String key : params.keySet()) {
				String columnName = "";
				if(key.endsWith("_start")) {
					columnName = key.substring(0, key.indexOf("_start"));
					if(StringUtils.isNotBlank(params.get(key).toString())) {
						wrapper.ge(columnName, params.get(key));
					}
				}
				if(key.endsWith("_end")) {
					columnName = key.substring(0, key.indexOf("_end"));
					if(StringUtils.isNotBlank(params.get(key).toString())) {
						wrapper.le(columnName, params.get(key));
					}
				}
			}
			return wrapper;
		}
	
		public static Wrapper sort2(Wrapper wrapper,Map<String, Object> params) {
			String order = "";
			if(params.get("order") != null && StringUtils.isNotBlank(params.get("order").toString())) {
				order = params.get("order").toString();
			}
			if(params.get("sort") != null && StringUtils.isNotBlank(params.get("sort").toString())) {
				if(order.equalsIgnoreCase("desc")) {
					wrapper.orderDesc(Arrays.asList(params.get("sort")));
				} else {
					wrapper.orderAsc(Arrays.asList(params.get("sort")));
				}
			}
			return wrapper;
		}

        public static Wrapper sort(Wrapper wrapper,Map<String, Object> params) {
            List<String> orderList = new ArrayList<String>();
            List<String> sortList = new ArrayList<String>();
            if(params.get("order") != null && StringUtils.isNotBlank(params.get("order").toString())) {
                orderList = Arrays.asList(params.get("order").toString().split(","));
            }
            if(params.get("sort") != null && StringUtils.isNotBlank(params.get("sort").toString())) {
                sortList = Arrays.asList(params.get("sort").toString().split(","));
            }
            if(orderList!=null && sortList!=null && orderList.size()==sortList.size()) {
                for(int i=0; i<orderList.size(); i++) {
                    if(orderList.get(i).equalsIgnoreCase("desc")) {
                        wrapper.orderDesc(Arrays.asList(sortList.get(i)));
                    } else {
                        wrapper.orderAsc(Arrays.asList(sortList.get(i)));
                    }
                }
            }
            return wrapper;
        }
	
	
	/**
	 * 驼峰格式字符串转换为下划线格式字符串
	 * 
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static void main(String[] ages) {
		System.out.println(camelToUnderline("ABCddfANM"));
	}
	
	public static Map camelToUnderlineMap(Map param, String pre) {

		Map<String, Object> newMap = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = camelToUnderline(key);
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {

				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		return newMap;
	}
}
