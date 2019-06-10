package agustinreinoso.com.descubrerd.viewmodels;

import agustinreinoso.com.descubrerd.interfaces.PlaceService;
import agustinreinoso.com.descubrerd.models.UserPlace;
import agustinreinoso.com.descubrerd.services.PlaceDatabaseService;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class UserPlaceListViewModel extends AndroidViewModel {

    private LiveData<List<UserPlace>> userPlaceslist;
    private PlaceService service;
    private MutableLiveData<Boolean> isLoading;

    public MutableLiveData<Boolean> getIsLoading() {

        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
            isLoading.setValue(true);
        }
        return isLoading;
    }

    public void setIsLoading(MutableLiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    public PlaceService getService() {

        return service;
    }

    public void setService(PlaceService service) {
        this.service = service;
    }

    public UserPlaceListViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<UserPlace>> getUserPlaceslist() {
        /*if (userPlaceslist == null) {
            userPlaceslist = new MutableLiveData<>();
            service = new PlaceDatabaseService(getApplication());

        }*/
        if (service == null) {
            service = new PlaceDatabaseService(getApplication());
        }

        userPlaceslist = service.getPlacesSave();
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<UserPlace> usersplaces = service.getPlacesSave().getValue();
                if (usersplaces == null) {
                    userPlaceslist.setValue(new ArrayList<UserPlace>());
                }
            }
        }).start();
*/
        return userPlaceslist;
    }

    public void setUserPlaceslist(MutableLiveData<List<UserPlace>> userPlaceslist) {
        this.userPlaceslist = userPlaceslist;
    }


}

