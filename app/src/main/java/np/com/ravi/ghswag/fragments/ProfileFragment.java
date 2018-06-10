package np.com.ravi.ghswag.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Date;

import np.com.ravi.ghswag.R;
import np.com.ravi.ghswag.activity.MainActivity;
import np.com.ravi.ghswag.api.ApiClient;
import np.com.ravi.ghswag.api.ApiInterface;
import np.com.ravi.ghswag.model.GithubUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    private ImageView profilePicIV;
    private TextView userNameTV, nameTV, locationTV, joinDateTV, numberOfFollowersTV, numberOfFollowinTV, bioTV;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponents(rootView);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        MainActivity parent = (MainActivity) getActivity();
        getGHUserRetrofitObject(parent.ghUsername);

        return rootView;
    }

    private void initComponents(View rootView) {
        toolbar = rootView.findViewById(R.id.tb);
        collapsingToolbarLayout = rootView.findViewById(R.id.ctl);
        progressBar = rootView.findViewById(R.id.profile_progressbar);
        profilePicIV = rootView.findViewById(R.id.gh_user_display_pic);
        userNameTV = rootView.findViewById(R.id.gh_user_username);
        nameTV = rootView.findViewById(R.id.gh_user_name);
        locationTV = rootView.findViewById(R.id.gh_user_location);
        joinDateTV = rootView.findViewById(R.id.gh_user_joined_date);
        numberOfFollowersTV = rootView.findViewById(R.id.gh_user_no_of_followers);
        numberOfFollowinTV = rootView.findViewById(R.id.gh_user_no_of_following);
        bioTV = rootView.findViewById(R.id.gh_user_bio);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getGHUserRetrofitObject(String username) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<GithubUser> call = apiService.getGithubUserDetails(username);

        call.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                try {
                    int statusCode = response.code();
                    Log.d("StatusCode ", "for response: " + statusCode);
                    Log.d("username ", response.body().getLogin()); //working
                    //Log.d("Bio ", response.body().getBio());  //working, will not work if bioTV is not set
                    Log.d("AvatarUrl ", response.body().getAvatarUrl().toString());

                    updateView(response);

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    Log.d("Error ", e.getLocalizedMessage());
                    Toast.makeText(getContext(), "Could not get details, this time around", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getContext(), "Could not get details, this time around", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void updateView(Response<GithubUser> response) {
        Picasso.with(getContext()).load(response.body().getAvatarUrl().toString())
                .resize(1200, 400)
                .centerCrop()
                .into(profilePicIV); //MIGHT BE NULL TOO??, Nope

        userNameTV.setText(response.body().getLogin());

        collapsingToolbarLayout.setTitle(response.body().getName());
        collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.iconPrimary));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
        nameTV.setText(response.body().getName()); //MIGHT BE NULL TOO??, Yes but is not crashing
        locationTV.setText(response.body().getLocation()); //MIGHT BE NULL TOO??, Yes, but is not crashing

        Date joinedDate = response.body().getCreated_at();
        DateTime joinedDateTime = new DateTime(joinedDate);
        DateTime currentDateTime = new DateTime();
        Duration duration = new Duration(joinedDateTime, currentDateTime);
        Long differenceDays = duration.getStandardDays();
        int yearDiff = (int) (differenceDays / 365);
        differenceDays %= 365;
        int monthDiff = (int) (differenceDays / 30);

        Log.d("Joined ", yearDiff + " years, " + monthDiff + " months ago");
        Resources res = getResources();
        String joinedBeforeText = String.format(res.getString(R.string.joined_interval), yearDiff, monthDiff);
        joinDateTV.setText(joinedBeforeText);

        numberOfFollowersTV.setText(String.format(res.getString(R.string.number), response.body().getFollowers()));

        numberOfFollowinTV.setText(String.format(res.getString(R.string.number), response.body().getFollowing()));

        if (response.body().getBio() != null) {
            bioTV.setText(response.body().getBio());
        }

        progressBar.setVisibility(View.INVISIBLE);
    }

}
