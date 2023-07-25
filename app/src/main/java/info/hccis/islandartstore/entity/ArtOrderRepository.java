package info.hccis.islandartstore.entity;

/*
 * This class will allow access to the api.
 *
 * @Author: BJM (Modified from Mariana Alkabalan (Flower Shop App)
 * @since 20220314
 * Reference: https://learntodroid.com/how-to-send-json-data-in-a-post-request-in-android/
 */


import info.hccis.islandartstore.api.ApiWatcher;
import info.hccis.islandartstore.api.JsonArtOrderApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ArtOrderRepository {

    private static ArtOrderRepository instance;

    private JsonArtOrderApi jsonArtOrderApi;

    /**
     * This method will return an instance of this repository.  It will create the instance and store
     * it in the instance attribute so it only has to be created once.
     * @since 20230209
     * @author BJM
     * @return The repository instance
     */
    public static ArtOrderRepository getInstance() {
        if (instance == null) {
            instance = new ArtOrderRepository();
        }
        return instance;
    }

    /**
     * This constructor will setup the Retrofit object which will allow users of this repository
     * to have access to the available api calls for this app.
     * @since 20230209
     * @author BJM
     * modified by Mursalin
     */
    public ArtOrderRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiWatcher.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonArtOrderApi = retrofit.create(JsonArtOrderApi.class);
    }

    public JsonArtOrderApi getArtOrderService() {
        return jsonArtOrderApi;
    }

}
