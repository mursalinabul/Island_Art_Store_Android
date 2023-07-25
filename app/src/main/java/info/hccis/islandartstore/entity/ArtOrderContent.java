package info.hccis.islandartstore.entity;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

import info.hccis.islandartstore.dao.MyAppDatabase;

/**
 * This class will be used to access the business data stored on the local device database.
 * @since 20230209
 * @author BJM
 * modified by Mursalin
 */
public class ArtOrderContent {

    //Room Database attribute
    private static MyAppDatabase myAppDatabase;

    /**
     * Provide a getter for the database to be used throughout the app.
     * @since 20230209
     * @author BJM
     */

    public static MyAppDatabase getMyAppDatabase() {
        return myAppDatabase;
    }

    /**
     * This method will setup the database attribute to be used in this ap
     * @since 20230209
     * @author BJM
     * @param context
     */
    public static void setMyAppDatabase(Context context){
        //****************************************************************************************
        //Set the database attribute (Room database)
        //****************************************************************************************
        myAppDatabase = Room.databaseBuilder(context, MyAppDatabase.class, "performancehalldb").allowMainThreadQueries().build();

    }

    /**
     * This method will take the list passed in and reload the room database
     * based on the items in the list.
     * @param artOrders
     * @since 20220210
     * @author BJM
     * modified by Mursalin
     */
    public static void reloadArtOrdersInRoom(List<ArtOrder> artOrders)
    {
        //Delete all entries
        getMyAppDatabase().artOrderDAO().deleteAll();

        //Loop through and insert each entry.
        for(ArtOrder current : artOrders)
        {
            getMyAppDatabase().artOrderDAO().insert(current);
        }

        Log.d("BJM Room","loading ticket orders into Room ("+artOrders.size()+" loadeed");

    }


    /**
     * This method will obtain all the ticket orders out of the Room database.
     * @return list of ticket orders
     * @since 20220210
     * @author BJM
     * modified by Mursalin
     */
    public static List<ArtOrder> getArtOrdersFromRoom()
    {
        Log.d("BJM Room","Loading ticket orders from Room");

        List<ArtOrder> artOrdersBack = getMyAppDatabase().artOrderDAO().selectAllArtOrders();
        Log.d("BJM Room","Number of ticket orders loaded from Room: " + artOrdersBack.size());
        for(ArtOrder current : artOrdersBack)
        {
            Log.d("BJM Room",current.toString());
        }
        return artOrdersBack;
    }

}