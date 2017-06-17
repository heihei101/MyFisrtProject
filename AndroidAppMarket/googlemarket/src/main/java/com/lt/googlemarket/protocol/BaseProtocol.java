package com.lt.googlemarket.protocol;

import android.text.TextUtils;

import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/13.
 */

public abstract class BaseProtocol<T> {
    //基础协议类.为不同的fragment获取数据提供父类
    //这里要提供一个方法,解析json的方法,访问网络的方法
    //缓存,写入文件的方法
    //当文件缓存在本地时,从本地读取文件,写一个方法从本地获取文件
    //写一个方法读取获取json,根据情况判断

    /*//1.获取数据,并且将获取数据后解析得到的bean对象返回,供界面(控件)使用
    * 由于不同页面获取的数据类型不同,要区分不同的页面
    * 要根据是否有缓存和缓存是否过期判断获取数据的方式
    * 此处要解析json串来返回不同的javabeen,所以使用泛型*/
    public T getData(String url, int index){
        //先尝试从缓存中读取数据
        String result = LocalData(url,index);
        String json=null;
        if(!TextUtils.isEmpty(result)){
            //如果result非空,说明从本地取到数据,直接读取;
            json=result;
        }else {
            //如果从本地读取失败,则从网络获取数据
            json=InternetData(url,index);
        }
        //此处回调解析方法,让子类实现对json的解析
       return parseJson(json);
    }
    public T getData(String url,int index,String params){
        //1.1 数据从缓存中来,json串
        String result = LocalData(url,params);
        String json = null;
        if (!TextUtils.isEmpty(result)){
            //缓存中有数据,解析,展示
            json = result;
        }else{
            //1.2 数据从网络中来,json串,解析,展示
            json = InternetData(url,index,params);
        }
        //2.json解析和展示
        return parseJson(json);

        //7.0模拟器
    }

    protected abstract T parseJson(String json);//交给子类实现

    //1.访问网络获取数据
        /*1.需要okhttp框架
        * 2.从接口文档中获取url地址以及参数,由于每次参数不同,所以需要在调用本方法时传入
        * 3.url是指不同fragment的数据地址,index是指传递的数据范围
        * 4.将获取到的json数据缓存到本地*/
    public static String InternetData(String url,int index){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            //如果还需要传递参数,则需要将参数拼接在index后面形式如下"&key=value&key1=value1"
            //http://127.0.0.1:8090/home?index=0
            //http://127.0.0.1:8090/app?index=20
            Request requset=new Request.Builder().get().
                    url(ConstantUtil.BASEURL+url+"?index="+index).build();
            Response execute = okHttpClient.newCall(requset).execute();//执行请求的发送
            String json = execute.body().string();//获取从服务器得到的json字符串
            if(!TextUtils.isEmpty(json)){
                //如果获取到的结果不为空,则说明取到数据,将数据缓存到本地
                writeTolocal(json,url,index);
                return json;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String InternetData(String url, int index,String params){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            //如果还需要传递参数,则需要将参数拼接在index后面形式如下"&key=value&key1=value1"
            //http://127.0.0.1:8090/home?index=0
            //http://127.0.0.1:8090/app?index=20
            Request requset=new Request.Builder().get().
                    url(ConstantUtil.BASEURL+ url + "?"+params).build();
            Response execute = okHttpClient.newCall(requset).execute();//执行请求的发送
            String json = execute.body().string();//获取从服务器得到的json字符串
            if(!TextUtils.isEmpty(json)){
                //如果获取到的结果不为空,则说明取到数据,将数据缓存到本地
                writeTolocal(json,url,index);
                return json;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*从本地获取文件时,需要和当前时间与文件时间戳进行对比
    * 如果超过有效时间,则从网络获取文件,否则直接从本地读取*/
    private static String LocalData(String url,int index){
        File file = new File(Utils.getContext().getCacheDir(),url+index);
        FileReader filereader= null;
        BufferedReader bufferedreader=null;
        try {
            filereader = new FileReader(file.getAbsolutePath());
            bufferedreader = new BufferedReader(filereader);
            long invalidtime = Long.parseLong(bufferedreader.readLine());//读取文件第一行,获取时间戳
            if(invalidtime>System.currentTimeMillis()){
                //此时文件有效
                String tempContent;
                //需要拼接读取到的每一行的内容
                StringBuffer stringbuffer = new StringBuffer();
                while((tempContent=bufferedreader.readLine())!=null){
                    stringbuffer.append(tempContent);//将每次读到的字符创进行拼接,直到读完
                }
                return stringbuffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (filereader != null && bufferedreader != null) {
                    filereader.close();
                    bufferedreader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }private static String LocalData(String url,String params){
        File file = new File(Utils.getContext().getCacheDir(),url+params);
        FileReader filereader= null;
        BufferedReader bufferedreader=null;
        try {
            filereader = new FileReader(file.getAbsolutePath());
            bufferedreader = new BufferedReader(filereader);
            long invalidtime = Long.parseLong(bufferedreader.readLine());//读取文件第一行,获取时间戳
            if(invalidtime>System.currentTimeMillis()){
                //此时文件有效
                String tempContent;
                //需要拼接读取到的每一行的内容
                StringBuffer stringbuffer = new StringBuffer();
                while((tempContent=bufferedreader.readLine())!=null){
                    stringbuffer.append(tempContent);//将每次读到的字符创进行拼接,直到读完
                }
                return stringbuffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (filereader != null && bufferedreader != null) {
                    filereader.close();
                    bufferedreader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /*将数据缓存到本地的方法
    * 1.这个方法传递三个参数,json串,页面url,数据范围
    * 2.在写入文件时在文件第一行加入时间戳,判断文件爱你是否失效
    * 3.文件写入后关闭
    * */
    private static void writeTolocal(String json, String url, int index) {
        //创建文件路径,data目录下cache文件,命名规则是url地址加范围
        File file= new File(Utils.getContext().getCacheDir(),url+index);
        //第一行写入此文件的有效时间戳
        long currenttime = System.currentTimeMillis();
        long invalidtime = System.currentTimeMillis() + ConstantUtil.FILETIMEOUT;//这是此文件的有效时间
        FileWriter filewrite=null;
        BufferedWriter bufferedwrite = null;
        try {
            filewrite = new FileWriter(file.getAbsolutePath());
            bufferedwrite = new BufferedWriter(filewrite);
            //写入第一个行有效时间,并且换行
            bufferedwrite.write(invalidtime+"\r\n");
            //写入json字符传
            bufferedwrite.write(json.toCharArray());
            bufferedwrite.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
            if(bufferedwrite!=null&&filewrite!=null){
                    bufferedwrite.close();
                    filewrite.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }private static void writeTolocal(String json, String url, String params) {
        //创建文件路径,data目录下cache文件,命名规则是url地址加范围
        File file= new File(Utils.getContext().getCacheDir(),url+params);
        //第一行写入此文件的有效时间戳
        long currenttime = System.currentTimeMillis();
        long invalidtime = System.currentTimeMillis() + ConstantUtil.FILETIMEOUT;//这是此文件的有效时间
        FileWriter filewrite=null;
        BufferedWriter bufferedwrite = null;
        try {
            filewrite = new FileWriter(file.getAbsolutePath());
            bufferedwrite = new BufferedWriter(filewrite);
            //写入第一个行有效时间,并且换行
            bufferedwrite.write(invalidtime+"\r\n");
            //写入json字符传
            bufferedwrite.write(json.toCharArray());
            bufferedwrite.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedwrite!=null&&filewrite!=null){
                    bufferedwrite.close();
                    filewrite.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
