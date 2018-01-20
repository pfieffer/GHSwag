package np.com.ravi.ghswag.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Date;

import np.com.ravi.ghswag.R;
import np.com.ravi.ghswag.api.ApiClient;
import np.com.ravi.ghswag.api.ApiInterface;
import np.com.ravi.ghswag.model.GithubUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.tb);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctl);
        progressBar = (ProgressBar) findViewById(R.id.profile_progressbar);

        setSupportActionBar(toolbar);

        progressBar.setVisibility(View.VISIBLE);
        Intent intentFromMainActivity = getIntent();
        String ghUsername = intentFromMainActivity.getStringExtra("githubUsername");

        getGHUserRetrofitObject(ghUsername);
    }

    private void getGHUserRetrofitObject(String username) {
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
                    //Log.d("Bio ", response.body().getBio());  //working, will not work if bio is not set
                    Log.d("AvatarUrl ", response.body().getAvatarUrl().toString());

                    ImageView profilePic = findViewById(R.id.gh_user_display_pic);
                    Picasso.with(ProfileActivity.this).load(response.body().getAvatarUrl().toString())
                            .resize(1200, 400)
                            .centerCrop()
                            .into(profilePic); //MIGHT BE NULL TOO??, Nope

                    TextView userName = findViewById(R.id.gh_user_username);
                    userName.setText(response.body().getLogin());

                    TextView name = findViewById(R.id.gh_user_name);

                    collapsingToolbarLayout.setTitle(response.body().getName());
                    collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.iconPrimary));
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
                    name.setText(response.body().getName()); //MIGHT BE NULL TOO??, Yes but is not crashing

                    TextView location = findViewById(R.id.gh_user_location);
                    location.setText(response.body().getLocation()); //MIGHT BE NULL TOO??, Yes, but is not crashing

                    Date joinedDate = response.body().getCreated_at();
                    DateTime joinedDateTime = new DateTime(joinedDate);

                    DateTime currentDateTime = new DateTime();

                    Duration duration = new Duration(joinedDateTime, currentDateTime);
                    Long differenceDays = duration.getStandardDays();
                    int yearDiff = (int) (differenceDays / 365);
                    differenceDays %= 365;
                    int monthDiff = (int) (differenceDays / 30);

                    Log.d("Joined ", yearDiff + " years, " + monthDiff + " months ago");
                    TextView joinDate = findViewById(R.id.gh_user_joined_date);
                    Resources res = getResources();
                    String joinedBeforeText = String.format(res.getString(R.string.joined_interval), yearDiff, monthDiff);
                    joinDate.setText(joinedBeforeText);

                    TextView numberOfFollowers = (TextView) findViewById(R.id.gh_user_no_of_followers);
                    numberOfFollowers.setText(String.format(res.getString(R.string.number), response.body().getFollowers()));

                    TextView numberOfFollowing = (TextView) findViewById(R.id.gh_user_no_of_following);
                    numberOfFollowing.setText(String.format(res.getString(R.string.number), response.body().getFollowing()));

                    if (response.body().getBio() != null) {
                        TextView bio = findViewById(R.id.gh_user_bio);
                        bio.setText(response.body().getBio());
                    }

                    progressBar.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    Log.d("Error ", e.getLocalizedMessage());
                    Toast.makeText(ProfileActivity.this, "Could not get details, this time around", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(ProfileActivity.this, "Could not get details, this time around", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
