package info.hccis.islandartstore.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import info.hccis.islandartstore.entity.ArtOrder;

/**
 * Interface which defines the methods available to interact with the database
 * @since 20230209
 * @author BJM
 * Modified by AFM Mursalin
 */
@Dao
public interface ArtOrderDAO {

    @Insert
    void insert(ArtOrder artOrder);

    @Query("SELECT * FROM ArtOrder")
    List<ArtOrder> selectAllArtOrders();

    @Query("delete from ArtOrder")
    public void deleteAll();

}
