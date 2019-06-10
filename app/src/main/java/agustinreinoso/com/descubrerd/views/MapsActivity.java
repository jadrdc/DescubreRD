package agustinreinoso.com.descubrerd.views;

import agustinreinoso.com.descubrerd.R;
import agustinreinoso.com.descubrerd.UserPlaceListFragment;
import agustinreinoso.com.descubrerd.config.ConfigKey;
import agustinreinoso.com.descubrerd.models.UserPlace;
import agustinreinoso.com.descubrerd.viewmodels.MapUserPlaceViewModel;
import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, PlaceSelectionListener, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng latLng;
    private PlaceFoundActionFragment placeFoundActionFragment;
    private MapUserPlaceViewModel placeViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Places.initialize(getApplicationContext(), ConfigKey.GOOGLE_MAPS_KEY);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setCountry("DO");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.ID, Place.Field.LAT_LNG,
                Place.Field.NAME, Place.Field.TYPES));

        autocompleteFragment.setOnPlaceSelectedListener(this);

        if (placeViewModel == null) {
            placeViewModel = ViewModelProviders.of(this).get(MapUserPlaceViewModel.class);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        progressBar = findViewById(R.id.progressbar);
        placeViewModel.getIsSaving().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                int visibility = View.INVISIBLE;
                if (aBoolean == true) {
                    visibility = View.VISIBLE;

                } else {
                    visibility = View.INVISIBLE;
                }
                progressBar.setVisibility(visibility);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (placeFoundActionFragment != null) {
                    placeFoundActionFragment.show(getSupportFragmentManager(), "add_places");

                }
                return false;
            }
        });
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
                latLng = new LatLng(location.getLatitude(), location.getLongitude());

                mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicacion Actual").icon(BitmapDescriptorFactory.
                        fromResource(R.drawable.current_location))).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            }
        });


    }

    @Override
    public void onPlaceSelected(@NonNull Place place) {
        if (placeFoundActionFragment == null) {
            placeFoundActionFragment = new PlaceFoundActionFragment();
        }
        UserPlace placeEntity = new UserPlace();
        placeEntity.setAddress(place.getAddress());
        placeEntity.setLat(place.getLatLng().latitude);
        placeEntity.setLng(place.getLatLng().longitude);
        placeEntity.setName(place.getName());
        placeEntity.setPlace_id(place.getId());
        Place.Type x = place.getTypes().get(0);
        mMap.clear();
        LatLng location = new LatLng(placeEntity.getLat(), placeEntity.getLng());

        mMap.addMarker(new MarkerOptions().position(location).title(placeEntity.getName()
        ).icon(BitmapDescriptorFactory.
                fromResource(R.drawable.current_location))).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));


        placeViewModel.getCurrentPlace().setValue(placeEntity);
        placeFoundActionFragment.show(getSupportFragmentManager(), "add_places");

    }

    @Override
    public void onError(@NonNull Status status) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_places) {
            Intent intent = new Intent(getApplicationContext(),UserPlaceListFragment.class);
            startActivity(intent);
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        super.onBackPressed();
    }
}
//TODO: CUANDO BUSQUE POR AUTOCOMPLETE SI HAY OTROS LUGARES REGISTRADOS QUE SE MUESTREN CON ICONO DEL TIPO DE LUGAR
