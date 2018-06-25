package com.example.baymax.func;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.text.Collator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.baidu.api.TransApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.zip.CheckedOutputStream;

/**
 * Created by baymax on 18-6-15.
 */

public class ToolFunc {
    private Context context;

    private PackageManager packageManager; //?????? 可以通过context获得this.context.getPackageManager();

    private List<PackageInfo> allPackages;
    public ToolFunc(Context context, final PackageManager packageManager){
        this.context = context;
        this.packageManager = packageManager;
        this.allPackages = packageManager.getInstalledPackages(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Arrays.sort(new List[]{this.allPackages}, Collator.getInstance(Locale.CHINA));
        }else {
            Collections.sort(this.allPackages, new Comparator<PackageInfo>() {
                @Override
                public int compare(PackageInfo packageInfo, PackageInfo t1) {
                    return packageInfo.applicationInfo.loadLabel(packageManager).toString().length()- t1.applicationInfo.loadLabel(packageManager).toString().length();
                }
            });
        }
    }

    //返回第三方应用的json数据，path:源地址 Name：名称 packagename:包名称（用于卸载）
    @JavascriptInterface
    public String apkJson() {

        JSONArray jsonArray = new JSONArray();
        for(int i = 0;i<this.allPackages.size();i++){
            PackageInfo packageInfo = this.allPackages.get(i);
            JSONObject jo = new JSONObject();
            String path = packageInfo.applicationInfo.sourceDir;
            String name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            String packageName = packageInfo.applicationInfo.packageName;
            if(!((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 && (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0 )){
                try {
                    jo.put("path",path);
                    jo.put("name",name);
                    jo.put("packagename",packageName);
                    jsonArray.put(jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArray.toString();
    }
    // 返回系统应用
    @JavascriptInterface
    public String apkSystem() {
        JSONArray jsonArray = new JSONArray();
        for(int i = 0;i<this.allPackages.size();i++){
            PackageInfo packageInfo = this.allPackages.get(i);
            JSONObject jo = new JSONObject();
            String path = packageInfo.applicationInfo.sourceDir;
            String name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            String packageName = packageInfo.applicationInfo.packageName;
            if(((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 && (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0 )){
                try {
                    jo.put("path",path);
                    jo.put("name",name);
                    jo.put("packagename",packageName);
                    jsonArray.put(jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArray.toString();
    }
    //弹窗
    @JavascriptInterface
    public void makeToast(String msg){
        Toast.makeText(this.context,msg,Toast.LENGTH_LONG).show();
    }

    // 应用卸载
    @JavascriptInterface
    public void apkDel(String msgJson){
        System.out.println(msgJson);
        try {
            JSONArray ja = new JSONArray(msgJson);
            for(int i = 0; i < ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                String packagename = jo.get("packagename").toString();
                //Toast.makeText(this.context,packagename,Toast.LENGTH_LONG);
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:"+packagename));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.context.startActivity(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // 应用备份
    @JavascriptInterface
    public boolean apkBackup(String apkname,String path){
        String baseDir = "/mnt/sdcard/BM0D/ExportApk/";
        try{
            File in = new File(path);
            File fileBase = new File(baseDir);
            if(!fileBase.exists()){
                fileBase.mkdirs();
            }

            File out = new File(baseDir +apkname+ ".apk");
            if(!out.exists()){out.createNewFile();}
            FileInputStream fis = new FileInputStream(in);
            FileOutputStream fos = new FileOutputStream(out);
            int count;
            byte[] buffer = new byte[256 * 1024];
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fis.close();
            fos.flush();
            fos.close();
            return true;
        }catch (Exception e){
            System.out.println(e.toString());
            return  false;
        }
    }
    //编码转换,返回转换好的编码
    @JavascriptInterface
    public String reCoding(int id,String code){
        String res = "";
        char[] chars = code.toCharArray();
        switch (id){
            case 0:
                return code;
            case 1:

                for (int i = 0; i < chars.length; i++) {
                    res +=  "\\u" + Integer.toString(chars[i], 16);
                }
                return res;
            case 2:
                String[] strs = code.split("\\\\u");
                for (int i = 1; i < strs.length; i++) {
                    res += (char) Integer.valueOf(strs[i], 16).intValue();
                }
                return res;
            case 3:
                String[] str3 = code.split("0x");
                for (int i = 1; i < str3.length; i++) {
                    res += (char)Integer.parseInt(str3[i],16);
                }
                return res;
            case 4:
                for (int i = 0; i < chars.length; i++) {
                    res += "0x" + Integer.toHexString((int)chars[i]);
                }
                return res;
            default:
                return code;
        }
    }

    //wifi密码查看
    @JavascriptInterface
    public String wifiPass(){
        try {
            Runtime.getRuntime().exec("su");
            return "123456789";
        } catch (IOException e) {
            return "没有root权限";
        }
    }

    //百度翻译
    @JavascriptInterface
    public String baidufanyi(String lan,String query){
        String APP_ID = "20180617000177423";
        String SECURITY_KEY = "qlUZzvnorrbb_dzACbXl";
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        return api.getTransResult(query, "auto", lan);
    }

    @SuppressLint("MissingPermission")
    @JavascriptInterface
    public String sysInfo(){

        TelephonyManager phone = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("devceid","序列号IMEI："+phone.getDeviceId().toString());
            jo.put("release","设备唯一标识："+ Build.FINGERPRINT);
            jo.put("Number","手机号码：" + (phone.getLine1Number() == null ? phone.getLine1Number() : "未知") );
            jo.put("macAddress","Mac地址："+ phone.getNetworkOperatorName());

            String str1 = "/proc/cpuinfo";
            String str2 = "";
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2=localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    str2 =  str2.split(":")[1] == null ? str2.split(":")[1] :"未知";
                }else {
                    str2 = "未知";
                }
            }
            localBufferedReader.close();
            jo.put("cpuinfo","CPU型号："+str2);
            jo.put("release","系统版本："+ Build.VERSION.RELEASE);
            jo.put("API","API级别："+ Build.VERSION.SDK_INT);
            jo.put("BOARD","设备基板名称："+ Build.BOARD);
            jo.put("Build","Build时间："+ Build.TIME);
            jo.put("DEVICE","设备驱动名称："+ Build.DEVICE);
            jo.put("MODEL","手机型号："+Build.MODEL);
            jo.put("PRODUCE","产品名："+Build.PRODUCT);
            System.out.println(jo.toString());
            return jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    // 短连接生成
    @JavascriptInterface
    public String getShortUrl(String url){
        if(url.indexOf("http") == -1){
            url = "http://" + url;
        }
        try {
            URL realUrl = new URL("http://api.t.sina.com.cn/short_url/shorten.json?source=2815391962&url_long=" + url);
            URLConnection conn = realUrl.openConnection();

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1) "
                    + "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) "
                    + "Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = "";
            String res = "";
            while ((line=in.readLine())!=null){
                res += line;
            }
            System.out.println(res.indexOf("http"));

            return res;

            //String res = "{\"url_short\":\"http://t.cn/RBedtgU\",\"url_long\":\"http://www.baymax0ady.com\",\"type\":0}";

        } catch (Exception e) {
            return "不可解析";
        }
    }
    @JavascriptInterface
    public  void sendQQ(String length){
        String str  = "\n";
        for (int i = 0; i < Integer.valueOf(length); i++) {
            str += "\n";
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, str);
        sendIntent.setType("text/plain");
        try {
            sendIntent.setClassName("com.tencent.mobileqq",
                    "com.tencent.mobileqq.activity.JumpActivity");
//            Intent chooserIntent = Intent.createChooser(sendIntent, "选择分享途径");
//            if (chooserIntent == null) {
//                return;
//            }
//            System.out.println(chooserIntent.toString());
//            System.out.println(chooserIntent.toURI());
//            Log.d("intent", "sendQQ: "+chooserIntent.toString());
//            context.startActivity(chooserIntent);
            context.startActivity(sendIntent);
        } catch (Exception e) {
            context.startActivity(sendIntent);
        }
    }

    //支付宝捐赠
    @JavascriptInterface
    public void alipayDonate(){
        Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/c1x09783vkgosbej8lwnsba");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        context.startActivity(intent);
    }


    // 支付宝扫一扫
    @JavascriptInterface
    public void alipayScan(){
        try {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            context.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(context, "无法跳转到支付宝，请检查是否安装了支付宝", Toast.LENGTH_SHORT).show();
        }

    }

    // 微信扫一扫
    @SuppressLint("WrongConstant")
    @JavascriptInterface
    public void wechatScan(){
        try {
// 早期 的微信可以通过Uri uri = Uri.parse("weixin://dl/scan");打开微信扫一扫，but现在的没有了，只能通过获取包名，传入intent打开微信扫一扫
//            Uri uri = Uri.parse("weixin://");
//            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//            context.startActivity(intent);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intent.setFlags(335544320);
            intent.setAction("android.intent.action.VIEW");
            context.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(context, "无法跳转到微信，请检查是否安装了微信", Toast.LENGTH_SHORT).show();
        }

    }

    // 通过qq联系我
    @JavascriptInterface
    public void contactQQ(){
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=919270685&version=1";
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(context,"本机未安装QQ",Toast.LENGTH_SHORT).show();
        }
    }

}
































