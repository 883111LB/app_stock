package com.cvicse.stock.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ruan_ytai on 17-5-1.
 */

public class LocationConvertUtils {

     public  static void queryCity(final android.location.Location location){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 组装反向地理编码的接口地址
                    StringBuilder url = new StringBuilder();
                    url.append("http://maps.google.com/maps/api/geocode/json?latlng=");
                    url.append(location.getLatitude()).append(",")
                            .append(location.getLongitude());
                    url.append("&language=zh-CN");
                    url.append("&sensor=false");

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url.toString());
                    // 在请求消息头中指定语言，保证服务器会返回中文数据
                    httpGet.addHeader("Accept-Language", "zh-CN");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultArray = jsonObject.getJSONArray("results");
                        if (resultArray.length() >0){
                           JSONObject subjectObject = resultArray.getJSONObject(0);
                            // 取出格式化后的位置信息
                            String address = subjectObject.getString("formatted_address");
                            if(mlistener!=null){
                                mlistener.onCitySuccess(address);
                            }
                        }
                    }/*else{
                        //
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static OnQueryCityListener mlistener;

    public static void setOnQueryCityListener(OnQueryCityListener listener){
       mlistener = listener;
    }

    public interface OnQueryCityListener{
        void onCitySuccess(String cityName);
    }
}
