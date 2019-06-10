package agustinreinoso.com.descubrerd.views;

import afu.org.checkerframework.checker.nullness.qual.Nullable;
import agustinreinoso.com.descubrerd.R;
import agustinreinoso.com.descubrerd.models.UserPlace;
import agustinreinoso.com.descubrerd.viewmodels.MapUserPlaceViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceFoundActionFragment extends BottomSheetDialogFragment {

    private MapUserPlaceViewModel placeViewModel;
    private TextView txtPlaceName;
    private TextView txtPlaceAddress;
    private ImageView imgPlaceIcon;
    private Button btnSavePlace;

    public PlaceFoundActionFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_place_found_action, container,
                false);

        placeViewModel = ViewModelProviders.of(getActivity()).get(MapUserPlaceViewModel.class);

        txtPlaceName = (TextView) view.findViewById(R.id.place_found_name);
        txtPlaceAddress = (TextView) view.findViewById(R.id.place_found_address);
        imgPlaceIcon = (ImageView) view.findViewById(R.id.place_found_img);
        btnSavePlace = (Button) view.findViewById(R.id.btn_place_save);
        btnSavePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeViewModel.savePlaceToFavorite(placeViewModel.getCurrentPlace().getValue());

            }
        });
        placeViewModel.getCurrentPlace().observe(this, new Observer<UserPlace>() {
            @Override
            public void onChanged(@android.support.annotation.Nullable UserPlace place) {
                txtPlaceName.setText(place.getName());
                txtPlaceAddress.setText(place.getAddress());


            }
        });
        return view;

    }
}
