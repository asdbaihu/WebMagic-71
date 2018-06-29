package com.fzd.ali.po;

import com.fzd.util.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by FZD on 2018/5/18.
 * Description:
 */
public class CompanyBaseContentProcessor {

    public CompanyInfo getCompanyBase(Document doc){
        CompanyInfo companyBase = new CompanyInfo();
        BusinessInfo businessInfo = parseBusinessInfo(doc);
        companyBase.setBusinessInfo(businessInfo);
        return companyBase;
    }

    private BusinessInfo parseBusinessInfo(Document doc) {
        BusinessInfo businessInfo = new BusinessInfo();
        Elements rootEl = doc.select("#J_COMMON_CompanyRegisterInfoBox").select(".register-data");
        businessInfo.setAuthentication(rootEl.select(".title-remark").text());
        Elements trs = rootEl.select("tr");
        for(int i = 0; i < trs.size(); i++){
            Elements tdValue = trs.get(i).select(".td-value");
            String value1 = null;
            String value2 = null;
            if(tdValue != null) {
                if (tdValue.get(0) != null)
                    value1 = tdValue.get(0).text();
                if(tdValue.get(1) != null)
                    value2 = tdValue.get(0).text();
            }
            switch (i){
                case 0:
                    businessInfo.setName(value1);
                    businessInfo.setRegAdr(value2);
                    break;
                case 1:
                    businessInfo.setRegCapital(StringUtils.getFloatFromStr(value1));
                    businessInfo.setRegCur(StringUtils.getChineseFromStr(value2));
                    businessInfo.setRegDate(new Date());
                    break;
                case 2:
                    businessInfo.setIdentifyNo(value1);
                    businessInfo.setCorporate(value2);
                    break;
                case 3:
                    businessInfo.setRegAuthority(value1);
                    businessInfo.setType(value2);
                    break;
                case 4:
                    String[] pattern = {"yyyy-mm-dd"};
                    if(!StringUtils.isEmpty(value1)){
                        String[] optPeriod = value1.trim().split("至");
                        if(optPeriod.length == 2){
                            try {
                                Date start = DateUtils.parseDate(optPeriod[0], pattern);
                                businessInfo.setOptPeriodStart(start);
                                Date end = DateUtils.parseDate(optPeriod[1], pattern);
                                businessInfo.setOptPeriodEnd(end);
                            } catch (ParseException e) {
                                System.out.println("工商信息经营日期错误!!!");
                            }
                        }else {
                            System.out.println("工商信息经营日期：" + value1);
                        }
                    }
                    businessInfo.setAnnuals(value2);
                    break;
                case 5:
                    businessInfo.setOptScope(value1);
                    break;
                default:break;
            }
        }
        return businessInfo;
    }
}
