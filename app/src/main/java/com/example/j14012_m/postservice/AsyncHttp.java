package com.example.j14012_m.postservice;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Created by j14012_m on 2017/02/10.
 */
public class AsyncHttp extends AsyncTask<String,Integer,Boolean>{
    HttpURLConnection urlConnection = null;
    Boolean flg = false;
    String name;
    String value;

    //コンストラクタ
    public AsyncHttp(String name, double lat, double lng){
        this.name = name;
        this.value = lat+","+lng;
    }

    @Override
    protected Boolean doInBackground(String... contents){
        String urlinput = "http://10.250.0.152:1080/upload/post.php";
        try {
            URL url = new URL(urlinput);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            //POST用パラメータ
            String postDataSample = "name=" + name + "&text=" + value;
            //POSTパラメータ
            OutputStream out = urlConnection.getOutputStream();
            out.write(postDataSample.getBytes());
            out.flush();
            out.close();
            Log.d("test", postDataSample);
            //レスポンスを受け取る
            urlConnection.getInputStream();

            flg = true;
        }catch (MalformedInputException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return flg;
    }
}
