package com.zw.opencv.util;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: opencv
 * @description:
 * @author: Mr.zhang
 * @create: 2019-05-25 09:37
 **/
@Data
public class TempDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private long x;
    private long y;
    private long d;
}
