package agustinreinoso.com.descubrerd.views;

import agustinreinoso.com.descubrerd.R;
import agustinreinoso.com.descubrerd.config.ConfigKey;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, PlaceSelectionListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Places.initialize(getApplicationContext(), ConfigKey.GOOGLE_MAPS_KEY);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setCountry("DO");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.ID, Place.Field.LAT_LNG,
                Place.Field.NAME, Place.Field.TYPES));

        autocompleteFragment.setOnPlaceSelectedListener(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);

        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.
                        fromResource(R.drawable.current_location)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            }
        });


    }

    @Override
    public void onPlaceSelected(@NonNull Place place) {

    }

    @Override
    public void onError(@NonNull Status status) {

    }
}
//TODO: MEJORAR EL DISENIO DEL AUTOCOMPLETE PARA QUE SEA TRANSPARENTE Y NO DIGA POWER BY GOOGLE
//TODO: CUANDO BUSQUE POR AUTOCOMPLETE QUE SE LE HAGA ZOOM AL LUGAR  Y SI HAY OTROS LUGARES REGISTRADOS QUE SE MUESTREN CON ICONO DEL TIPO DE LUGAR
//TODO: DISENIAR EL BOTTOMSHEET
//TODO: PRESENTAR EL BOTTOMSHEET
//TODO: FUNCIONALIDAD BOTTOMSHEET