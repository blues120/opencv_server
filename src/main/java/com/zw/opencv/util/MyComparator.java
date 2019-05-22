package com.zw.opencv.util;

import java.util.Comparator;

/**
 * @program: opencv
 * @description: 倒叙
 * @author: Mr.zhang
 * @create: 2019-05-21 14:05
 **/
public class MyComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1 < o2){
            return 1;
        }else if (o1 > o2){
            return -1;
        }else{
            return 0;
        }
    }
}
