package com.fzd.ali.po;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fzd.httpclient4_5.HttpUtil;
import com.fzd.util.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by FZD on 2018/5/17.
 * Description:
 */
public class CreditDetailContentProcessor {

    public CreditDetail getCreditDetail(Document doc, String memberId){
        CreditDetail creditDetail = new CreditDetail();
        TradeSituation tradeSituation = parseTradeSituation(doc);
        ServiceSituation serviceSituation = parseServiceSituation(doc);
        StarStat starStat = parseStarStat(doc, memberId);
        creditDetail.setTradeSituation(tradeSituation);
        creditDetail.setServiceSituation(serviceSituation);
        creditDetail.setStarStat(starStat);
        return creditDetail;
    }

    private TradeSituation parseTradeSituation(Document doc){
        TradeSituation tradeSituation = new TradeSituation();
        Elements tradeDataLis = doc.select("#J_TradeSituationSumDataList").select("li");
        for(int i = 0; i < tradeDataLis.size(); i++){
            Elements el = tradeDataLis.get(i).select(".num");
            switch (i){
                case 0 :
                    tradeSituation.setTradeDealSumNum(Integer.parseInt(el.get(0).text()));
                    tradeSituation.setTradeDealAverageNum(Integer.parseInt(el.get(1).text()));
                    break;
                case 1 :
                    tradeSituation.setTradeBuyerSumNum(Integer.parseInt(el.get(0).text()));
                    tradeSituation.setTradeBuyerAverageNum(Integer.parseInt(el.get(1).text()));
                    break;
                case 2 :
                    tradeSituation.setRepurchaseRate(StringUtils.getFloatFromStr(el.get(0).text()));
                    tradeSituation.setRepurchaseRateAverage(StringUtils.getFloatFromStr(el.get(0).text()));
                    break;
                default:break;
            }
        }
        tradeSituation.setVerifiedMemberRate(StringUtils.getFloatFromStr(doc.select("#J_TradeChardDataNum_Member").text()));
        tradeSituation.setTbTmMemberRate(StringUtils.getFloatFromStr(doc.select("#J_TradeChardDataNum_Tmall").text()));
        Elements chartData = doc.select(".trade-chart-list").select(".chart-data");
        tradeSituation.setVerifiedMemberNum(StringUtils.getNumberFromStr(chartData.get(0).select(".num").text()));
        tradeSituation.setTbTmMemberNum(StringUtils.getNumberFromStr(chartData.get(1).select(".num").text()));
        return tradeSituation;
    }

    private ServiceSituation parseServiceSituation(Document doc){
        ServiceSituation serviceSituation = new ServiceSituation();
        Elements trs = doc.select("#J_ServiceSituationData_Table").select("tr");
        for(int i = 0; i < trs.size(); i++){
            Element tr = trs.get(i);
            Elements span = tr.select("span");
            switch (i){
                case 0:
                    serviceSituation.setDeliverySpeedNum(StringUtils.getFloatFromStr(span.get(0).text()));
                    serviceSituation.setDeliverySpeedAverageNum(StringUtils.getFloatFromStr(span.get(1).text()));
                    break;
                case 1:
                    serviceSituation.setReFundSpeedNum(StringUtils.getFloatFromStr(span.get(0).text()));
                    serviceSituation.setReFundSpeedAverageNum(StringUtils.getFloatFromStr(span.get(1).text()));
                    break;
                case 2:
                    serviceSituation.setReFundRate(StringUtils.getFloatFromStr(span.get(1).text()));
                    serviceSituation.setReFundAverageRate(StringUtils.getFloatFromStr(span.get(2).text()));
                    break;
                case 3:
                    serviceSituation.setCustomServiceRate(StringUtils.getFloatFromStr(span.get(1).text()));
                    serviceSituation.setCustomServiceAverageRate(StringUtils.getFloatFromStr(span.get(2).text()));
                    break;
                case 4:
                    serviceSituation.setDisputeRate(StringUtils.getFloatFromStr(span.get(1).text()));
                    serviceSituation.setDisputeAverageRage(StringUtils.getFloatFromStr(span.get(2).text()));
                    break;
                default:break;
            }
        }
        serviceSituation.setRefundTimes(StringUtils.getNumberFromStr(doc.select("#J_ServiceSituation_DetailInfo_Refund").select("span").get(0).text()));
        return serviceSituation;
    }

    private StarStat parseStarStat(Document doc, String memberId){
        StarStat starStat = new StarStat();
        String callBack = "jQuery17208899905368902117_1526543555641";
        Integer page = 1, nextPage = 0;
        ObjectMapper mapper = new ObjectMapper();
        List<Remark> remarkList = new ArrayList<>();
        List<StarStatItem> starStatItems = new ArrayList<>();
        do{
            String url = "https://rate.1688.com/remark/viewPubRemark/view.json?" +
                    "callback="+callBack+"&callback=callback&memberId=" +memberId +
                    "&memberRole=seller&starLevel=0&hasContent=all&tradeType=ALL&limit=all&page=" + page;
            String result = HttpUtil.doGet(url);
            if(!StringUtils.isEmpty(result)) result = result.substring((callBack + "(").length(), result.length() -2 );
            try {
                JsonNode root = mapper.readTree(result).get("data");
                ArrayNode remarkListNode = (ArrayNode) root.get("remarkList");
                remarkList.addAll((Collection<? extends Remark>) mapper.readValue(remarkListNode.toString(), new TypeReference<List<Remark>>(){}));
                nextPage = root.get("nextPage").asInt();
                if(nextPage == 0){
                    ArrayNode starStatisticsListNode = (ArrayNode) root.get("starStatisticsList");
                    starStatItems = mapper.readValue(starStatisticsListNode.toString(), new TypeReference<List<StarStatItem>>(){});
                    break;
                }
            }catch (IOException e) {
                e.printStackTrace();
                break;
            }
            page++;
        }while (true);
        starStat.setRemarkList(remarkList);
        starStat.setStarStatItemList(starStatItems);
        return starStat;
    }
}
