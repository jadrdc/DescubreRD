package  agustinreinoso.com.descubrerd.DAO.access;

import agustinreinoso.com.descubrerd.models.UserPlace;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PlaceDAO {

    @Insert
    public long  insert(UserPlace place);

    @Delete
    public  int delete(UserPlace place);

    @Query("Select * from places")
    LiveData<List<UserPlace>> getPlacesSave();


}
