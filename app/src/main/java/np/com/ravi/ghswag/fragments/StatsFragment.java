package np.com.ravi.ghswag.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import np.com.ravi.ghswag.R;
import np.com.ravi.ghswag.activity.MainActivity;
import np.com.ravi.ghswag.api.ApiClient;
import np.com.ravi.ghswag.api.ApiInterface;
import np.com.ravi.ghswag.model.UserRepo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsFragment extends Fragment {
    private static final String TAG = StatsFragment.class.getSimpleName();
    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity parent = (MainActivity) getActivity();
        hitGetStatsApiAndUpdateView(view, parent.ghUsername);
    }

    private void hitGetStatsApiAndUpdateView(View view, final String username) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<List<UserRepo>> call = apiService.getGithubUserRepos(username);

       call.enqueue(new Callback<List<UserRepo>>() {
           @Override
           public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {
               Log.d(TAG, response.body().toString());
               for (UserRepo userRepo: response.body()){
                   Log.d(TAG, userRepo.getName());
                   if (userRepo.getLanguage()!=null){
                       Log.d(TAG, userRepo.getLanguage());
                   }
               }
           }

           @Override
           public void onFailure(Call<List<UserRepo>> call, Throwable t) {
                Log.d(TAG, " in onFailure()");
                t.printStackTrace();
           }
       });
    }


}
