package com.cqupt.tool;

import android.app.Activity;

import com.cqupt.app.App;
import com.cqupt.listener.HttpStateListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.util.List;

/**
 * Created by ls on 15-4-19.
 */
public class HttpHandlerUtils {


    private HttpStateListener httpStateListener;

    public HttpHandlerUtils() {

    }


    public void downLoad(String url) {
        //   RequestParams params = new RequestParams();
        //   params.addQueryStringParameter("method", "mkdir");
//        // params.addQueryStringParameter("access_token", "3.1042851f652496c9362b1cd77d4f849b.2592000.1377530363.3590808424-248414");
        //     params.addBodyParameter("path", "/apps/测试应用/test文件夹");
//
//        HttpUtils http = new HttpUtils();
//        http.send(HttpRequest.HttpMethod.GET,
//                downLoadURL,
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
//                downLoadURL,
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


    public void postLoginOrRegisterInfor(String url, String inforType, String infor) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("registerInfor", infor);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        LogUtils.e("注册登陆onStart()   ");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        LogUtils.e("注册登陆onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("注册登陆onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.postState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("注册登陆onFailure()   " + msg);
                    }
                });
    }

    public void postPraise(String url, String inforType, String uuid) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("articleuuid", uuid);
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
                        LogUtils.e("赞onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("赞onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.postState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("赞onFailure()   " + msg);
                    }
                });
    }

    public void sendArticleInfor(String url, String infor) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", "uploadArticle");
        params.addBodyParameter("article", infor);
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
                        LogUtils.e("发送文章onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("发送文章onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.refreshArticleState("success");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("发送文章onFailure()   ");
                        if (httpStateListener != null) {
                            httpStateListener.refreshArticleState("failure");
                        }

                    }
                });
    }


    public void upLoad(final Activity activity, String url, List<File> files, String article) {

        RequestParams params = new RequestParams();
        params.addBodyParameter("article", article);
        if (files.size() == 0) {
            sendArticleInfor(App.downLoadURL, article);
            return;
        }
        for (int i = 0; i < files.size(); i++) {
            params.addBodyParameter("file:   " + files.get(i).getAbsolutePath(), files.get(i));
            LogUtils.e("   file   " + i);
        }
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
                        LogUtils.e("上传onLoading" + current + "/" + total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("上传onSuccess   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.refreshArticleState("success");
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("上传onFailure" + msg);
                        if (httpStateListener != null) {
                            httpStateListener.refreshArticleState("failure");
                        }
                    }
                });
    }

    public HttpStateListener getHttpStateListener() {
        return httpStateListener;
    }

    public void setHttpStateListener(HttpStateListener httpStateListener) {
        this.httpStateListener = httpStateListener;
    }


//    public static void uploadFile(String path, String uploadUrl) {
//
//        //  String uploadUrl = "http://10.0.2.2:8080/upLoadFile/servlet/UpLoadFile";
//        String end = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "******";
//        try {
//            URL downLoadURL = new URL(uploadUrl);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) downLoadURL.openConnection();
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


    public void postRefreshArticle(String url, String inforType, String userUUID, String circle, String page, String time) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("userUUID", userUUID);
        params.addBodyParameter("circle", circle);
        params.addBodyParameter("page", page);
        params.addBodyParameter("time", time);
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
                        LogUtils.e("请求文章onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("请求文章onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.refreshArticleState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("请求文章onFailure()   " + msg);
                    }
                });
    }


    public void postRefreshArticle(String url, String inforType, String articleUUID, String time) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("articleUUID", articleUUID);
        params.addBodyParameter("time", time);
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
                        LogUtils.e("请求刷新这个文章onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("请求刷新这个文章onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.refreshArticleState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("请求刷新这个文章onFailure()   " + msg);
                    }
                });
    }

    public void postComment(String url, String inforType, String articleUUID) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("comment", articleUUID);
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
                        LogUtils.e("回复onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("回复onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.postState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("回复onFailure()   " + msg);
                    }
                });
    }

    public void getUserArticleInfor(String url, String inforType, String userUUID) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("userUUID", userUUID);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        LogUtils.e("个人文章onStart()   ");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        LogUtils.e("个人文章onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("个人文章onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.refreshArticleState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("个人文章onFailure()   " + msg);
                    }
                });
    }

    public void searchCirclsInfor(String url, String inforType, String userUUID, String infor) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("userUUID", userUUID);
        params.addBodyParameter("infor", infor);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        LogUtils.e("获取的圈子名称onStart()   ");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        LogUtils.e("获取的圈子名称onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("获取的圈子名称onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.postState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("获取的圈子名称onFailure()   " + msg);
                    }
                });
    }

    public void follow(String url, String inforType, String followType, String followUUID, String userUUID) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", inforType);
        params.addBodyParameter("followType", followType);
        params.addBodyParameter("followUUID", followUUID);
        params.addBodyParameter("userUUID", userUUID);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        LogUtils.e("关注onStart()   ");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        LogUtils.e("关注onLoading()   ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("关注onSuccess()   " + responseInfo.result);
                        if (httpStateListener != null) {
                            httpStateListener.postState(responseInfo.result);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("关注onFailure()   " + msg);
                    }
                });
    }


}







