package com.fzd.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fzd.ali.po.BasePo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ElasticSearch助手类
 * @ClassName:  ElasticSearchUtils
 * @author: chenyang
 * @date:   2017年7月18日 下午5:43:26
 *
 */
public class ElasticSearchUtils {

	/**
	 * 将一个Map格式的数据（key,value）插入索引（指定_id，一般是业务数据的id，及elasticSearch和关系型数据使用同一个id，方便同关系型数据库互动）
	 *
	 * @param type 类型（对应数据库表）
	 * @param docId id，对应elasticSearch中的_id字段
	 * @param mapParam Map格式的数据
	 * @return
	 */
	@Deprecated
	public static boolean addDoc(String type, String docId, Map<String, Object> mapParam) {
		return ElasticSearchUtilsImp.addMapDocToIndex(type, docId, mapParam);
	}

	/**
	 * 将一个Map格式的数据（key,value）插入索引 （使用默认_id）
	 *
	 * @param type 类型（对应数据库表）
	 * @param mapParam Map格式的数据
	 * @return
	 */
	@Deprecated
	public static boolean addDoc(String type, Map<String, Object> mapParam) {
		return ElasticSearchUtilsImp.addMapDocToIndex(type, null, mapParam);
	}

	/**
	 * 将一个实体存入到默认索引的类型中（默认_id）
	 *
	 * @param type 类型（对应数据库表）
	 * @param entity 要插入的实体
	 * @param
	 * @return
	 */
	@Deprecated
	public static boolean addDoc(String type, Object entity) {
		return ElasticSearchUtilsImp.addEntityDoc(type, type, null, entity);
	}

	public static boolean addEntityDoc(String index,String type,Object entity){
		return ElasticSearchUtilsImp.addEntityDoc(index, type, null, entity);
	}

	/**
	 * 将一个实体存入到默认索引的类型中（指定_id，一般是业务数据的id，及elasticSearch和关系型数据使用同一个id，方便同关系型数据库互动）
	 *
	 * @param type 类型（对应数据库表）
	 * @param docId id，对应elasticSearch中的_id字段
	 * @param entity 要插入的实体
	 * @param
	 * @return
	 */
	@Deprecated
	public static boolean addDoc(String type, String docId, Object entity) {
		return ElasticSearchUtilsImp.addEntityDoc(type, type, docId, entity);
	}


	/**
	 * 将一个实体存入到默认索引的类型中（默认_id）
	 *
	 * @param type 类型（对应数据库表）
	 * @param json
	 * @return
	 */
	@Deprecated
	public static String addDoc(String type, String json) {
		return ElasticSearchUtilsImp.addJsonDoc(type,type, null, json);
	}
	/**
	 * 将一个实体存入到默认索引的类型中（指定_id，一般是业务数据的id，及elasticSearch和关系型数据使用同一个id，方便同关系型数据库互动）
	 *
	 * @param type 类型（对应数据库表）
	 * @param docId id，对应elasticSearch中的_id字段
	 * @param json
	 * @return
	 */
	@Deprecated
	public static String addDoc(String type, String docId, String json) {
		return ElasticSearchUtilsImp.addJsonDoc(type,type, docId, json);
	}
	/**
	 * 将一个实体存入到索引和类型中
	 *
	 * @param index
	 * @param type
	 * @param docId  null代表新增,不为null则为更新
	 * @param json
	 * @return
	 */
	public static String addDoc(String index, String type, String docId, String json) {
		return ElasticSearchUtilsImp.addJsonDoc(index, type, docId, json);
	}


	public static <T extends BasePo> boolean batchAdd(String index, String type, List<T> objList){
		return ElasticSearchUtilsImp.batchAdd(index, type, objList);
	}
	/**
	 * 删除文档
	 *
	 * @param type 类型（对应数据库表）
	 * @param docId 类型中id
	 * @return
	 */
	@Deprecated
	public static boolean deleteById(String type, String docId) {
		return ElasticSearchUtilsImp.deleteById(type,type, docId);
	}

	public static boolean deleteById(String index,String type, String docId)
	{
		return ElasticSearchUtilsImp.deleteById(index,type, docId);
	}

	public static String bulkDetlete(String index, QueryBuilder queryBuilder){
		return ElasticSearchUtilsImp.bulkDetlete(index, queryBuilder);
	}
	/**
	 * 修改文档
	 *
	 * @param type 类型
	 * @param docId 文档id
	 * @param updateParam 需要修改的字段和值
	 * @return
	 */
	public static boolean updateDoc(String type, String docId, Map<String, ?> updateParam) {
		return ElasticSearchUtilsImp.updateDoc(type, docId, updateParam);
	}


	// --------------------以下是各种搜索方法--------------------------

	/**
	 * 高亮搜索
	 *
	 * @param type 类型
	 * @param fieldName 段
	 * @param keyword 段值
	 * @return
	 */
	public static Map<String, Object> searchDocHighlight(String type, String fieldName, String keyword) {
		return ElasticSearchUtilsImp.searchDocHighlight(type, fieldName, keyword, 0, 10);
	}

	/**
	 * 高亮搜索
	 *
	 * @param type 类型
	 * @param fieldName 段
	 * @param keyword 关键词
	 * @param from 开始行数
	 * @param size 每页大小
	 * @return
	 */
	public static Map<String, Object> searchDocHighlight(String type, String fieldName, String keyword, int from,
														 int size) {
		return ElasticSearchUtilsImp.searchDocHighlight(type, fieldName, keyword, from, size);
	}

	/**
	 * or条件查询高亮
	 *
	 * @param type 类型
	 * @param shouldMap or条件和值
	 * @return
	 */
	public static Map<String, Object> multiOrSearchDocHigh(String type, Map<String, String> shouldMap, int from,
														   int size) {
		return ElasticSearchUtilsImp.multiOrSearchDocHigh(type, shouldMap, from, size);
	}

	/**
	 * 搜索
	 *
	 * @param type 类型
	 * @param fieldName 待搜索的字段
	 * @param keyword 待搜索的关键词
	 */
	public static Map<String, Object> searchDoc(String type, String fieldName, String keyword) {
		return ElasticSearchUtilsImp.searchDoc(type, fieldName, keyword, 0, 10);
	}

	/**
	 * 返回对象列表
	 * @param type
	 * @param fieldName
	 * @param keyword
	 * @param objectClass
	 * @param from
	 * @param size
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@Deprecated //推荐使用searchDocs泛型方法
	public static List<Object> searchDoc(String type, String fieldName, String keyword, Class<?> objectClass, Integer from, Integer size) {
		if(from==null && size==null){
			from = 0; size = 10;
		}
		if(from!=null && size==null){
			size = 10;
		}
		if(from==null && size!=null){
			from = 0;
		}

		try {
			return ElasticSearchUtilsImp.searchDoc(type, fieldName, keyword, objectClass, from, size);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EsException(e.getMessage(), e.getCause());
		}
	}

	//by 泛型
	public static <T> List<T> searchDocs(String type, String fieldName, String keyword, Class<T> objectClass, Integer from, Integer size) {
		if(from==null){
			from = 0;
		}
		if(size==null) {
			size = 10;
		}
		try {
			return ElasticSearchUtilsImp.searchDocs(type, fieldName, keyword, objectClass, from, size);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EsException(e.getMessage(), e.getCause());
		}
	}

	//泛型
	public static <T> List<T> searchDocs(String type, String fieldName, String keyword, Class<T> objectClass) {
		return searchDocs(type, fieldName, keyword, objectClass, null, null);
	}

	@Deprecated
	public static List<Object> searchDocById(String type, String keyword, Class<?> objectClass) {
		try {
			return ElasticSearchUtilsImp.searchDoc(type, "_id", keyword, objectClass, 0, 1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EsException(e.getMessage(), e.getCause());
		}
	}
	@Deprecated
	public static Object searchById(String type, String id, Class<?> objectClass) {
		try {
			List<Object> info = ElasticSearchUtils.searchDoc(type, "_id", id, objectClass, 0 , 1);
			if(info==null || info.size() == 0){
				return null;
			}
			return info.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EsException(e.getMessage(), e.getCause());
		}
	}


	public static <T> T searchById(String index,String type, String id, Class<T> clazz){
		return ElasticSearchUtilsImp.searchById(index,type,id,clazz);
	}
	/**
	 * 多个条件进行or查询
	 *
	 * @param type 类型
	 * @param shouldMap 进行or查询的段和值
	 * @return
	 */
	public static Map<String, Object> multiOrSearchDoc(String type, Map<String, String> shouldMap) {
		return ElasticSearchUtilsImp.multiOrSearchDoc(type, shouldMap, 0, 10);
	}

	/**
	 * 多个条件进行and查询
	 *
	 * @param type 类型
	 * @param mustMap 进行and查询的段和值
	 * @return
	 */
	public static Map<String, Object> multiAndSearchDoc(String type, Map<String, Object> mustMap) {
		return ElasticSearchUtilsImp.multiAndSearchDoc(type, mustMap, 0, 10);
	}

	/**
	 * 多个条件进行and查询
	 *
	 * @param type 类型
	 * @param mustMap 进行and查询的段和值
	 * @return
	 */
	public static List<Object> multiAndSearchDoc(String type, Map<String, Object> mustMap, Class<?> objectClass, Integer from, Integer size) {
		if(from==null && size==null){
			from = 0; size = 10;
		}
		if(from!=null && size==null){
			size = 10;
		}
		if(from==null && size!=null){
			from = 0;
		}
		return ElasticSearchUtilsImp.multiAndSearchDoc(type, mustMap, objectClass, from, size);
	}



	
	/**
	 * 多个条件进行and查询并且排序
	 *
	 * @param type 类型
	 * @param mustMap 进行and查询的段和�??
	 * * @param orderMap 排序 <字段名称:asc/desc>
	 * @param from �?始行�?
	 * @param size 每页大小
	 * @return
	 */
	//by 泛型
	public static <T> List<T> multiAndSearchDocs(String type, Map<String, Object> mustMap, Map<String, String> orderMap, Class<T> objectClass, Integer from, Integer size){
		if(from==null){
			from = 0;
		}
		if(size==null){
			size = 10;
		}
		return ElasticSearchUtilsImp.multiAndSearchDocs(type, mustMap, orderMap, objectClass, from, size);
	}
	//by 泛型
	public static <T> List<T> multiAndSearchDocs(String type, Map<String, Object> mustMap, Map<String, String> orderMap, Class<T> objectClass){
		return multiAndSearchDocs(type, mustMap, orderMap, objectClass, null, null);
	}
	
	//by 泛型
	public static <T> List<T> multiAndSearchDocs(String idex, String type, Map<String, Object> mustMap, Map<String, String> orderMap, Class<T> objectClass, Integer from, Integer size){
		if(from==null){
			from = 0;
		}
		if(size==null){
			size = 10;
		}
		return ElasticSearchUtilsImp.multiAndSearchDocs(idex, type, mustMap, orderMap, objectClass, from, size);
	}

	/**
	 * 多个条件进行and查询,并且进行分页
	 * @param type
	 * @param mustMap
	 * @param objectClass
	 * @param pageNum
	 * @param pageSize
	 * @return
	 *  result.put("totalRecords", hits.getTotalHits());
	result.put("totalPage", totalPage);
	result.put("pageSize", pageSize);
	result.put("pageNum", pageNum);
	result.put("data", data); //data是List
	 */
	public static Map<String, Object> multiAndSearchDocWithPage(String type, Map<String, Object> mustMap, Class<?> objectClass, Integer pageNum, Integer pageSize){
		if(pageNum == null){
			pageNum = 1;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		return ElasticSearchUtilsImp.multiAndSearchDocWithPage(type, mustMap, objectClass, pageNum, pageSize);
	}

	public static <T> Map<String, Object>  multiAndSearchDocWithPage(String index,String type, Map<String, Object> mustMap, Class<T> objectClass, Integer pageNum, Integer pageSize){
		if(pageNum == null){
			pageNum = 1;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		return ElasticSearchUtilsImp.multiAndSearchDocWithPage(index,type, mustMap, objectClass, pageNum, pageSize);
	}
	
	/**
	 * 多个调教OR查询
	 * @param idex
	 * @param type
	 * @param orderMap
	 * @param objectClass
	 * @param from
	 * @param size
	 * @return
	 */
	public static <T> List<T> multiOrSearchDocs(String idex, String type, Map<String, Object> shouldMap, Map<String, String> orderMap, Class<T> objectClass, Integer from, Integer size){
		if(from==null){
			from = 0;
		}
		if(size==null){
			size = 10;
		}
		return ElasticSearchUtilsImp.multiOrSearchDoc(idex, type, shouldMap, orderMap, objectClass, from, size);
	}

	/**
	 * 多个条件进行or查询,并且进行分页
	 * @param type
	 * @param mustMap
	 * @param objectClass
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static Map<String, Object> multiOrSearchDocWithPage(String type, Map<String, Object> mustMap, Class<?> objectClass, int pageNum, int pageSize){
		return ElasticSearchUtilsImp.multiOrSearchDocWithPage(type, mustMap, objectClass, pageNum, pageSize);
	}

	/**
	 * 多个条件进行and查询,并且进行分页
	 * @param type
	 * @param mustMap
	 * @param orderMap
	 * @param objectClass
	 * @param pageNum
	 * @param pageSize
	 * @return
	 *  result.put("totalRecords", hits.getTotalHits());
	result.put("totalPage", totalPage);
	result.put("pageSize", pageSize);
	result.put("pageNum", pageNum);
	result.put("data", data); //data是List
	 */
	public static Map<String, Object> multiAndSearchDocWithPage(String type, Map<String, Object> mustMap, Map<String, String> orderMap, Class<?> objectClass, int pageNum, int pageSize){
		return ElasticSearchUtilsImp.multiAndSearchDocWithPage(type, mustMap, orderMap, objectClass, pageNum, pageSize);
	}

	/**
	 * 多个条件进行or查询,并且进行分页排序
	 * @param type
	 * @param mustMap
	 * @param orderMap
	 * @param objectClass
	 * @param pageNum
	 * @param pageSize
	 * @return
	 *  result.put("totalRecords", hits.getTotalHits());
	result.put("totalPage", totalPage);
	result.put("pageSize", pageSize);
	result.put("pageNum", pageNum);
	result.put("data", data); //data是List
	 */
	public static Map<String, Object> multiOrSearchDocWithPage(String type, Map<String, Object> mustMap, Map<String, String> orderMap, Class<?> objectClass, int pageNum, int pageSize){
		return ElasticSearchUtilsImp.multiOrSearchDocWithPage(type, mustMap, orderMap, objectClass, pageNum, pageSize);
	}


	/**
	 * 功能描述：统计查询
	 * @param index 索引名
	 * @param type 类型
	 * @param constructor 查询构造
	 * @param groupBy 统计字段
	 */
	public static Terms statSearch(String index, String type, ESQueryBuilderConstructor constructor, String groupBy) {
		return ElasticSearchUtilsImp.statSearch(index,type,constructor,groupBy);
	}

	/**
	 * 功能描述：统计查询
	 * @param index 索引名
	 * @param type 类型
	 * @param constructor 查询构造
	 * @param agg 自定义计算
	 */
	public static Terms statSearch(String index, String type, ESQueryBuilderConstructor constructor, AggregationBuilder agg) {
		return ElasticSearchUtilsImp.statSearch(index,type,constructor,agg);
	}

	public void reIndex(String oldIndex,String oldType,String newIndex,String newType,ESQueryBuilderConstructor constructor){
		ElasticSearchUtilsImp.reIndex(oldIndex,oldType,newIndex,newType,constructor);
	}

	public static Map<String,Object> searchDoc(String index, String type, ESQueryBuilderConstructor constructor, Class<?> clazz){
		SearchHits searchHits = ElasticSearchUtilsImp.getSearchHits(index,type,constructor);
		return ElasticSearchUtilsImp.searchHitsToMap(searchHits,clazz,constructor);
	}

	public static Map<String,Object> searchDocForWDZJ(String index, String type, ESQueryBuilderConstructor constructor, Class<?> clazz){
		SearchHits searchHits = ElasticSearchUtilsImp.getSearchHits(index,type,constructor);
		SearchHit[] hits = searchHits.getHits();
		List<Object> list = new ArrayList<Object>();
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < hits.length; i++) {
			String json = hits[i].getSourceAsString();
			String id = hits[i].getId();
			try {
				ObjectNode jsonNode = mapper.readValue(json, ObjectNode.class);
				jsonNode.put("id", id);
				Object bean = JSONObject.parseObject(jsonNode.toString(),clazz);
				list.add(bean);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> result = new HashMap<>();
		//总记录数
		Long total = searchHits.getTotalHits();
		int totalInt = total.intValue();
		int offset = constructor.getFrom();
		//总页数
		int totalPage = totalInt % constructor.getSize() == 0 ? totalInt / constructor.getSize() : totalInt/ constructor.getSize() + 1;
		int pageNum = offset/ constructor.getSize() + 1;
		//偏移量
		result.put("totalRecords", searchHits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", constructor.getSize());
		result.put("pageNum", pageNum);
		result.put("data", list);
		return result;
	}

	public static <T> List<T> searchDocs(String index, String type, ESQueryBuilderConstructor constructor, Class<T> clazz){
		SearchHits searchHits = ElasticSearchUtilsImp.getSearchHits(index,type,constructor);
		return ElasticSearchUtilsImp.searchHitsToObj(searchHits,clazz);
	}

	public static SearchHits searchHits(String index, String type, ESQueryBuilderConstructor constructor){
		return ElasticSearchUtilsImp.getSearchHits(index,type,constructor);
	}
	
	

}
