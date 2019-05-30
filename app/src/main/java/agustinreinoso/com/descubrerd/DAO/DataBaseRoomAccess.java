package agustinreinoso.com.descubrerd.DAO;

import agustinreinoso.com.descubrerd.DAO.access.PlaceDAO;
import agustinreinoso.com.descubrerd.models.UserPlace;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {UserPlace.class}, version = 1, exportSchema = false)
public abstract class DataBaseRoomAccess extends RoomDatabase {
    public abstract PlaceDAO getPlaceDAO();
}
