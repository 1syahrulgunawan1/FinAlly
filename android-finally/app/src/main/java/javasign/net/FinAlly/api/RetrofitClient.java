package javasign.net.FinAlly.api;

import java.io.IOException;

import javasign.net.FinAlly.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://develop.finfini.com/api/finally/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        HttpLoggingInterceptor interceptorLogger = new HttpLoggingInterceptor();
        interceptorLogger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().
                                addHeader("apikey", "NmVlNjRlYzk4MWYzMTk3YmVlOTZkZDUxN2ZlNTViMGM=")
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
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

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
