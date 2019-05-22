package com.zw.opencv.util;

import java.util.*;

/**
 * @program: opencv
 * @description: dd
 * @author: Mr.zhang
 * @create: 2019-05-16 20:47
 **/
public class CommonUtil {

    /**
     * 使用 Map按value进行排序
     * @param map
     * @return
     */
    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public  Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }






    class  MapKeyComparator implements Comparator<String>{

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

    /**
     * 使用 Map按value进行排序
     * @param map
     * @return
     */
    public  Map<String, String> sortMapByValue(Map<String, String> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, String>> iter = entryList.iterator();
        Map.Entry<String, String> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    class MapValueComparator implements Comparator<Map.Entry<String, String>> {

        @Override
        public int compare(Map.Entry<String, String> me1, Map.Entry<String, String> me2) {

            return me1.getValue().compareTo(me2.getValue())*(-1);
        }
    }



}
