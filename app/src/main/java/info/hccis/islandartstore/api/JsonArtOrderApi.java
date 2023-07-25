package info.hccis.islandartstore.api;

import java.util.List;

import info.hccis.islandartstore.entity.ArtOrder;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonArtOrderApi {

    /**
     * This abstract method to be created to allow retrofit to get list of ticket orders
     * @return List of ticket orders
     * @since 20220202
     * @author BJM (with help from the retrofit research!).
     * modified by Mursalin
     */

    @GET("artOrders")
    Call<List<ArtOrder>> getArtOrders();
    @POST("artOrders")
    Call<ArtOrder> createArtOrder(@Body ArtOrder artOrder);

}