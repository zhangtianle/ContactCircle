package com.cqupt.tool;

import com.cqupt.listener.LoginStateListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;

/**
 * Created by ls on 15-4-19.
 */
public class HttpHandlerUtils {
    private static final HttpHandlerUtils httpHandlerUtils = new HttpHandlerUtils();


    private LoginStateListener loginStateListener;

    private HttpHandlerUtils() {

    }

    public static HttpHandlerUtils getInstance() {
        return httpHandlerUtils;
    }


    public void downLoad(String url) {
        //   RequestParams params = new RequestParams();
        //   params.addQueryStringParameter("method", "mkdir");
//        // params.addQueryStringParameter("access_token", "3.1042851f652496c9362b1cd77d4f849b.2592000.1377530363.3590808424-248414");
        //     params.addBodyParameter("path", "/apps/测试应用/test文件夹");
//
//        HttpUtils http = new HttpUtils();
//        http.send(HttpRequest.HttpMethod.GET,
//                url,
//                params,
//                new RequestCallBack<String>() {
//                    @Override
//                    public void onSuccess(ResponseInfo<String> stringResponseInfo) {
//                        System.out.println("下载成功" + stringResponseInfo);
//                    }
//
//                    @Override
//                    public void onFailure(HttpException e, String s) {
//                        System.out.println("下载失败");
//                    }
//                }
//
//        );

//
//        HttpUtils http = new HttpUtils();
//        http.configCurrentHttpCacheExpiry(1000 * 10);
//        http.send(HttpRequest.HttpMethod.GET,
//                url,
//                new RequestCallBack<String>() {
//
//                    @Override
//                    public void onStart() {
//                       // resultText.setText("conn...");
//                    }
//
//                    @Override
//                    public void onLoading(long total, long current, boolean isUploading) {
//                        System.out.println("下载:"+ current +"/"+total);
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        System.out.println("下载成功" + responseInfo.result);
//                    }
//
//
//                    @Override
//                    public void onFailure(HttpException error, String msg) {
//                        System.out.println("下载成功" + msg);
//                    }
//                });

        HttpHandler handler;
        // 1.创建下载器
        HttpUtils http = new HttpUtils();
        // 2.最大开启线程数量
        http.configRequestThreadPoolSize(4);
        handler = http.download(
                url,                // 1）源文件地址
                Utils.getAlbumStorageDir("download.apk").getAbsolutePath(),    // 2）下载保存地址
                true,                // 3）如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true,                // 4）如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {    // 5）回调函数，处理整个下载事件

                    // （1）事件：开始下载
                    public void onStart() {
                        // 交由主线程处理
                        System.out.println("conn...");
                    }

                    // （2）事件：下载中
                    public void onLoading(long total, long current, boolean isUploading) {
                        System.out.println(current + "/" + total);
                    }

                    // （3）事件：下载成功
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        System.out.println("下载成功" + responseInfo.reasonPhrase);
                        //     tv_info.setText("downloaded:" + responseInfo.result.getPath());
                    }

                    // （4）事件：下载失败
                    public void onFailure(com.lidroid.xutils.exception.HttpException error, String msg) {
                        //    tv_info.setText(msg);
                        System.out.println("下载失败" + error);
                    }
                });

    }


    public void postInfor(String url, String inforType, String infor) {
        RequestParams params = new RequestParams();
        //params.addHeader("name", "value");
        //   params.addQueryStringParameter("type", "register");
        //  params.addQueryStringParameter("registerInfor", "lishuang");
// 只包含字符串参数时默认使用BodyParamsEntity，
// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("registerInfor", infor);
// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
        //      params.addBodyParameter("file", new File("path"));


        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        LogUtils.e("onStart()   ");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        if (isUploading) {
                            //     testTextView.setText("upload: " + current + "/" + total);
                        } else {
                            //  testTextView.setText("reply: " + current + "/" + total);
                        }
                        //  System.out.println("onLoading");
                        LogUtils.e("onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        //testTextView.setText("reply: " + responseInfo.result);
                        LogUtils.e("onSuccess()   " + responseInfo.result);
                        // System.out.println("onSuccess   " + responseInfo.result);
                        if (loginStateListener != null) {
                            loginStateListener.loginState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        //testTextView.setText(error.getExceptionCode() + ":" + msg);
                        // System.out.println("onFailure" + msg);
                        LogUtils.e("onFailure()   " + msg);
                    }
                });


    }


    public void upLaod(String url, File file) {

        RequestParams params = new RequestParams();
        //   params.addHeader("type", "upload");
        //   params.addQueryStringParameter("type", "register");
        //  params.addQueryStringParameter("registerInfor", "lishuang");
// 只包含字符串参数时默认使用BodyParamsEntity，
// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        params.addBodyParameter("asdfg", "/888888");

// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
        params.addBodyParameter("file", file);


        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        System.out.println("上传onStart");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        if (isUploading) {
                            //     testTextView.setText("upload: " + current + "/" + total);
                        } else {
                            //  testTextView.setText("reply: " + current + "/" + total);
                        }
                        System.out.println("上传onLoading");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        //testTextView.setText("reply: " + responseInfo.result);
                        System.out.println("上传onSuccess   " + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        //testTextView.setText(error.getExceptionCode() + ":" + msg);
                        System.out.println("上传onFailure" + msg);
                    }
                });
    }

    public LoginStateListener getLoginStateListener() {
        return loginStateListener;
    }

    public void setLoginStateListener(LoginStateListener loginStateListener) {
        this.loginStateListener = loginStateListener;
    }


//    public static void uploadFile(String path, String uploadUrl) {
//
//        //  String uploadUrl = "http://10.0.2.2:8080/upLoadFile/servlet/UpLoadFile";
//        String end = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "******";
//        try {
//            URL url = new URL(uploadUrl);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setUseCaches(false);
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
//            httpURLConnection.setRequestProperty("Charset", "UTF-8");
//            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
//            dos.writeBytes(twoHyphens + boundary + end);
//            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
//                    + path.substring(path.lastIndexOf("/") + 1) + "\"" + end);
//            dos.writeBytes(end);
//
//            FileInputStream fis = new FileInputStream(path);
//            byte[] buffer = new byte[8192]; // 8k
//            int count = 0;
//            while ((count = fis.read(buffer)) != -1) {
//                dos.write(buffer, 0, count);
//            }
//            Log.e("", dos.size() + "----------");
//            fis.close();
//
//            dos.writeBytes(end);
//            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
//            dos.flush();
//
//            InputStream is = httpURLConnection.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is, "utf-8");
//            BufferedReader br = new BufferedReader(isr);
//            String result = br.readLine();
//
//            //    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//            dos.close();
//            is.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // setTitle(e.getMessage());
//        }
//
//    }

}







