package np.com.ravi.ghswag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        //Log.d("Got ", "username "+ghUsername);
        //String userAPIurl = AppController.BASE_URL;

        //Log.d("BASE_URL: ", AppController.BASE_URL);

        getGHUserRetrofitObject(ghUsername);

    }

    private void getGHUserRetrofitObject(String username) {
        //showProgressDialog();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<GithubUser> call = apiService.getGithubUserDetails(username);

        call.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                try {
                    int statusCode = response.code();
                    Log.d("StatusCode ", "for response: "+statusCode);
                    Log.d("username ", response.body().getLogin());
                    Log.d("AvatarUrl ", response.body().getBio());
                } catch (Exception e){
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
