package com.zw.opencv.util;

import java.util.Comparator;
import java.util.Map;

/**
 * @program: opencv
 * @description: ddd
 * @author: Mr.zhang
 * @create: 2019-05-16 20:43
 **/
public class MapValueComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }
}