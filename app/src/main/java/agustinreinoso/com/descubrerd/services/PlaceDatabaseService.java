package agustinreinoso.com.descubrerd.services;

import agustinreinoso.com.descubrerd.DAO.DataBaseRoomAccess;
import agustinreinoso.com.descubrerd.config.ConfigKey;
import agustinreinoso.com.descubrerd.interfaces.PlaceService;
import agustinreinoso.com.descubrerd.models.UserPlace;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

public class PlaceDatabaseService implements PlaceService {

    private DataBaseRoomAccess db;

    public PlaceDatabaseService(Context app)
    {
        db = Room.databaseBuilder(app, DataBaseRoomAccess.class, ConfigKey.DB_NAME).build();
    }

    @Override
    public long insert(UserPlace place)
    {
        return db.getPlaceDAO().insert(place);
    }

    @Override
    public int delete(UserPlace place)
    {
        return db.getPlaceDAO().delete(place);
    }

    @Override
    public LiveData<List<UserPlace>> getPlacesSave()
    {
        return db.getPlaceDAO().getPlacesSave();
    }
}
