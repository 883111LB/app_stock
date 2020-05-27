package com.cvicse.stock.http;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.cvicse.base.ui.BaseApplication;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.netinfo.Location;
import com.cvicse.stock.util.LocationConvertUtils;


/**
 * Created by ruan_ytai on 17-5-1.
 */

public class GLocationUtils {

    private static final long LOCATION_UPDATE_MIN_TIME = 5000;
    private static final float LOCATION_UPDATE_MIN_DISTANCE = 10 ;

    static Location location;
   // private static LocationClient mLocationClient;
    private static LocationManager mLocationManager;

    /**
     *  用来监听取得更新位置后的动作
     */
    private static LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final android.location.Location location) {
            if(location != null){
                //Log.d("ryt","纬度："+location.getLatitude()+"经度："+location.getLongitude());
                //ToastUtils.showLongToast("纬度："+location.getLatitude()+"经度："+location.getLongitude());

                LocationConvertUtils.queryCity(location);
                //获取城市名
                LocationConvertUtils.setOnQueryCityListener(new LocationConvertUtils.OnQueryCityListener() {
                    @Override
                    public void onCitySuccess(String cityName) {
                      if(!TextUtils.isEmpty(cityName)){
                          Location mylocation = new Location();
                          mylocation.setAddress(cityName);
                          mylocation.setLangitude(location.getLongitude());
                          mylocation.setLatitude(location.getLatitude());

                          //ToastUtils.showLongToast(cityName);
                          Log.d("Glocation ryt",cityName);
                          GLocationUtils.location = mylocation;
                          //mLocationManager.removeUpdates(mLocationListener);
                      }
                    }
                });


            }else{
                ToastUtils.showLongToast("get location fail");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public static void init(){
        mLocationManager = (LocationManager) BaseApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
       /* int googlePlayStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(BaseApplication.getInstance());
        if(googlePlayStatus != ConnectionResult.SUCCESS){
            ToastUtils.showLongToast("googlePlayStatus " +googlePlayStatus);
        }else{
            getCurrentLocation();
        }*/
        getCurrentLocation();
       /* initLocation(mLocationClient);*/
    }

   /* private static void initLocation(LocationClient mLocationClient){

    }*/

    /**
     * 取得目前位置的實作，我們由網路和GPS來取得定位，因為GPS精準度比網路來的更好，
     * 所以先使用網路定位、後續再用GPS定位，如果兩者皆無開啟，則跳無法定位的錯誤訊息。
     */
    private static void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        android.location.Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled)){
            //ToastUtils.showLongToast("网络与GPS定位都不可用");
             /*Snackbar.make(mMapView, R.string.error_location_provider, Snackbar.LENGTH_INDEFINITE).show();*/
        } else {
            if (isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {
            /*Logger.d(String.format("getCurrentLocation(%f, %f)", location.getLatitude(),
                    location.getLongitude()));*/
            //drawMarker(location);
            final android.location.Location finalLocation = location;
            LocationConvertUtils.queryCity(location);
            //获取城市名
            LocationConvertUtils.setOnQueryCityListener(new LocationConvertUtils.OnQueryCityListener() {
                @Override
                public void onCitySuccess(String cityName) {
                    if(!TextUtils.isEmpty(cityName)){
                        Location mylocation = new Location();
                        mylocation.setAddress(cityName);
                        mylocation.setLangitude(finalLocation.getLongitude());
                        mylocation.setLatitude(finalLocation.getLatitude());

                        ToastUtils.showLongToast(cityName);
                        GLocationUtils.location = mylocation;
                        //LocationConvertUtils.queryCity(location);
                    }
                }
            });


        }
    }





}
