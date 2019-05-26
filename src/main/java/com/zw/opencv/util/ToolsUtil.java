package com.zw.opencv.util;

/**
 * @program: opencv
 * @description:
 * @author: Mr.zhang
 * @create: 2019-05-25 08:59
 **/
public class ToolsUtil {

    public static long China(int n,long m[],long a[])
    {
        long M=1;
        for(int i=0;i<n;i++)
        {
            M*=m[i];
        }
        TempDTO tempDTO=new TempDTO();
//            d,x,y顺序参数
        tempDTO.setD(0);
        tempDTO.setX(0);
        tempDTO.setY(0);

        for(int i=0;i<n;i++){
            long w=M/m[i];

            gcd(m[i],w,tempDTO);
            tempDTO.setX((tempDTO.getX()+tempDTO.getY()*w*a[i])%M);

        }
        return (tempDTO.getX()+M)%M;
    }


    public static void gcd(long a,long b,TempDTO tempDTO)
    {
        System.out.println("d="+tempDTO.getD()+"x="+tempDTO.getX()+"y="+tempDTO.getY());
//
//        if(b==0){
//            d=a;
//            x=1,y=0;
//        }
//        else{//else不能省略
//            gcd(b,a%b,d,y,x);
//            y-=(a/b)*x;
//        }

        if(b==0){
            tempDTO.setD(a);
            tempDTO.setX(1);
            tempDTO.setY(0);

        }
        else{//else不能省略
//            d,x,y转换成d,y,x
            long temp=tempDTO.getX();
            tempDTO.setX(tempDTO.getY());
            tempDTO.setY(temp);
            gcd(b,a%b,tempDTO);

            tempDTO.setY(tempDTO.getY()-(a/b)*tempDTO.getX());
//            y-=(a/b)*x;
        }
    }
}
