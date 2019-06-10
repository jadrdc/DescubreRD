package agustinreinoso.com.descubrerd.adapters;

import agustinreinoso.com.descubrerd.R;
import agustinreinoso.com.descubrerd.models.UserPlace;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserPlaceAdapter extends RecyclerView.Adapter<UserPlaceAdapter.UserPlaceViewHolder> {

    private List<UserPlace> userPlaceList;

    public UserPlaceAdapter() {
    }

    @NonNull
    @Override
    public UserPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View contactView = inflater.inflate(R.layout.place_item, viewGroup, false);
        UserPlaceViewHolder viewHolder = new UserPlaceViewHolder(contactView);
        return viewHolder;
    }

    public void setUserPlaces(List<UserPlace> list) {
        userPlaceList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull UserPlaceViewHolder userPlaceViewHolder, int i) {

        userPlaceViewHolder.updateUI(userPlaceList.get(i));
    }

    @Override
    public int getItemCount() {
        return userPlaceList.size();
    }

    public static class UserPlaceViewHolder extends RecyclerView.ViewHolder {

        private ImageView placePhoto;
        private TextView placeName;
        private ImageView placeIcon;

        public UserPlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.place_item_name);
        }

        public void updateUI(UserPlace place) {
            placeName.setText(place.getName());

        }
    }

    public static class UserPlaceSwipeBehavior extends ItemTouchHelper.SimpleCallback {

        public UserPlaceSwipeBehavior(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        public  UserPlaceSwipeBehavior()
        {
            super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);

        }
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }
    }


}
