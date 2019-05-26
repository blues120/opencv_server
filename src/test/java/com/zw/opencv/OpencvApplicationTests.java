package com.zw.opencv;

import com.zw.opencv.util.ToolsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpencvApplicationTests {

    @Test
    public void contextLoads() {

        int n=2;
        long m[]={2431,437};
        long a[]={873,358};

        long result=ToolsUtil.China(n,m,a);
    }

}
