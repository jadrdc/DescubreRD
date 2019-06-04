package agustinreinoso.com.descubrerd.viewmodels;

import agustinreinoso.com.descubrerd.models.UserPlace;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class PlaceViewModel extends ViewModel {
    private MutableLiveData<UserPlace> currentPlace;



    public MutableLiveData<UserPlace> getCurrentPlace()
    {

        if(currentPlace==null)
        {
            currentPlace=new MutableLiveData<>();
        }
        return currentPlace;
    }

    public void setCurrentPlace(MutableLiveData<UserPlace> currentPlace) {

        this.currentPlace = currentPlace;
    }
}
