package com.eric.mvvm;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class NetworkApi {

    private static String BASE_URL = null;

    private static OkHttpClient okHttpClient;

    private static final HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();

    public static <T> T createService(Class<T> serviceClass) {
        BASE_URL = "http://" + BuildConfig.SERVER_URL+ ":3000";
        return getRetrofit(serviceClass).create(serviceClass);
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            int cacheSize = 100 * 1024 * 1024;
            builder.connectTimeout(6, TimeUnit.SECONDS);
            builder.addInterceptor(new ResponseInterceptor());
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(httpLoggingInterceptor);

            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    private static Retrofit getRetrofit(Class serviceClass) {
        if (retrofitHashMap.get(BASE_URL + serviceClass.getName()) != null) {
            return retrofitHashMap.get(BASE_URL + serviceClass.getName());
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.client(getOkHttpClient());
//        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        retrofitHashMap.put(BASE_URL + serviceClass.getName(), retrofit);
        return retrofit;
    }

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return upstream -> {
            Observable<T> observable = upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
//                    .map(NetworkApi.getAppErrorHandler())
//                    .onErrorResumeNext(new HttpErrorHandler<>());
            observable.subscribe(observer);
            return observable;
        };
    }

}
