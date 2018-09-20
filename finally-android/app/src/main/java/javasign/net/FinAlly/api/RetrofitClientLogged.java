package javasign.net.FinAlly.api;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javasign.net.FinAlly.BuildConfig;
import javasign.net.FinAlly.storage.SharedPrefManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientLogged {

    private SharedPrefManager session;

    private static final String BASE_URL = "https://develop.finfini.com/api/finally/";
    private static RetrofitClientLogged mInstance;
    private Retrofit retrofit;

    private RetrofitClientLogged(Context context) {
        session = SharedPrefManager.getInstance(context);
        HttpLoggingInterceptor interceptorLogger = new HttpLoggingInterceptor();
        interceptorLogger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .writeTimeout(50, TimeUnit.SECONDS)
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("apikey", "NmVlNjRlYzk4MWYzMTk3YmVlOTZkZDUxN2ZlNTViMGM=")
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .addHeader("accesstoken", session.getUser().getAccess_token())
                                .build();
                        return chain.proceed(request);
                    }
                });
        if (BuildConfig.DEBUG) {
            client.addInterceptor(interceptorLogger);
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static synchronized RetrofitClientLogged getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RetrofitClientLogged(context);
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
