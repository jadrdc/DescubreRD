package agustinreinoso.com.descubrerd;

import agustinreinoso.com.descubrerd.adapters.UserPlaceAdapter;
import agustinreinoso.com.descubrerd.models.UserPlace;
import agustinreinoso.com.descubrerd.viewmodels.UserPlaceListViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Arrays;
import java.util.List;


public class UserPlaceListFragment extends AppCompatActivity {
    public UserPlaceListFragment() {
        // Required empty public constructor
    }

    private UserPlaceAdapter adapter;
    private UserPlaceListViewModel userPlaceListViewModel;

    /*
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_place_list, container, false);
        userPlaceListViewModel = ViewModelProviders.of(this).get(UserPlaceListViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_list);
        List<UserPlace> userPlaces = userPlaceListViewModel.getUserPlaceslist().getValue();
        final UserPlaceAdapter adapter = new UserPlaceAdapter(userPlaces);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        userPlaceListViewModel.getUserPlaceslist().observe(this, new Observer<List<UserPlace>>() {
            @Override
            public void onChanged(@Nullable List<UserPlace> list) {
                adapter.setUserPlaces(list);
            }
        });


        return view;
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_user_place_list);
        super.onCreate(savedInstanceState);

        userPlaceListViewModel = ViewModelProviders.of(this).get(UserPlaceListViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycle_view_list);
        final UserPlaceAdapter adapter = new UserPlaceAdapter();
        adapter.setUserPlaces(Arrays.asList(new UserPlace()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);


        userPlaceListViewModel.getUserPlaceslist().observe(this, new Observer<List<UserPlace>>() {
            @Override
            public void onChanged(@Nullable List<UserPlace> list) {
                adapter.setUserPlaces(list);
            }
        });

        UserPlaceAdapter.UserPlaceSwipeBehavior swipeBehavior = new UserPlaceAdapter.UserPlaceSwipeBehavior();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeBehavior);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
