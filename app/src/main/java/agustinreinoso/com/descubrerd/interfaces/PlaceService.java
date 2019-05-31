package agustinreinoso.com.descubrerd.interfaces;

import agustinreinoso.com.descubrerd.models.UserPlace;
import android.arch.lifecycle.LiveData;

import java.util.List;

public interface PlaceService {


    public long  insert(UserPlace place);

    public  int delete(UserPlace place);

    LiveData<List<UserPlace>> getPlacesSave();

}
