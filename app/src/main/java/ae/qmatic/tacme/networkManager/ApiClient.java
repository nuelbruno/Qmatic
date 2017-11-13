package ae.qmatic.tacme.networkManager;

import ae.qmatic.tacme.utils.HttpConstants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mdev3 on 8/16/16.
 */
public class ApiClient {
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
             // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …
            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!
            retrofit = new Retrofit.Builder()
                    .baseUrl(HttpConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
