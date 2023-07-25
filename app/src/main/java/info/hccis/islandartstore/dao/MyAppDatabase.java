package info.hccis.islandartstore.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import info.hccis.islandartstore.entity.ArtOrder;

@Database(entities = {ArtOrder.class},version = 2, exportSchema = true)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract ArtOrderDAO artOrderDAO();
}

