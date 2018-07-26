package com.example.weihong.practice;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Weihong on 2018-06-17.
 */

public class HtmlService {
    private static final String TAG = "HTML SERVICE";
    public void getHtml(String site, Handler mainHandler){
        Log.d(HtmlService.TAG, site);

        try{

            URL url= new URL(site);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(500);
            InputStream inStream=conn.getInputStream();
            String html=readInputSteam(inStream);
            Message msg=mainHandler.obtainMessage();
            msg.obj=html;
            mainHandler.sendMessage(msg);
        }
        catch (Exception e){
            Log.e(HtmlService.TAG, e.toString());

        }

//            byte[] data=readInputSteam(inStream);
//            String html=new String(data,"utf-8");





    }

//    public static byte[] readInputSteam(InputStream inStream) throws Exception{
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        byte[] buffer=new byte[1024];
//        int len=0;
//        while((len=inStream.read(buffer))!=-1){
//            outStream.write(buffer,0,len);
//        }
//        inStream.close();
//        return outStream.toByteArray();
//    }

    public String readInputSteam(InputStream inStream) throws Exception{
        BufferedReader in=new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sb = new StringBuilder();

        String current;
        while ((current = in.readLine()) != null){
            sb.append(current);
        }
        String urlString=sb.toString();

        in.close();
        Log.d("HTML SERVICE", "read input stream::"+urlString);
        return urlString;
    }
}
