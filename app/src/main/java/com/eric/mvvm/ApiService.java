package com.eric.mvvm;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ApiService {

    @GET("/api/login")
    Observable<ResponseBody> login(@Query("worker_id") String worker_id, @Query("mac_address") String mac_address);

    @GET("/AndroidAPK/AndroidUpdate.json")
    Observable<ResponseBody> checkUpdate();

    @GET("/AndroidAPK/{apkName}")
    @Streaming
    Observable<ResponseBody> downloadApk(@Path("apkName") String apkName);

}
