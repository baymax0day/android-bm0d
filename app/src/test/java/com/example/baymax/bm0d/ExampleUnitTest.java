package com.example.baymax.bm0d;

import android.util.Base64;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Random;
import java.util.UUID;


import com.baidu.api.*;

import org.apache.http.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        //assertEquals(4, 2 + 2);
    }

    @Test
    public void recoding() {
        // 编码解码
//        System.out.println("=============");
//        String s = "我爱你";
//        String res = "";
//        char[] arr = s.toCharArray();
//        for(int i = 0 ; i< arr.length;i++){
//            System.out.println((int)arr[i]);
//        }
//        System.out.println(s.toCharArray().length);
//        String s = "asd";
//        char[] chars = s.toCharArray();
//
//        for (int i = 0; i < chars.length; i++) {
//            System.out.println(String.valueOf(chars[i]));
//            String resInt = Integer.toHexString((int)chars[i]);
//            String res ="";
//            Integer x = 666;
//
//            System.out.println();
//            //System.out.println(Integer.valueOf(String.valueOf(chars[i]), 16).intValue());
//            //res += "0x" + (char) Integer.valueOf(String.valueOf(chars[i]), 16).intValue();
//            //res = s+"";
//        }

//        String str = "0x650x640x63";
//        String[] strs = str.split("0x");
//
//        for (int i = 1; i < strs.length; i++) {
//
//            System.out.println((char)Integer.parseInt(strs[i],16));
//        }

        // 百度翻译API
        // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
//        String APP_ID = "20180617000177423";
//        String SECURITY_KEY = "qlUZzvnorrbb_dzACbXl";
//
//
//        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
//
//        String query = "高度600米";
//        String res = api.getTransResult(query, "auto", "zh");
//        System.out.println(res);



        //System.out.println();
//        System.out.println(r.nextInt(4));
        try {
//            URL realUrl = new URL("http://api.t.sina.com.cn/short_url/shorten.json?source=2815391962&url_long=http://www.baymax0ady.com");
//            URLConnection conn = realUrl.openConnection();
//
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1) "
//                    + "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) "
//                    + "Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10");
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//
////            PrintWriter out = new PrintWriter(conn.getOutputStream());
////            out.print();
//
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
//            String line = "";
//            String res = "";
//            while ((line=in.readLine())!=null){
//                res += line;
//            }
//            System.out.println(res);
//            String res = "{\"url_short\":\"http://t.cn/RBedtgU\",\"url_long\":\"http://www.baymax0ady.com\",\"type\":0}";
//
//            String json = "{\"name\": \"jadyli\", \"gender\": \"male\", \"age\": 18}";
//
//            JSONArray jsonArray = new JSONArray(res);
//            JSONObject jo = new JSONObject(json);
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//            System.out.println(jo.getString("name"));
//            System.out.println(jsonObject.get("url_long"));


            String json = "{\"name\": \"jadyli\", \"gender\": \"male\", \"age\": 18}";
            JSONObject jsonObject = new JSONObject(json);
            System.out.println("姓名：" + jsonObject.getString("name"));
            System.out.println("性别：" + jsonObject.getString("gender"));
            System.out.println("年龄：" + jsonObject.getString("age"));

        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        String s = "http://www.baymax0day.com";
////        String s1 = android.util.Base64.encodeToString(s.getBytes(),Base64.DEFAULT);
////
////        String str = "Hello!";
////        // 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
////        String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
////        System.out.println( strBase64);
//
//        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
//        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
//
//        String str = encoder.encodeToString(s.getBytes());
//        System.out.println(str);
    }

}

