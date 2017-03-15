package andella.challenge.com.andellachallenge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.icu.lang.UCharacter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import andella.challenge.com.andellachallenge.data.GiHubApiInterface;
import andella.challenge.com.andellachallenge.data.GitHubClient;
import andella.challenge.com.andellachallenge.data.GitHubUser;
import andella.challenge.com.andellachallenge.data.User;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ememobong on 15/03/2017.
 */
public class ListContentFragment extends Fragment {

    ContentAdapter adapter;
    RecyclerView recyclerView;
    static String[] usernameList;
    static String[] userProfilePhotoList;
    static String[] userProfileUrl;

    ProgressDialog progressDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
//         adapter = new ContentAdapter(MainActivity.userDetails, recyclerView.getContext());


        // check for internet connection before trying to use Retrovit to get api data
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()){
            // user network is available send get request while showing a progress dialog to notify user
            progressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Loading ...", true);
            GiHubApiInterface apiService = GitHubClient.getClient().create(GiHubApiInterface.class);

            Call<User> call = apiService.getUserDetailsLagos();

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User>call, Response<User> response) {
                    List<GitHubUser> userDetails = response.body().getUserDetails();
                    Log.d("Retrofit","number of users " + userDetails.size());

                    usernameList = new String[userDetails.size()];
                    userProfilePhotoList = new String[userDetails.size()];
                    userProfileUrl = new String[userDetails.size()];

                    ArrayList<String> userNames = new ArrayList<String>();
                    ArrayList<String> profilePhotoUrl = new ArrayList<String>();
                    ArrayList<String> profileUrl = new ArrayList<String>();

                    for (int i = 0; i<userDetails.size(); i++){
                        //parsing the json data from api

                        usernameList[i] = (userDetails.get(i).getUsername());
                        userProfilePhotoList[i] = userDetails.get(i).getProfilePhotoUrl();
                        userProfileUrl[i] = userDetails.get(i).getProfileUrl();

                        adapter = new ContentAdapter(userDetails, recyclerView.getContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        //done loading from server - hide the progressDialog
                        progressDialog.hide();
                    }

                }

                @Override
                public void onFailure(Call<User>call, Throwable t) {
                    // Log error here since request failed
                    Log.e("error", t.toString());
                }
            });
        }else{
            //network is not available on this device
            Intent intent = new Intent(getActivity(), ErrorUtility.class);
            startActivity(intent);
            getActivity().finish();

        }

        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView avator;
        public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_tile, parent, false));
            avator = (CircleImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 30;
        Context context;
        List<GitHubUser> users;
        private final String[] usernames;
        private final String[] profilePhotoUrl;


        public ContentAdapter(List<GitHubUser> users, Context context) {
            this.context = context;
            this.users = users;
            Resources resources = context.getResources();

            usernames = (String[]) ListContentFragment.usernameList;
            profilePhotoUrl = (String[]) ListContentFragment.userProfileUrl;

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

//            using glide to download profile image with diskcaching to minimise data usage on user phone
            Glide.with(context)
                    .load(ListContentFragment.userProfilePhotoList[position])
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.avator);

            //capitalising the first letter of the name
            String name  = usernames[position];
            name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
            holder.name.setText( name);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
