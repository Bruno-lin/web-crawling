package com.teamscorpio.webcrawling;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class callWebImpl implements callWeb {

    private Map<String, Map<String, Integer>> stockTable = new HashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void callDigiKey(String itemCode, String itemId) {
        String digiKeyUrl = "https://www.digikey.cn/products/api/v4/pricing/" + itemId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept-language", "zh-cn");
        headers.add("x-currency", "CNY");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        JSONObject body = restTemplate.getForEntity(digiKeyUrl, JSONObject.class, entity).getBody();
        int stockQuantity = Integer.parseInt(Objects.requireNonNull(body)
                .getJSONObject("data")
                .getJSONArray("messages")
                .getJSONObject(0)
                .getString("message")
                .split(" ")[0]);
        Map<String, Integer> DigiTable = new HashMap<>();
        DigiTable.put("itemCode", stockQuantity);
        stockTable.put("DigiKey", DigiTable);
        System.out.println(stockTable);
    }

    @Override
    public void callArrow() {
        String arrowUrl = "https://www.arrow.com/api/pricing/getgroupedbuyingoptions";
        JSONObject jsonObject = null;
        HttpEntity<JSONObject> request = new HttpEntity<>(jsonObject);
        JSONObject object = restTemplate.postForObject(arrowUrl, request, JSONObject.class);
        System.out.println(object);
    }

    @Override
    public void callMouser() {
        String mouserUrl = "https://www.mouser.cn/Product/Product/GetProductInfoPartialViews";
        mouserUrl = UriComponentsBuilder.fromHttpUrl(mouserUrl)
                .queryParam("qs", "dJbwp1x2B3zDzJWzYYtT7lpxnIgcw99/48mDnfZ/eqKuK4nqhb//qFDOsxKAWYq9")
                .queryParam("isVip", "false")
                .queryParam("countryCode", "")
                .queryParam("currencyCode", "")
                .encode()
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "*/*");
        headers.add("accept-encoding", "gzip, deflate, br");
        headers.add("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,pt;q=0.7,ko;q=0.6");
        headers.add("cache-control", "no-cache");
        headers.add("cookie", "ASP.NET_SessionId=plemtz3mn0vjwx02fz0dn2wg; CARTCOOKIEUUID=3d618b38-b5ba-4e21-b8eb-cbe8be6bb0d9; preferences=pl=zh-CN&pc_cn2=RMB; akacd_Default_PR=3833429759~rv=22~id=eb9acede594a7d2f00dd2cc5bc97c118; LPVID=M2M2Y2ZTgxZDY4Y2JmMmJh; __RequestVerificationToken=Ain-NedidlVgMpANiMKbSA3cQZH_e1LUs6zxk0CnSLyAqV2AZfzo6huTMHtPnqxsUgP4I48vYgbOnColwiwQU34BQOM1; OptanonAlertBoxClosed=2022-06-23T09:37:57.016Z; __neoui=080e8ac5-ccf8-4b9b-88c8-c0aab791e830; AKA_A2=A; LPSID-12757882=Tj_JRS5IRh6E7UdxatxXvQ; OptanonConsent=isGpcEnabled=0&datestamp=Sat+Jun+25+2022+22%3A15%3A34+GMT%2B0800+(China+Standard+Time)&version=6.20.0&isIABGlobal=false&hosts=&consentId=30270db0-c9ea-447f-9d59-66f1944a97dc&interactionCount=2&landingPath=NotLandingPage&groups=C0001%3A1%2CC0002%3A1%2CC0004%3A1&AwaitingReconsent=false&geolocation=US%3BCA");
        headers.add("pragma","no-cache");
        headers.add("referer","https://www.mouser.cn/ProductDetail/Diodes-Incorporated/ZXMP6A17E6TA?qs=4zh0VFflF2EFHwDGsGBqJA%3D%3D");
        headers.add("sec-ch-ua","\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Google Chrome\";v=\"102\"");
        headers.add("sec-ch-ua-mobile","?0");
        headers.add("sec-ch-ua-platform","\"Linux\"");
        headers.add("sec-fetch-dest","empty");
        headers.add("sec-fetch-mode","cors");
        headers.add("sec-fetch-site","same-origin");
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        headers.add("x-requested-with","XMLHttpRequest");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println(entity);
        JSONObject body = restTemplate.getForEntity(mouserUrl, JSONObject.class, entity).getBody();
        System.out.println(body);
    }

    @Override
    public void callAvent() {
        String aventUrl = "https://www.avnet.com/search/resources/store/715839035/productview/byIds/";
        aventUrl = UriComponentsBuilder.fromHttpUrl(aventUrl)
                .queryParam("profileName", "Avn_findSubAlternativeProductsByParentCatentryId_Details")
                .queryParam("contractId", "4000000000000071008")
                .queryParam("storeId", "715839035")
                .queryParam("catalogId", "10001")
                .queryParam("langId", "-1")
                .queryParam("orgEntityId", "-2000")
                .queryParam("responseFormat", "json")
                .queryParam("pageSize", "10")
                .queryParam("pageNumber", "1")
                .queryParam("id", "3074457345616850870")
                .queryParam("parentId","3074457345616821986")
                .queryParam("parentId", "3074457345616908030")
                .queryParam("parentId", "3074457345616846419")
                .queryParam("parentId", "3074457345616865243")
                .queryParam("relationType", "subalternatives")
                .queryParam("wt", "json")
                .encode()
                .toUriString();
        JSONObject body = restTemplate.getForEntity(aventUrl, JSONObject.class).getBody();
        System.out.println(body);
    }
}
