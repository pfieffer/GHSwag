package np.com.ravi.ghswag.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import np.com.ravi.ghswag.R;
import np.com.ravi.ghswag.api.ApiClient;
import np.com.ravi.ghswag.api.ApiInterface;
import np.com.ravi.ghswag.model.GithubUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
                            .resize(400, 400)
                            .centerCrop().
                            into(profilePic); //MIGHT BE NULL TOO??

                    TextView userName = findViewById(R.id.gh_user_username);
                    userName.setText(response.body().getLogin());

                    TextView name = findViewById(R.id.gh_user_name);
                    name.setText(response.body().getName()); //MIGHT BE NULL TOO??

                    TextView location = findViewById(R.id.gh_user_location);
                    location.setText(response.body().getLocation()); //MIGHT BE NULL TOO??

                    Date joinedDate = response.body().getCreated_at();
                    Date currentDate = Calendar.getInstance().getTime();
                    int yearDiff = currentDate.getYear() - joinedDate.getYear();
                    int monthDiff = currentDate.getMonth() - joinedDate.getMonth();
                    Log.d("Joined ", yearDiff + " years, " + monthDiff + " months ago");
                    TextView joinDate = findViewById(R.id.gh_user_joined_date);
                    Resources res = getResources();
                    String joinedBeforeText = String.format(res.getString(R.string.joined_interval), yearDiff, monthDiff);
                    joinDate.setText(joinedBeforeText);

                    TextView numberOfFollowers = (TextView) findViewById(R.id.gh_user_no_of_followers);
                    numberOfFollowers.setText(String.format(res.getString(R.string.followers), response.body().getFollowers()));

                    TextView numberOfFollowing = (TextView) findViewById(R.id.gh_user_no_of_following);
                    numberOfFollowing.setText(String.format(res.getString(R.string.following), response.body().getFollowing()));

                    if (response.body().getBio() != null) {
                        TextView bio = findViewById(R.id.gh_user_bio);
                        bio.setText(response.body().getBio());
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    Log.d("Error ", e.getLocalizedMessage());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

}
