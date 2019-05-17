package com.zw.opencv.util;

import java.util.Comparator;
import java.util.Map;

/**
 * @program: opencv
 * @description: ddd
 * @author: Mr.zhang
 * @create: 2019-05-16 20:43
 **/
public class MapValueComparator implements Comparator<Map.Entry<String, String>> {

    @Override
    public int compare(Map.Entry<String, String> me1, Map.Entry<String, String> me2) {

        return me2.getValue().compareTo(me1.getValue());
    }
}