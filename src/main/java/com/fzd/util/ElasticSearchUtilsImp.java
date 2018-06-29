package com.fzd.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fzd.ali.po.BasePo;
import com.fzd.exception.PiperException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import us.codecraft.webmagic.selector.Json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName:  ElasticSearchUtilsImp
 * @Description: ElasticSearch助手类具体实类
 * @author: chenyang
 * @date:   2017年7月18日 下午5:09:49
 *
 */
public class ElasticSearchUtilsImp {
	private final static int MAX = 10000;
	private static String cluster_name = null;// 实例名称
	private static String[] cluster_serverip = null;// elasticSearch服务器ip
	//	private static String type = null;// 索引名称
	// 创建私有对象
	private static TransportClient client;

	static {
		try {

			// 读取信息
			cluster_name = "my-es";
			cluster_serverip = new String[]{"192.168.0.101", "192.168.0.102", "192.168.0.103"};
//			type = props.getProperty("type");

			Settings settings = Settings.builder().put("cluster.name", cluster_name).build();// 设置集群名称
			client = new PreBuiltTransportClient(settings);// 创建client
			try {
				for(String ip : cluster_serverip){
					client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));// 增加地址和端�?
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
				//System.out.println("ElasticSearch连接失败！");
			}

		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("加载数据库配置文件出错！");
		}
	}

	/**
	 * 返回1个到ElasticSearch的连接客户端
	 *
	 * @return
	 */
	public static TransportClient getClient() {
		return client;
	}

	/**
	 * 将Map转换成builder
	 *
	 * @param mapParam
	 * @return
	 * @throws Exception
	 */
	private static XContentBuilder createMapJson(Map<String, ?> mapParam) throws Exception {
		XContentBuilder source = XContentFactory.jsonBuilder().startObject();

		for (Map.Entry<String, ?> entry : mapParam.entrySet()) {
			if(entry.getValue().getClass().isAssignableFrom(Date.class)){
				Date d = (Date) entry.getValue();
				source.field(entry.getKey(), d.getTime());
			}else{
				source.field(entry.getKey(), entry.getValue());
			}
		}

		source.endObject();

		return source;
	}

	/**
	 * 将一个Map格式的数据（key,value）插入索引 （私有方法）
	 *
	 * @param type 类型（对应数据库表）
	 * @param docId id，对应elasticSearch中的_id字段
	 * @param mapParam Map格式的数据
	 * @return
	 */
	public static boolean addMapDocToIndex(String type, String docId, Map<String, Object> mapParam) {
		boolean result = false;

		TransportClient client = getClient();

		// 存json入索引中
		IndexResponse response = null;
		if (docId == null) {
			// 使用默认的id
			response = client.prepareIndex(type, type).setSource(mapParam).get();
		} else {
			response = client.prepareIndex(type, type, docId).setSource(mapParam).get();
		}

		// 插入结果获取
		String index = response.getIndex();
		String gettype = response.getType();
		String id = response.getId();
		long version = response.getVersion();
		RestStatus status = response.status();

		String strResult = "新增文档成功！" + index + " : " + gettype + ": " + id + ": " + version + ": " + status.getStatus();
		//System.out.println(strResult);

		if (status.getStatus() == 201) {
			result = true;
		}

		// 关闭client


		return result;
	}

	/**
	 * 将一个实体存入到默认索引的类型中（指定_id，一般是业务数据的id，及elasticSearch和关系型数据使用同一个id，方便同关系型数据库互动
	 * （私有方法）
	 *
	 * @param type 类型（对应数据库表）
	 * @param docId id，对应elasticSearch中的_id字段
	 * @param entity 要插入的实体
	 * @return
	 */
	public static boolean addEntityDoc(String index, String type, String docId, Object entity) {
		boolean result = false;

		TransportClient client = getClient();

		// 存json入索引中
		IndexResponse response = null;
		if (docId == null) {
			// 使用默认的id
			response = client.prepareIndex(index, type).setSource(JsonBeanUtil.transBean2Json(entity), XContentType.JSON).get();
		} else {
			response = client.prepareIndex(index, type, docId).setSource(JsonBeanUtil.transBean2Json(entity), XContentType.JSON).get();
		}
		// 插入结果获取
		index = response.getIndex();
		String gettype = response.getType();
		String id = response.getId();
		long version = response.getVersion();
		RestStatus status = response.status();

		String strResult = "新增文档成功 " + index + ": " + gettype + ": " + id + ": " + version + ": " + status.getStatus();
		//System.out.println(strResult);

		if (status.getStatus() == 201) {
			result = true;
		}
		// 关闭client
		return result;
	}
	
	/**
	 * 将一个实体存入到默认索引的类型中（指定_id，一般是业务数据的id，及elasticSearch和关系型数据使用同一个id，方便同关系型数据库互动
	 * （私有方法）
	 *
	 * @param type 类型（对应数据库表）
	 * @param docId id，对应elasticSearch中的_id字段
	 * @param entity 要插入的实体
	 * @return
	 */
	public static String addEntityDocReturnId(String index, String type, String docId, Object entity) {
		boolean result = false;

		TransportClient client = getClient();

		// 存json入索引中
		IndexResponse response = null;
		if (docId == null) {
			// 使用默认的id
			response = client.prepareIndex(index, type).setSource(JsonBeanUtil.transBean2Json(entity), XContentType.JSON).get();
		} else {
			response = client.prepareIndex(index, type, docId).setSource(JsonBeanUtil.transBean2Json(entity), XContentType.JSON).get();
		}
		// 插入结果获取
		index = response.getIndex();
		String gettype = response.getType();
		String id = response.getId();
		long version = response.getVersion();
		RestStatus status = response.status();

		String strResult = "新增文档成功 " + index + ": " + gettype + ": " + id + ": " + version + ": " + status.getStatus();
		//System.out.println(strResult);

		if (status.getStatus() == 201) {
			result = true;
		}
		// 关闭client
		if(result){
			return id;
		}else{
			return "";
		}
	}



	public static String addJsonDoc(String index, String type, String docId, String json) {
		boolean result = false;

		TransportClient client = getClient();

		// 存json入索引中
		IndexResponse response = null;
		if (docId == null) {
			// 使用默认的id
			response = client.prepareIndex(index, type).setSource(json, XContentType.JSON).get();
		} else {
			response = client.prepareIndex(index, type, docId).setSource(json, XContentType.JSON).get();
		}

		// 插入结果获取
		index = response.getIndex();
		String gettype = response.getType();
		String id = response.getId();
		long version = response.getVersion();
		RestStatus status = response.status();

		String strResult = "新增文档成功 " + index + ": " + gettype + ": " + id + ": " + version + ": " + status.getStatus();
		//System.out.println(strResult);

		if (status.getStatus() == 201) {
			result = true;
		}

		// 关闭client


		return id;
	}

	/**
	 * 删除文档
	 *
	 * @param type 类型（对应数据库表）
	 * @param docId 类型中id
	 * @return
	 */
	public static boolean deleteById(String index,String type, String docId) {
		boolean result = false;

		TransportClient client = getClient();
		DeleteResponse deleteresponse = client.prepareDelete(index, type, docId).get();

		//System.out.println("删除结果 " + deleteresponse.getResult().toString());
		if (deleteresponse.getResult().toString() == "DELETED") {
			result = true;
		}

		// 关闭client


		return result;
	}
	
	public static boolean deleteByAndQuery(String index,String type, Map<String,Object> mustMap) throws InterruptedException, ExecutionException {
		boolean result = false;

		TransportClient client = getClient();
		QueryBuilder queryBuilder = buildResponse(mustMap); 
		
		BulkRequestBuilder bulkRequest = client.prepareBulk();    
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);  
		//分页  
		searchRequestBuilder.setFrom(0).setSize(1000);  
		  
		searchRequestBuilder.setQuery(queryBuilder);  
		SearchResponse response = searchRequestBuilder.execute().get();  
		for(SearchHit hit : response.getHits()){    
		    String id = hit.getId();    
		    bulkRequest.add(client.prepareDelete(index, type, id).request());  
		}    
		BulkResponse bulkResponse = bulkRequest.get();    
		if (bulkResponse.hasFailures()) {    
		    for(BulkItemResponse item : bulkResponse.getItems()){    
//		        System.out.println(item.getFailureMessage());    
		    }    
		} else {    
//		    System.out.println("delete ok");    
		}    
		
		// 关闭client


		return result;
	}

	public static String bulkDetlete(String index, QueryBuilder queryBuilder){
		BulkByScrollResponse response =
				DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
						.filter(queryBuilder)
						.source(index)
						.get();

		long deleted = response.getDeleted();
		return String.valueOf(deleted);
	}

	/**
	 * 修改文档
	 *
	 * @param type 类型
	 * @param docId 文档id
	 * @param updateParam 需要修改的字段和值?
	 * @return
	 */
	public static boolean updateDoc(String type, String docId, Map<String, ?> updateParam) {
		String strResult = "";
		boolean result = false;

		TransportClient client = getClient();

		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(type);
		updateRequest.type(type);
		updateRequest.id(docId);
		try {
			updateRequest.doc(createMapJson(updateParam));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			strResult = client.update(updateRequest).get().getResult().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//System.out.println(strResult);

		if (strResult == "UPDATED") {
			result = true;
		}

		return result;
	}
	
	/**
	 * 修改文档
	 *
	 * @param type 类型
	 * @param docId 文档id
	 * @param updateParam 需要修改的字段和值?
	 * @return
	 */
	public static boolean updateDoc(String index, String type, String docId, Map<String, ?> updateParam) {
		String strResult = "";
		boolean result = false;

		TransportClient client = getClient();

		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(docId);
		try {
			updateRequest.doc(createMapJson(updateParam));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			strResult = client.update(updateRequest).get().getResult().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//System.out.println(strResult);

		if (strResult == "UPDATED") {
			result = true;
		}

		return result;
	}
	
	/**
	 * 修改文档
	 *
	 * @param type 类型
	 * @param docId 文档id
	 * @return
	 */
	public static boolean updateDocByJson(String index, String type, String docId, String json) {
		String strResult = "";
		boolean result = false;

		TransportClient client = getClient();

		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(docId);
		try {
			updateRequest.doc(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			strResult = client.update(updateRequest).get().getResult().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//System.out.println(strResult);

		if (strResult == "UPDATED") {
			result = true;
		}

		return result;
	}

	/**
	 * 高亮搜索
	 *
	 * @param type 类型
	 * @param fieldName 字段
	 * @param keyword 关键词
	 * @param from 起始行
	 * @param size 每页大小
	 * @return
	 */
	public static Map<String, Object> searchDocHighlight(String type, String fieldName, String keyword, int from,
														 int size) {
		TransportClient client = getClient();

		// 高亮
		HighlightBuilder hiBuilder = new HighlightBuilder();
		hiBuilder.preTags("<span style=\"color:red\">");
		hiBuilder.postTags("</span>");
		hiBuilder.field(fieldName);

		QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, keyword);

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setQuery(queryBuilder);
		responsebuilder.highlighter(hiBuilder);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits searchHits = myresponse.getHits();

		// 总命中数
		long total = searchHits.getTotalHits();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < searchHits.getHits().length; i++) {
			Map<String, HighlightField> highlightFields = searchHits.getHits()[i].getHighlightFields();

			// 段高亮
			HighlightField titleField = highlightFields.get(fieldName);
			Map<String, Object> source = searchHits.getHits()[i].getSource();
			if (titleField != null) {
				Text[] fragments = titleField.fragments();
				String name = "";
				for (Text text : fragments) {
					name += text;
				}
				source.put(fieldName, name);
			}

			list.add(source);
		}
		map.put("rows", list);


		return map;
	}

	/**
	 * 批量新增记录
	 * @param objList
	 * @return true:全部成功 false:部分成功
	 */
	public static <T extends BasePo> boolean batchAdd(String index, String type, final List<T> objList){
		TransportClient client = getClient();
		if(null != objList && objList.size() > 0){
			BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
			for(T obj: objList){
				IndexRequest request;
				JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);
				jsonObject.remove("id");
				if(obj.getId() != null) {
					request = new IndexRequest(index, type, obj.getId().toString()).source(jsonObject.toJSONString(), XContentType.JSON);
				}else{
					request = new IndexRequest(index, type).source(jsonObject.toJSONString(), XContentType.JSON);
				}
				bulkRequestBuilder.add(request);
			}
			BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();

			boolean allSuccess = !bulkResponse.hasFailures();
			if(!allSuccess){
				String errorMessage = "batchInsert fail:"+bulkResponse.buildFailureMessage();
				//System.out.println(errorMessage);
			}

			return allSuccess;

		}else{

			return false;
		}
	}

	/**
	 * or条件查询高亮
	 *
	 * @param type 类型
	 * @param shouldMap or条件和�??
	 * @param from �?始行�?
	 * @param size 每页大小
	 * @return
	 */
	public static Map<String, Object> multiOrSearchDocHigh(String type, Map<String, String> shouldMap, int from,
														   int size) {
		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		// 高亮
		HighlightBuilder hiBuilder = new HighlightBuilder();
		hiBuilder.preTags("<span style=\"color:red\">");
		hiBuilder.postTags("</span>");

		// 高亮每个字段
		for (String key : shouldMap.keySet()) {
			hiBuilder.field(key);
		}

		responsebuilder.highlighter(hiBuilder);

		if (null != shouldMap && shouldMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : shouldMap.keySet()) {
				queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or连接条件
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits searchHits = myresponse.getHits();

		// 总命中数
		long total = searchHits.getTotalHits();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < searchHits.getHits().length; i++) {
			Map<String, HighlightField> highlightFields = searchHits.getHits()[i].getHighlightFields();
			Map<String, Object> source = searchHits.getHits()[i].getSource();

			for (String key : shouldMap.keySet()) {
				// 各个段进行高�?
				HighlightField titleField = highlightFields.get(key);
				if (titleField != null) {
					Text[] fragments = titleField.fragments();
					String name = "";
					for (Text text : fragments) {
						name += text;
					}
					source.put(key, name);
				}
			}

			list.add(source);
		}

		map.put("rows", list);

		return map;
	}

	/**
	 * 搜索
	 *
	 * @param type 类型
	 * @param fieldName 待搜索的字段
	 * @param keyword 待搜索的关键�?
	 * @param from �? 始 行�?
	 * @param size 每页大小
	 * @return
	 */
	public static Map<String, Object> searchDoc(String type, String fieldName, String keyword, int from, int size) {
		List<String> hitResult = new ArrayList<String>();

		TransportClient client = getClient();

		QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, keyword);

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setQuery(queryBuilder);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			hitResult.add(hits.getHits()[i].getSourceAsString());
		}

		// 将命中结果转换成Map输出
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("total", hitResult.size());
		modelMap.put("rows", hitResult);

		return modelMap;
	}

	public static List<Object> searchDoc(String type, String fieldName, String keyword, Class<?> objectClass, int from, int size) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
		List<String> hitResult = new ArrayList<String>();
		List<Object> list = new ArrayList<>();
		TransportClient client = getClient();

		QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, keyword);

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setQuery(queryBuilder);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
//			JSONObject jo = JSONObject.fromObject(json);
//			jo.put("id", id);
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			Object bean = (Object) JsonBeanUtil.transJson2Bean(jsonObj.toJSONString(), objectClass);

			list.add(bean);
		}

		// 将命中结果转换成Map输出
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("total", hitResult.size());
		modelMap.put("rows", hitResult);

		return list;
	}

	//by 泛型
	public static <T> List<T> searchDocs(String type, String fieldName, String keyword, Class<T> objectClass, int from, int size) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
		List<String> hitResult = new ArrayList<String>();
		List<T> list = new ArrayList<>();
		TransportClient client = getClient();

		QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, keyword);

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setQuery(queryBuilder);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);
			T bean = JsonBeanUtil.transJson2Obj(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}
		return list;
	}
	/**
	 * 多个条件进行or查询
	 *
	 * @param type 类型
	 * @param shouldMap 进行or查询的段和�??
	 * @param from �?始行�?
	 * @param size 每页大小
	 * @return
	 */
	public static Map<String, Object> multiOrSearchDoc(String type, Map<String, String> shouldMap, int from, int size) {
		List<String> hitResult = new ArrayList<String>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		if (null != shouldMap && shouldMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : shouldMap.keySet()) {
				queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or连接条件
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			hitResult.add(hits.getHits()[i].getSourceAsString());
		}

		// 将命中结果转换成Map输出
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("total", hitResult.size());
		modelMap.put("rows", hitResult);

		return modelMap;
	}

	/**
	 * 多个条件进行and查询
	 *
	 * @param type 类型
	 * @param mustMap 进行and查询的段和�??
	 * @param from �?始行�?
	 * @param size 每页大小
	 * @return
	 */
	public static Map<String, Object> multiAndSearchDoc(String type, Map<String, Object> mustMap, int from, int size) {
		List<String> hitResult = new ArrayList<String>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			hitResult.add(hits.getHits()[i].getSourceAsString());
		}

		// 将命中结果转换成Map输出
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("total", hitResult.size());
		modelMap.put("rows", hitResult);

		return modelMap;
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
	public static List<Object> multiAndSearchDoc(String type, Map<String, Object> mustMap, Map<String, String> orderMap, Class<?> objectClass, int from, int size) {
		List<Object> list = new ArrayList<Object>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap,orderMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
//			JSONObject jo = JSONObject.fromObject(json);
//			jo.put("id", id);
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			Object bean = (Object) JsonBeanUtil.transJson2Bean(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}


		return list;
	}

	//by 泛型
	public static <T> List<T> multiAndSearchDocs(String index, String type, Map<String, Object> mustMap, Map<String, String> orderMap, Class<T> objectClass, int from, int size) {
		List<T> list = new ArrayList<T>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap,orderMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			T bean = JsonBeanUtil.transJson2Obj(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}


		return list;
	}
	/**
	 * index = type
	 * @param type
	 * @param mustMap
	 * @param orderMap
	 * @param objectClass
	 * @param from
	 * @param size
	 * @return
	 */
	public static <T> List<T> multiAndSearchDocs(String type, Map<String, Object> mustMap, Map<String, String> orderMap, Class<T> objectClass, int from, int size) {
		List<T> list = new ArrayList<T>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap,orderMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			T bean = JsonBeanUtil.transJson2Obj(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}


		return list;
	}

	/**
	 * 多个条件进行and查询
	 *
	 * @param type 类型
	 * @param mustMap 进行and查询的段和�??
	 * @param from �?始行�?
	 * @param size 每页大小
	 * @return
	 */
	public static List<Object> multiAndSearchDoc(String type, Map<String, Object> mustMap, Class<?> objectClass, int from, int size) {
		List<Object> list = new ArrayList<Object>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
//			JSONObject jo = JSONObject.fromObject(json);
//			jo.put("id", id);
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			Object bean = (Object) JsonBeanUtil.transJson2Bean(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}


		return list;
	}

	public static <T> List<T> multiAndSearchDoc(String index,String type, Map<String, Object> mustMap, Class<T> objectClass, int from, int size) {
		List<T> list = new ArrayList<>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			T bean = JsonBeanUtil.transJson2Obj(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}


		return list;
	}

	/**
	 * 多个条件进行or查询
	 *
	 * @param type 类型
	 * @param shouldMap 进行or查询的段和�??
	 * @param from �?始行�?
	 * @param size 每页大小
	 * @return
	 */
	public static List<Object> multiOrSearchDoc(String type, Map<String, Object> shouldMap, Class<?> objectClass, int from, int size) {
		List<Object> list = new ArrayList<Object>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		if (null != shouldMap && shouldMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : shouldMap.keySet()) {
				queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or查询
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
//			JSONObject jo = JSONObject.fromObject(json);
//			jo.put("id", id);
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			Object bean = (Object) JsonBeanUtil.transJson2Bean(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}


		return list;
	}

	/**
	 * 多个条件进行or查询，支持排序
	 * @param type
	 * @param shouldMap
	 * @param orderMap
	 * @param objectClass
	 * @param from
	 * @param size
	 * @return
	 */
	public static List<Object> multiOrSearchDoc(String type, Map<String, Object> shouldMap, Map<String, String> orderMap, Class<?> objectClass, int from, int size) {
		List<Object> list = new ArrayList<Object>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		if (null != shouldMap && shouldMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : shouldMap.keySet()) {
				queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or查询
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}

		if(orderMap!=null && orderMap.size()>0){
			for (String key : orderMap.keySet()) {
				String value = orderMap.get(key)==null?"":orderMap.get(key).toString();
				if("desc".equals(value)){
					responsebuilder.addSort(key,SortOrder.DESC);
				}else{
					responsebuilder.addSort(key,SortOrder.ASC);
				}
			}
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
//			JSONObject jo = JSONObject.fromObject(json);
//			jo.put("id", id);
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			Object bean = (Object) JsonBeanUtil.transJson2Bean(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}


		return list;
	}
	
	/**
	 * 多个条件进行or查询，支持排序
	 * @param type
	 * @param shouldMap
	 * @param orderMap
	 * @param objectClass
	 * @param from
	 * @param size
	 * @return
	 */
	public static <T> List<T> multiOrSearchDoc(String index, String type, Map<String, Object> shouldMap, Map<String, String> orderMap, Class<T> objectClass, int from, int size) {
		List<T> list = new ArrayList<T>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
		responsebuilder.setFrom(from);
		responsebuilder.setSize(size);
		responsebuilder.setExplain(true);

		if (null != shouldMap && shouldMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : shouldMap.keySet()) {
				queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or查询
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}
		
		if(orderMap!=null && orderMap.size()>0){
			for (String key : orderMap.keySet()) {
				String value = orderMap.get(key)==null?"":orderMap.get(key).toString();
				if("desc".equals(value)){
					responsebuilder.addSort(key,SortOrder.DESC);
				}else{
					responsebuilder.addSort(key,SortOrder.ASC);
				}
			}
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		for (int i = 0; i < hits.getHits().length; i++) {
			String json = hits.getHits()[i].getSourceAsString();
			String id = hits.getHits()[i].getId();
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);

			T bean = JsonBeanUtil.transJson2Obj(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}

		return list;
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
	public static Map<String, Object> multiAndSearchDocWithPage(String type, Map<String, Object> mustMap, Class<?> objectClass, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();

		//总记录数
		Long total = hits.getTotalHits();
		int totalInt = total.intValue();
		//总页数
		int totalPage = totalInt % pageSize == 0 ? totalInt / pageSize : totalInt/ pageSize + 1;
		//偏移量
		int from = (pageNum-1)*pageSize;

		List<Object> data = multiAndSearchDoc(type, mustMap, objectClass, from, pageSize);

		result.put("totalRecords", hits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", pageSize);
		result.put("pageNum", pageNum);
		result.put("data", data);

		return result;
	}

	public static <T> Map<String, Object> multiAndSearchDocWithPage(String index,String type, Map<String, Object> mustMap, Class<T> objectClass, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();

		//总记录数
		Long total = hits.getTotalHits();
		int totalInt = total.intValue();
		//总页数
		int totalPage = totalInt % pageSize == 0 ? totalInt / pageSize : totalInt/ pageSize + 1;
		//偏移量
		int from = (pageNum-1)*pageSize;

		List<T> data = multiAndSearchDoc(index,type, mustMap, objectClass, from, pageSize);

		result.put("totalRecords", hits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", pageSize);
		result.put("pageNum", pageNum);
		result.put("data", data);

		return result;
	}

	/**
	 * 多个条件进行or查询,并且进行分页
	 * @param type
	 * @param
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
	public static Map<String, Object> multiOrSearchDocWithPage(String type, Map<String, Object> shouldMap, Class<?> objectClass, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);

		if (null != shouldMap && shouldMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : shouldMap.keySet()) {
				queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or连接条件
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();

		//总记录数
		Long total = hits.getTotalHits();
		int totalInt = total.intValue();
		//总页数
		int totalPage = totalInt % pageSize == 0 ? totalInt / pageSize : totalInt/ pageSize + 1;
		//偏移量
		int from = (pageNum-1)*pageSize;

		List<Object> data = multiOrSearchDoc(type, shouldMap, objectClass, from, pageSize);

		result.put("totalRecords", hits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", pageSize);
		result.put("pageNum", pageNum);
		result.put("data", data);

		return result;
	}
	
	public static Map<String, Object> multiOrSearchDocWithPage(String index, String type, Map<String, Object> shouldMap, Class<?> objectClass, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);

		if (null != shouldMap && shouldMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : shouldMap.keySet()) {
				queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or连接条件
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();

		//总记录数
		Long total = hits.getTotalHits();
		int totalInt = total.intValue();
		//总页数
		int totalPage = totalInt % pageSize == 0 ? totalInt / pageSize : totalInt/ pageSize + 1;
		//偏移量
		int from = (pageNum-1)*pageSize;

		List<?> data = multiOrSearchDoc(index, type, shouldMap, null, objectClass, from, pageSize);

		result.put("totalRecords", hits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", pageSize);
		result.put("pageNum", pageNum);
		result.put("data", data);

		return result;
	}

	private static SearchRequestBuilder buildResponse(SearchRequestBuilder responsebuilder, Map<String, Object> mustMap) {
		if (null != mustMap && mustMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : mustMap.keySet()) {
				
				if(key.equals("rangeQueryBuilder")){
					//range查询
					Map<String, Map<String, Object>> rangeMap = (Map<String, Map<String, Object>>) mustMap.get(key);
					for(String field : rangeMap.keySet()){
						Map<String, Object> range = rangeMap.get(field);
						RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field);
						for(String k : range.keySet()){
							switch (k) {
							case "gte":
								rangeQueryBuilder.gte(range.get(k));
								break;
							case "gt":
								rangeQueryBuilder.gt(range.get(k));
								break;
							case "lte":
								rangeQueryBuilder.lte(range.get(k));
								break;
							case "lt":
								rangeQueryBuilder.lt(range.get(k));
								break;
							default:
								break;
							}
						}
						queryBuilder.must(rangeQueryBuilder);
					}
				}else{
					//普通and查询
					queryBuilder.must(QueryBuilders.matchPhraseQuery(key, mustMap.get(key)));
				}
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}
		return responsebuilder;
	}
	
	private static QueryBuilder buildResponse(Map<String, Object> mustMap) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if (null != mustMap && mustMap.size() > 0) {
			// 这里查询的条件用map传�??
			for (String key : mustMap.keySet()) {
				
				if(key.equals("rangeQueryBuilder")){
					//range查询
					Map<String, Map<String, Object>> rangeMap = (Map<String, Map<String, Object>>) mustMap.get(key);
					for(String field : rangeMap.keySet()){
						Map<String, Object> range = rangeMap.get(field);
						RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field);
						for(String k : range.keySet()){
							switch (k) {
							case "gte":
								rangeQueryBuilder.gte(range.get(k));
								break;
							case "gt":
								rangeQueryBuilder.gt(range.get(k));
								break;
							case "lte":
								rangeQueryBuilder.lte(range.get(k));
								break;
							case "lt":
								rangeQueryBuilder.lt(range.get(k));
								break;
							default:
								break;
							}
						}
						queryBuilder.must(rangeQueryBuilder);
					}
				}else{
					//普通and查询
					queryBuilder.must(QueryBuilders.matchPhraseQuery(key, mustMap.get(key)));
				}
			}
			// 查询
		}
		return queryBuilder;
	}

	private static SearchRequestBuilder buildResponse(SearchRequestBuilder responsebuilder, Map<String, ?> mustMap, Map<String, String> orderMap) {
		if (null != mustMap && mustMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : mustMap.keySet()) {
				if(key.equals("rangeQueryBuilder")){
					//range查询
					Map<String, Map<String, Object>> rangeMap = (Map<String, Map<String, Object>>) mustMap.get(key);
					for(String field : rangeMap.keySet()){
						Map<String, Object> range = rangeMap.get(field);
						RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field);
						for(String k : range.keySet()){
							switch (k) {
							case "gte":
								rangeQueryBuilder.gte(range.get(k));
								break;
							case "gt":
								rangeQueryBuilder.gt(range.get(k));
								break;
							case "lte":
								rangeQueryBuilder.lte(range.get(k));
								break;
							case "lt":
								rangeQueryBuilder.lt(range.get(k));
								break;
							default:
								break;
							}
						}
						queryBuilder.must(rangeQueryBuilder);
					}
				}else{
					//普通and查询
					queryBuilder.must(QueryBuilders.matchPhraseQuery(key, mustMap.get(key)));
				}
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}

		if(orderMap!=null && orderMap.size()>0){
			for (String key : orderMap.keySet()) {
				String value = orderMap.get(key)==null?"":orderMap.get(key).toString();
				if("desc".equals(value)){
					responsebuilder.addSort(key,SortOrder.DESC);
				}else{
					responsebuilder.addSort(key,SortOrder.ASC);
				}
			}
		}

		return responsebuilder;
	}

	public static Map<String, Object> multiAndSearchDocWithPage(String type, Map<String, Object> mustMap,
																Map<String, String> orderMap, Class<?> objectClass, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap, orderMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();

		//总记录数
		Long total = hits.getTotalHits();
		int totalInt = total.intValue();
		//总页数
		int totalPage = totalInt % pageSize == 0 ? totalInt / pageSize : totalInt/ pageSize + 1;
		//偏移量
		int from = (pageNum-1)*pageSize;

		List<Object> data = multiAndSearchDoc(type, mustMap, orderMap, objectClass, from, pageSize);

		result.put("totalRecords", hits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", pageSize);
		result.put("pageNum", pageNum);
		result.put("data", data);

		return result;
	}

	public static Map<String, Object> multiAndSearchDocWithPage(String index, String type, Map<String, Object> mustMap,
			Map<String, String> orderMap, Class<?> objectClass, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<>();
		
		TransportClient client = getClient();
		
		SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);
		
		responsebuilder = buildResponse(responsebuilder,mustMap, orderMap);
		
		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		
		//总记录数
		Long total = hits.getTotalHits();
		int totalInt = total.intValue();
		//总页数
		int totalPage = totalInt % pageSize == 0 ? totalInt / pageSize : totalInt/ pageSize + 1;
		//偏移量
		int from = (pageNum-1)*pageSize;
		List<?> data = multiAndSearchDocs(index, type, mustMap, orderMap, objectClass, from, pageSize);
		
		result.put("totalRecords", hits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", pageSize);
		result.put("pageNum", pageNum);
		result.put("data", data);
		
		return result;
	}
	
	public static Map<String, Object> multiOrSearchDocWithPage(String type, Map<String, Object> shouldMap,
															   Map<String, String> orderMap, Class<?> objectClass, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<>();

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(type).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);

		if (null != shouldMap && shouldMap.size() > 0) {
			// 创建�?个查�?
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

			// 这里查询的条件用map传�??
			for (String key : shouldMap.keySet()) {
				queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or查询
			}
			// 查询
			responsebuilder.setQuery(queryBuilder);
		}

		if(orderMap!=null && orderMap.size()>0){
			for (String key : orderMap.keySet()) {
				String value = orderMap.get(key)==null?"":orderMap.get(key).toString();
				if("desc".equals(value)){
					responsebuilder.addSort(key,SortOrder.DESC);
				}else{
					responsebuilder.addSort(key,SortOrder.ASC);
				}
			}
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();

		//总记录数
		Long total = hits.getTotalHits();
		int totalInt = total.intValue();
		//总页数
		int totalPage = totalInt % pageSize == 0 ? totalInt / pageSize : totalInt/ pageSize + 1;
		//偏移量
		int from = (pageNum-1)*pageSize;

//		List<Object> data = multiOrSearchDoc(type, shouldMap, objectClass, from, pageSize);
		List<Object> data = multiOrSearchDoc(type, shouldMap, orderMap, objectClass, from, pageSize);

		result.put("totalRecords", hits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", pageSize);
		result.put("pageNum", pageNum);
		result.put("data", data);

		return result;
	}
	
	public static Map<String, Object> multiOrSearchDocWithPage(String index, String type, Map<String, Object> shouldMap,
			   Map<String, String> orderMap, Class<?> objectClass, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<>();
		
		TransportClient client = getClient();
		
		SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);
		
		if (null != shouldMap && shouldMap.size() > 0) {
		// 创建�?个查�?
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		
		// 这里查询的条件用map传�??
		for (String key : shouldMap.keySet()) {
		queryBuilder.should(QueryBuilders.matchPhraseQuery(key, shouldMap.get(key)));// or查询
		}
		// 查询
		responsebuilder.setQuery(queryBuilder);
		}
		
		if(orderMap!=null && orderMap.size()>0){
		for (String key : orderMap.keySet()) {
		String value = orderMap.get(key)==null?"":orderMap.get(key).toString();
		if("desc".equals(value)){
		responsebuilder.addSort(key,SortOrder.DESC);
		}else{
		responsebuilder.addSort(key,SortOrder.ASC);
		}
		}
		}
		
		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();
		
		//总记录数
		Long total = hits.getTotalHits();
		int totalInt = total.intValue();
		//总页数
		int totalPage = totalInt % pageSize == 0 ? totalInt / pageSize : totalInt/ pageSize + 1;
		//偏移量
		int from = (pageNum-1)*pageSize;
		
		//List<Object> data = multiOrSearchDoc(type, shouldMap, objectClass, from, pageSize);
		List<?> data = multiOrSearchDoc(index, type, shouldMap, orderMap, objectClass, from, pageSize);
		
		result.put("totalRecords", hits.getTotalHits());
		result.put("totalPage", totalPage);
		result.put("pageSize", pageSize);
		result.put("pageNum", pageNum);
		result.put("data", data);
		
		return result;
	}

	/**
	 * 功能描述：统计查询
	 * @param index 索引名
	 * @param type 类型
	 * @param constructor 查询构造
	 * @param groupBy 统计字段
	 */
	public static Terms statSearch(String index, String type, ESQueryBuilderConstructor constructor, String groupBy) {
		Map<Object, Object> map = new HashMap();
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch();
		if(index != null && index.length() != 0) {
			searchRequestBuilder.setIndices(index);
		}else {
//            searchRequestBuilder.setIndices(type);
		}
		searchRequestBuilder.setTypes(type);
		//排序
		if (constructor != null && org.apache.commons.lang3.StringUtils.isNotEmpty(constructor.getAsc()))
			searchRequestBuilder.addSort(constructor.getAsc(), SortOrder.ASC);
		if (constructor != null && org.apache.commons.lang3.StringUtils.isNotEmpty(constructor.getDesc()))
			searchRequestBuilder.addSort(constructor.getDesc(), SortOrder.DESC);
		//设置查询体
		if (null != constructor) {
			searchRequestBuilder.setQuery(constructor.listBuilders());
		} else {
			searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
		}
		int size = constructor != null ? constructor.getSize() : 10;
		if (size < 0) {
			size = 0;
		}
		if (size > MAX) {
			size = MAX;
		}
		//返回条目数
		searchRequestBuilder.setSize(size);

		searchRequestBuilder.setFrom(constructor != null && constructor.getFrom() < 0 ? constructor.getFrom() : 0);
		SearchResponse sr = searchRequestBuilder.addAggregation(
				AggregationBuilders.terms("agg").field(groupBy)
		).get();

		Terms stateAgg = sr.getAggregations().get("agg");
//		Iterator<Terms.Bucket> iter = (Iterator<Terms.Bucket>) stateAgg.getBuckets().iterator();
//
//		while (iter.hasNext()) {
//			Terms.Bucket gradeBucket = iter.next();
//			map.put(gradeBucket.getKey(), gradeBucket.getDocCount());
//		}

		return stateAgg;
	}

	/**
	 * 功能描述：统计查询
	 * @param index 索引名
	 * @param type 类型
	 * @param constructor 查询构造
	 * @param agg 自定义计算
	 */
	public static Terms statSearch(String index, String type, ESQueryBuilderConstructor constructor, AggregationBuilder agg) {
		if (agg == null) {
			return null;
		}
		Map<Object, Object> map = new HashMap();
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch();
		if(index != null && index.length() != 0) {
			searchRequestBuilder.setIndices(index);
		}else {
//            searchRequestBuilder.setIndices(type);
		}
		searchRequestBuilder.setTypes(type);
		//排序
		if (constructor != null && org.apache.commons.lang3.StringUtils.isNotEmpty(constructor.getAsc()))
			searchRequestBuilder.addSort(constructor.getAsc(), SortOrder.ASC);
		if (constructor != null && org.apache.commons.lang3.StringUtils.isNotEmpty(constructor.getDesc()))
			searchRequestBuilder.addSort(constructor.getDesc(), SortOrder.DESC);
		//设置查询体
		if (null != constructor) {
			searchRequestBuilder.setQuery(constructor.listBuilders());
		} else {
			searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
		}
		int size = constructor != null ? constructor.getSize() : 10;
		if (size < 0) {
			size = 0;
		}
		if (size > 10) {
			size = MAX;
		}
		if (size < 0) {
			size = 0;
		}
		if (size > 10) {
			size = 10;
		}
		//返回条目数
		searchRequestBuilder.setSize(size);


		searchRequestBuilder.setFrom(constructor != null && constructor.getFrom() < 0 ? constructor.getFrom() : 0);
		SearchResponse sr = searchRequestBuilder.addAggregation(
				agg
		).get();

		Terms stateAgg = sr.getAggregations().get("agg");
//		Iterator<Terms.Bucket> iter = (Iterator<Terms.Bucket>) stateAgg.getBuckets().iterator();
//
//		while (iter.hasNext()) {
//			Terms.Bucket gradeBucket = iter.next();
//			map.put(gradeBucket.getKey(), gradeBucket.getDocCount());
//		}

		return stateAgg;
	}

	/**
	 * 重新索引
	 * @param oldIndex
	 * @param oldType
	 * @param newIndex
	 * @param newType
	 * @param constructor
	 */
	public static void reIndex(String oldIndex, String oldType, String newIndex, String newType, ESQueryBuilderConstructor constructor) {
		SearchHits searchHists= getSearchHits(oldIndex,oldType,constructor);

//		BulkRequestBuilder bulkRequest = client.prepareBulk();
//		for (SearchHit sh : searchHists) {
//			bulkRequest.add(client.prepareIndex(newIndex, newType)
//						.setSource(sh.getSourceAsString(), XContentType.JSON));
//		}
//		BulkResponse bulkResponse = bulkRequest.get();
	}

	/**
	 * 功能描述：查询
	 * @param index 索引名
	 * @param type 类型
	 * @param constructor 查询构造
	 */
	public static SearchHits getSearchHits(String index, String type, ESQueryBuilderConstructor constructor) {
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
		//排序
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(constructor.getAsc()))
			searchRequestBuilder.addSort(constructor.getAsc(), SortOrder.ASC);
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(constructor.getDesc()))
			searchRequestBuilder.addSort(constructor.getDesc(), SortOrder.DESC);
		//设置查询体
		searchRequestBuilder.setQuery(constructor.listBuilders());
		//返回条目数
		int size = constructor.getSize();
		if (size < 0) {
			size = 0;
		}
		if (size > MAX) {
			size = MAX;
		}
		//返回条目数
		searchRequestBuilder.setSize(size);

		searchRequestBuilder.setFrom(constructor.getFrom() < 0 ? 0 : constructor.getFrom());
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		return searchResponse.getHits();
	}


	public static Map<String, Object> searchHitsToMap(SearchHits searchHits, Class<?> clazz, ESQueryBuilderConstructor constructor) {
		SearchHit[] hits = searchHits.getHits();
		List<Object> list = new ArrayList<Object>();
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < hits.length; i++) {
			String json = hits[i].getSourceAsString();
			String id = hits[i].getId();
			try {
				ObjectNode jsonNode = mapper.readValue(json, ObjectNode.class);
				jsonNode.put("id", id);
				Object bean = mapper.readValue(jsonNode.toString(), clazz);
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


	public static <T> List<T> searchHitsToObj(SearchHits searchHits, Class<T> clazz) {
		SearchHit[] hits = searchHits.getHits();
		List<T> list = new ArrayList<>();
		for (int i = 0; i < hits.length; i++) {
			String json = hits[i].getSourceAsString();
			String id = hits[i].getId();
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
			jsonObj.put("id", id);
			T bean = JsonBeanUtil.transJson2Obj(jsonObj.toJSONString(), clazz);
			list.add(bean);
		}
		return list;
	}

	public static <T> T searchById(String index, String type, String id, Class<T> clazz) {
		GetResponse response = client.prepareGet(index, type, id)
				.setOperationThreaded(false)
				.get();
		T t = JSON.parseObject(response.getSourceAsString(),clazz);
		try {
			Method setId = clazz.getMethod("setId", String.class);
			setId.invoke(t,id);
		} catch (Exception e) {
//			throw new PiperException(ApiCode.ERROR,"Es setId错误");
		}
		return t;
	}
	
	/**
	 * scroll方式读取全量数据，不支持排序，最多读取10万条数据
	 * @param fromIndex
	 * @param fromType
	 * @param need <=0时读取所有数据（做多10万条）
	 * @param mustMap 查询条件
	 * @param objectClass
	 * @return
	 */
	public static <T> List<T> scrollData(String fromIndex, String fromType, int need, Map<String, Object> mustMap, Class<T> objectClass) {
		List<T> l = new ArrayList<>();
		
		need = (need==0||need<0?100000:need);
		
		
		Client esClient = getClient();
		SearchResponse searchResponse = null;
		boolean Batchsizeistoolarge = false;
		try {
			SearchRequestBuilder requestBuilder = esClient.prepareSearch(fromIndex).setTypes(fromType);
			requestBuilder = buildResponse(requestBuilder,mustMap);
			searchResponse = requestBuilder.addSort(SortBuilders.fieldSort("_doc")).setSize(need)
					.setScroll(TimeValue.timeValueMinutes(8)).execute().actionGet();
		} catch (Exception e1) {
			if(e1.getCause().getMessage().indexOf("Batch size is too large")>=0){
				Batchsizeistoolarge = true;
				need = need/2;
			}
		}
		
		while(Batchsizeistoolarge==true){
			try {
				SearchRequestBuilder requestBuilder = esClient.prepareSearch(fromIndex).setTypes(fromType);
				requestBuilder = buildResponse(requestBuilder,mustMap);
				searchResponse = requestBuilder.addSort(SortBuilders.fieldSort("_doc")).setSize(need)
						.setScroll(TimeValue.timeValueMinutes(8)).execute().actionGet();
				Batchsizeistoolarge = false;
			} catch (Exception e1) {
				if(e1.getCause().getMessage().indexOf("Batch size is too large")>=0){
					Batchsizeistoolarge = true;
					need = need/2;
				}
			}
		}
		List<T> listpage1 = null;
		try {
			listpage1 = transSearchResponse(objectClass, searchResponse);
			l.addAll(listpage1);
			listpage1 = null;
		} catch (Exception e) {
		}
		
		return l;
	}
	
	public static <T> List<T> transSearchResponse(Class<T> objectClass, SearchResponse searchResponse) {
		List<T> list = new ArrayList<>();
		SearchHits hits = searchResponse.getHits();
		int num = hits.getHits().length;
		for (int i = 0; i < num; i++) {
			// n++;
//			String id = hits.getHits()[i].getId();
			// System.out.println("第"+n+"条数据："+id);
			String json = hits.getHits()[i].getSourceAsString();
			com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(json);
//			jsonObj.put("id", id);
			jsonObj.remove("id");
			T bean = JsonBeanUtil.transJson2Obj(jsonObj.toJSONString(), objectClass);
			list.add(bean);
		}

		return list;
	}
	
	
	public static Long multiAndSearchDocCount(String index,String type, Map<String, Object> mustMap) {

		TransportClient client = getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
		responsebuilder.setFrom(0);
		responsebuilder.setSize(1);
		responsebuilder.setExplain(true);

		responsebuilder = buildResponse(responsebuilder,mustMap);

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits hits = myresponse.getHits();

		//总记录数
		Long total = hits.getTotalHits();

		return total;
	}
	
	public static <T> Map<String, Object> scrollDataWithPage(String fromIndex, String fromType, Integer size, Integer page, Map<String, Object> mustMap, Class<T> objectClass) {
		Map<String, Object> result = new HashMap<>(5);
		List<T> l = null;
		//默认第一页
		page = (page==null||page==0||page<0)?1:page;
		//默认每页15条
		size = ((size==null||size==0||size<0)?15:(size>10000?15:size));
		Long totalRecords = multiAndSearchDocCount(fromIndex, fromType, mustMap);
		Client esClient = getClient();
		SearchResponse searchResponse = null;
		
		SearchRequestBuilder requestBuilder = esClient.prepareSearch(fromIndex).setTypes(fromType);
		requestBuilder = buildResponse(requestBuilder,mustMap);
		searchResponse = requestBuilder.addSort(SortBuilders.fieldSort("_doc")).setSize(size)
				.setScroll(TimeValue.timeValueMinutes(1)).execute().actionGet();
		l = new ArrayList<T>(size);
		
		int totalPage = (int) (totalRecords / size) + (totalRecords % size == 0 ? 0 : 1);// 计算总页数
		page = page>totalPage?totalPage:page;
//		System.out.println("总数：" + totalRecords + "，共" + page+"/"+totalPage + "页" + "，每页" + size + "条数据");
		
		if(page==1){
			l = transSearchResponse(objectClass, searchResponse);
		}else{
			for (int i = 2; i <= page; i++) {
				searchResponse = getClient().prepareSearchScroll(searchResponse.getScrollId())
						.setScroll(TimeValue.timeValueMinutes(8)).execute().actionGet();
			}
			l = transSearchResponse(objectClass, searchResponse);
		}
		
		
		result.put("totalRecords", totalRecords);
		result.put("totalPage", totalPage);
		result.put("pageSize", size);
		result.put("pageNum", page);
		result.put("data", l);
		return result;
	}
}
