package com.fzd.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;

public class JsonBeanUtil {
	public static Object transJson2Bean(String jsonStr, Class<?> objectClass) {

		Object o = null;
		 
		try {  
			o = JSON.parseObject(jsonStr, objectClass);
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
		
		return o;
    }

	//by 泛型
	public static <T> T transJson2Obj(String jsonStr, Class<T> objectClass) {

		T o = null;

		try {
			o = JSON.parseObject(jsonStr, objectClass);
		} catch (Exception e) {
			System.out.println("transJson2Obj Error " + e);
		}

		return o;
	}

	public static String transBean2Json(Object o) {  

		String jsonStr = "";
		
		try {  
			jsonStr = JSON.toJSONString(o);
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
		
		return jsonStr;
    }

	/**
	 * Json中为""的value转化为null
	 * @param obj
	 * @return
     */
	public static Object emptyStringToNull(Object obj){
		if(obj instanceof JSONObject) {
			for (Map.Entry<String, Object> entry : ((JSONObject)obj).entrySet()) {
				if (entry.getValue() instanceof String && StringUtils.isEmpty((CharSequence) entry.getValue()))
					((JSONObject) obj).put(entry.getKey(), null);
				else if(entry.getValue() instanceof JSONObject || entry.getValue() instanceof JSONArray)
					emptyStringToNull(entry.getValue());
			}
		}else if(obj instanceof JSONArray){
			JSONArray jsonArray = (JSONArray) obj;
			for(Object temp : jsonArray){
				emptyStringToNull(temp);
			}
		}
		return obj;
	}
	
	public static void main(String[] args) {
		String str = "[{\"doctype\":\"\",\"submittime\":\"1446652800000\",\"casereason\":\"承揽合同纠纷\",\"abstracts\":\"\\n威海经济技术开发区人民法院\\n民 事 裁 定 书\\n（2015）威经技区商初字第306号\\n原告山花地毯销售有限公司，住所地威海市青岛南路100号。\\n法定代表人刘晓芳，董事长。\\n委托代理人于年义，公司法律办主任。\\n被告双流县畅春园会议中心，住所地四川省成都市双流县。\\n\",\"lawsuitUrl\":\"https://www.tianyancha.com/lawsuit/09ce98fb1cbc11e6b554008cfae40dc0\",\"casetype\":\"民事案件\",\"id\":\"11155076\",\"title\":\"山花地毯销售有限公司与双流县畅春园会议中心承揽合同纠纷一审民事裁定书\",\"court\":\"威海经济技术开发区人民法院\",\"caseno\":\"（2015）威经技区商初字第306号\",\"uuid\":\"09ce98fb1cbc11e6b554008cfae40dc0\",\"url\":\"http://wenshu.court.gov.cn/content/content?DocID=722848ab-b1d7-431a-bac0-d73542bcb780\"},{\"submittime\":\"1435248000000\",\"casereason\":\"借款合同纠纷\",\"defendants\":\"双流县畅春园会议中心\",\"abstracts\":\"成都市武侯区人民法院\\n民 事 裁 定 书\\n（2014）武侯民初字第4326号\\n原告钟华林。\\n委托代理人刘钟蔚，四川索正律师事务所律师。\\n被告黎昌全。\\n被告卓小惠。\\n被告双流县畅春园会议中心。住所地：成都市双流县东升街道长顺路一段5号。\\n法定代表人刘伯平，总经理。\\n三被告共同委托代理人邓辉，四川衡平律师事务所律师。\\n三被告共同委托代理人孙利平，四川衡平律师事务所律师。\\n\",\"lawsuitUrl\":\"https://www.tianyancha.com/lawsuit/1569cda246d74841aa6b22c66fc9aba8\",\"court\":\"成都市武侯区人民法院\",\"title\":\"钟华林与黎昌全、卓小惠、双流县畅春园会议中心借款合同纠纷一案一审民事裁定书\",\"caseno\":\"（2014）武侯民初字第4326号\",\"uuid\":\"1569cda246d74841aa6b22c66fc9aba8\",\"url\":\"http://wenshu.court.gov.cn/content/content?DocID=61d9ed9e-30f6-4383-83b1-d350f7f61091\",\"doctype\":\"民事案件裁定书\",\"casetype\":\"民事案件\",\"id\":\"19132417\"}]";
		JSONArray resultDataJson = JSONObject.parseArray(str);
		Object obj = emptyStringToNull(resultDataJson);
		System.out.println(obj.toString());
	}
}
