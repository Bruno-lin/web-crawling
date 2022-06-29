package com.teamscorpio.webcrawling;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
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
    public void getRealItemList(List<String> itemList) {
        for (String item : itemList) {
            String digiKeyUrl = "https://www.digikey.com/suggestions/v1/suggestions/search";
            digiKeyUrl = UriComponentsBuilder.fromHttpUrl(digiKeyUrl)
                    .queryParam("keywordPrefix", item)
                    .queryParam("maxSuggestions", "5")
                    .encode()
                    .toUriString();
            System.out.println(digiKeyUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            headers.add("accept-encoding", "gzip, deflate, br");
            headers.add("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,pt;q=0.7,ko;q=0.6");
            headers.add("cache-control", "no-cache");
            headers.add("cookie", "oAuthMiddleware.unsecure=debugging-unsecure; oAuthMiddleware.secure=debugging-secure; utag_multi=koddi_guid:b4bee226-c0d5-484a-a23c-f77a5c0fd8cd%3Bexp-session; ai_user=oM3WPYWZgJBizt0gNp2N3d|2022-06-23T09:47:58.296Z; _abck=20A3F89066B425622E1FBA7ECA2A5CDB~0~YAAQlBw/O+6dXz2BAQAAM7MkqAiBDTzXnFtVsL21Ok6KKNJiH4+lNL4Q4p99yRHnUn8YstaAN+dvRVc2+xKL2/v3kQ0LkmQ1SzENsnvjtSSSKE1/55VFuOsARNXy6/rpG/SLA9u5SUmYNPhqGKe4HYnk2O8gMF6b/W8bh/62RA5kEykgDGL0p8INQUbvQ4GpwIyoPffYVIqXhN4GD00qk81/GWxIwDImGzaV4hbPp39isVczK85eIfwcacq00I2WkRswQV/s6p0fNEP08uu1jypx822vOABKKx8uN7K4wgkgJOKFfxnULl4Cyl159Eq5Zma9OIAzhiOkDmCoru2Q/7WvR7bntHok8cNKJoXZE9sIHs/AWkE4D47QUm7GlY5Fo6a9QRDhMC+JwFV28D1lwbOmi7dTes0=~-1~-1~-1; bm_sz=97E3E7FC21928449164049AB8346BC26~YAAQlBw/O++dXz2BAQAAM7MkqBAbLvcT3zQrM/AHkDGEc86q/OYssMWo9cBarz+qoOmG02fT38Y+S4tvMnsbG+YqWZCRz9jkHQu9CQff+fshuhG4d+Pz4GVYdqXWJXvLO6iOYX+c4iCi7pxH7lOOtEvU2fL6lR320MIvAw31rI6/e8JsM9dvBMToHYSLU2ZlayOz9Mg9wwfOfpYlObhURr19J19468qhl6MRLCaajHhzpvHBUOP8l9rbbK3gCCaAy98/L4sa+qpt1hw4oCe+gqdc5I2flbGsTnQZVFMp5N2CkyI=~4343110~3749689; _cs_mk=0.05639410731005734_1656383393051; TS01d90084=01460246b644632a0db28740adc347493c3cd0e6320aeb70ff097349a2fcc346c1c5ebf59bc3e778e806e04bdf78f5e00c6b7ddb09; dkuhint=false; ps-eudo-sid=%7b%22CustomerId%22%3a0%2c%22LoginId%22%3a0%2c%22CustomerClass%22%3a0%2c%22Currency%22%3a%22USD%22%2c%22OrderModel%22%3a0%2c%22UserIdentity%22%3a0%7d; search=%7B%22usage%22%3A%7B%22dailyCount%22%3A3%2C%22lastRequest%22%3A%222022-06-28T02%3A30%3A26.829Z%22%7D%2C%22version%22%3A1%7D; ai_session=/A/wPwXKGtV8WyZ3q95v/M|1656383427993|1656383427993; bm_mi=7AA6D72CF7B1D7E94152778A9B9E7DDC~YAAQlBw/O82fXz2BAQAA2sAlqBAI8mQv8+gDb/erzz8Y9GkkSl4H8XOvKP6CARfodfKKSkGPmsT8R1D3mnhioYM2Rr1eJ3x6JbEIOILyLvwUiKKyP1R3HDyDUSNPzapsswk3jqCkW657FcOmwn/7bTJ3m3CsgDP/u8lAxWD6mo4gJhfytxArcf2baJJY7Ew62TR8ymC/GWwWwd4s8/ZqB/VNyIz3oi0M/NAwe8iF4VLoqukwQOyMvAvrAfBeNiiSuqpjDPlKWFmUPE3bKwAZjYUt6dYE9JOqJFVMHgt4CX2Bl1Tdi8Ia3xSU78Kga8juFmirlFV/GAeQDDroOoH5LW96dNmdcQgohhjhNipX/RKA1V4egEnFfTWzTn2kHWSw9k7czy+RfA==~1; bm_sv=9F41E213CE2658F892D30A9D63CF864B~YAAQlBw/Oz+gXz2BAQAAUfUlqBCQPNZi5UHtR+oDORA+HsnGztLILHXhKEhmbPxxSQdxwrzAzWinhOXTlIQ5niiNhx7Mda3/nv6v+ZQO91tNGn6w1JjWxfFFhaFJyzz11OLqyloQPRFWq2EcGFCievIkoYtePzrnKsUmFzfLUiPFZtQeV4mXm2Kczy18BNkQnb6F3q1uVRIrd8m5zGueirBFBX/r5UG3WPjm0/RvKFTQssas6a55tU/wbuQTFMbzuQ==~1; ak_bmsc=3667E7B4C5BAF3184BF8420761066713~000000000000000000000000000000~YAAQlBw/O4m9Xz2BAQAATlY0qBB+ijuK4knWPIu5dI7VIYa+Bu3bpWqKE8NVuy2KI+/oXRFAG/iH8WJvk4ckO55/CIs2qX9xnzMLrWq2a3IQdJnhkf8spA2+Yhbl72ZpsA68PfxtTQfyMVXWT5lL3dzLQoC6t/3WSNaP1GdmuNo45znQHiNnFTB4OOaVSgCHZOliNifvTkcMPzVWQ1tG8Q+j8RDx+ZcZ1NAHCKvrl4ITRm40NfsagvZlKq0u+Eb6qsOqgaSpKDUVbf8Y7oVqv2SJgQ6wmmG6s5Bgq9oYjrVe8y2eIpwzcw0QjrXSAjJ+Q8G5a6tlHgD/F64igjz52osunjopaJ1SOLNeclJ9tiHMhW1oGnxYWeJnwl++jZ6wMEwzOfWbv4xq+UZ7QhR1XY5aoD0Wg3h9upg7UUqFI0te2CCMTsFGNMZd7aPne0jWixKKQ+ASy9VjUt4vSpgtnhitva/80ue0wntxj7smTE9riPtCVqno1eU9; TScb802422027=089265d43dab2000d7d9c6bb1c0e48f524611987aa8116b02e21a84abf40eefbd8fa253798fb44c908d69d9641113000dc8e9c97a3f479a5c19b375f97be9ad77b0730d2052ce3a41ac6b714116a138df5b6408d7a71d92adc4f5cc6fc776d7c; TS01173021=01a0cfa1f23737576f3a92bb8a9bd1648012492db3fdf7577b19a8796db5f3487d2961dfe5ab5c33ca16ab5428bea71b86c694d698; TSf2b291e0027=085f3c8666ab2000e83f6cfb657942f68f49b5fe73946f826ea239c216379d2a8dac5c9f2d41efb608054f968211300044c39081e45f15ab011c6f9f1ca54429cd71686a8c2183f400d8d7b2ef226ce3cc14fd8b8dedae16e321823765db7921; website#lang=; TS01c02def=01460246b6bbefbda3f3b79bafe0613915afa29633761b612b98cf63745ff1f18253d5995de188ce0405cb3bda2e143e3f1d5133c7; TS01b6e805=01460246b6bbefbda3f3b79bafe0613915afa29633761b612b98cf63745ff1f18253d5995de188ce0405cb3bda2e143e3f1d5133c7; TS809e22c5027=08374f23c1ab20005f34c486815dac9e0af1f4d2143a27f336e008400f2577dde9c4abed1198ff9408f58350bb1130007ce23f0c96681096464d72e1df7dc764ec258fc447dc7ee3e37d1b45280f900a257bb3b61313b11b9a1acf7d6e08225b; TS01cba742=01460246b65d25e228298cca9ee393de17ab63e98b568e5c77abcb84a7d3001d916b5439c75456df2e8fc4892b6bf4063d48c72982; TS01bd62a8=01460246b65d25e228298cca9ee393de17ab63e98b568e5c77abcb84a7d3001d916b5439c75456df2e8fc4892b6bf4063d48c72982; TS605a4192027=08374f23c1ab2000ae63f944988d3aa642a50381017a751c87a640ff4cdc73a58244045bc0a3e1e7089a20eb021130002fa18da0347be01c464d72e1df7dc764247725aa9f2a20df58168e24ac4b62bb8faee6934d7f703fcde0cf66579b94b3; TS016ca11c=01460246b68f34d1359cab16125728ef957d31ba3a0e1dde655a7aab342bb522c5c4865935092560e79985301decd9d776fd02df7c; TS011a64f6=01460246b68f34d1359cab16125728ef957d31ba3a0e1dde655a7aab342bb522c5c4865935092560e79985301decd9d776fd02df7c; TSc580adf4027=08374f23c1ab20009c95bc3f55f0f81fcab9c78e5e03b857495d3271b687e4fbbdca6a620dfe117e08c14d9de1113000b6a6387e58b70c34464d72e1df7dc7649408bcf52595bcd92e9581ef68f5dec45cb53e90b8d29b26650c2ab1d8687e7f; TS0198bc0d=01460246b65350fdf1f813a2b524ed47a68a92037c87616a055d209669ba53abef154a937aa0052069a894f4377ef763efd268397f; TSe14c7dc7027=08374f23c1ab20006df14cf0a1f05b47679f3fa66d6b84143678f92ee442834a6f8165d50ddc79ac08fe1298b8113000a4f2e8d7107e61da464d72e1df7dc7640a909f97b6710370c4b9663a64539e5217c16487fc1adadadb11a0a7298fcd45; TSac889e6d027=08374f23c1ab2000e5606d838f8fb42eaf6ac2273173af6c0a3ee90de891b160e5d2b14dae329a780823390239113000afab1bd72d94517f464d72e1df7dc7645e0ca34599f2da3e7a78ef56cf4c6500e3f280a0e6913bf43ca1cc2df4ca36f2; TS01afc56e=01460246b6c2674f2196d7c00fb10bc587d1bfcb5087d13d17e8d72416e00e2ffbba3e046dede46bcd5dbf6846083c3fd137346a6f; TSf44f2996027=08374f23c1ab20000e69a644b311a9dd3e6d8c7597dd88b1706f3ad2686581cbf43c55792746e1c108fd9ce4a5113000fb4d3680c4ab8c03464d72e1df7dc764db7e68a6a88d76c0c9b009635480f0e04932477b0e42f988f8707831bee5a16d; utag_main=v_id:01818ff5dbf10001508bd03b36e605065001705d00bd0$_sn:4$_se:137$_ss:0$_st:1656387576860$ses_id:1656383392867%3Bexp-session$_pn:15%3Bexp-session");
            headers.add("lang","zh");
            headers.add("pragma","no-cache");
            headers.add("referer", "https://www.digikey.cn/");
            headers.add("sec-ch-ua","\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Google Chrome\";v=\"102\"");
            headers.add("sec-ch-ua-mobile","?0");
            headers.add("sec-ch-ua-platform","\"Linux\"");
            headers.add("sec-fetch-dest","empty");
            headers.add("sec-fetch-mode","cors");
            headers.add("sec-fetch-site","same-origin");
            headers.add("site","US");
            headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
            headers.add("x-currency", "USD");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            JSONObject body = restTemplate.getForEntity(digiKeyUrl, JSONObject.class, entity).getBody();
            System.out.println(body);
        }
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
