package cn.incongress.endorcrinemagazine.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 25/10/2016.
 */

public class HttpUtils {
    private static URL url;
    /* Function  :   发送Post请求到服务器
    * Param     :   params请求体内容，encode编码格式
    * Author    :   博客园-依旧淡然
    */
    public static String submitPostData(String url1, Map<String, String> params, String encode) {

        try {
            url = new URL(url1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(10000);           //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");          //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            //httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();

            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            Log.e("GYW","-----响应码"+response);
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String submitGetData(String url,String encode) {

        try {

            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                return dealResponseResult(urlConnection.getInputStream());
            }
            } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
 2      * Function  :   封装请求体信息
 3      * Param     :   params请求体内容，encode编码格式
 4      * Author    :   博客园-依旧淡然
 5      */
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
          StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
                 for(Map.Entry<String, String> entry : params.entrySet()) {
                      stringBuffer.append(entry.getKey())
                                         .append("=")
                                        .append(URLEncoder.encode(entry.getValue(),encode))
                                      .append("&");
                      }
                   stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
                } catch (Exception e) {
                    e.printStackTrace();
            }
             return stringBuffer;
            }

    /*
     * Function  :   处理服务器的响应结果（将输入流转化成字符串）
     * Param     :   inputStream服务器的响应输入流
     * Author    :   博客园-依旧淡然
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
            resultData = new String(byteArrayOutputStream.toByteArray(),"GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultData;
    }
}
