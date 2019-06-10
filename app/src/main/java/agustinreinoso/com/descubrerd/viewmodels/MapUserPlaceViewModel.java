package agustinreinoso.com.descubrerd.viewmodels;

import agustinreinoso.com.descubrerd.interfaces.PlaceService;
import agustinreinoso.com.descubrerd.models.UserPlace;
import agustinreinoso.com.descubrerd.services.PlaceDatabaseService;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class MapUserPlaceViewModel extends AndroidViewModel {
    private MutableLiveData<UserPlace> currentPlace;
    private PlaceService placeService;

    public PlaceService getPlaceService() {
        return placeService;
    }

    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }


    public void setIsSaving(MutableLiveData<Boolean> isSaving) {
        this.isSaving = isSaving;
    }


    public MutableLiveData<Boolean> getIsSaving() {
        if (isSaving == null) {
            isSaving = new MutableLiveData<>();
            isSaving.setValue(false);
        }
        return isSaving;
    }

    private MutableLiveData<Boolean> isSaving;

    public MapUserPlaceViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<UserPlace> getCurrentPlace() {

        if (currentPlace == null) {
            currentPlace = new MutableLiveData<>();
        }
        return currentPlace;
    }

    public void setCurrentPlace(MutableLiveData<UserPlace> currentPlace) {

        this.currentPlace = currentPlace;
    }

    public void savePlaceToFavorite(final UserPlace place) {
        if (!place.isSaved()) {
            if (placeService == null) {
                placeService = new PlaceDatabaseService(getApplication());
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isSaving.postValue(true);
                    long id=placeService.insert(place);
                    place.setSaved(true);

                    isSaving.postValue(false);
                }
            }).start();
        }

    }

    public void changeSavingStatus() {
        isSaving.setValue(!isSaving.getValue());
    }
}
