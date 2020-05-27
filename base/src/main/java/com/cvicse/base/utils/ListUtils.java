package com.cvicse.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu_zlu on 2017/3/16 16:34
 */
public class ListUtils {
    /**
     *  把源List数据copy到目的List中
     * @param src 源List
     * @param srcPos 源List开始copy位置
     * @param dest 目的List
     * @param length copy长度
     * @param filter 过滤器
     * @param <T>
     */
    public static <T> void copy(List<T> src, int  srcPos,
                                List<T> dest,
                                int length,Filter<T> filter){
        if(src == null || dest == null)return;
        int srcLength = src.size()-srcPos;
        for(int i = 0;i < srcLength && i < length;i++){
            T t =src.get(i+srcPos);
            if(filter != null){
                if(filter.filter(t)){
                    dest.add(t);
                }
                continue;
            }
            dest.add(t);
        }
    }

    /**
     * 过滤器
     * @param <T>
     */
    public interface Filter<T>{
         boolean  filter(T t);
    }

    /**
     * 把一个List copy到另一个List中，
     * @param dest
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> copy(List<T> dest){
        if(dest == null)return new ArrayList<>();
        int srcLength = dest.size();
        ArrayList<T> src = new ArrayList<>();
        for(int i = 0;i < srcLength ;i++){
            src.add(dest.get(i));
        }
        return src;
    }

}
