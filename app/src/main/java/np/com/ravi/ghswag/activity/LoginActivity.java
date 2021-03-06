package np.com.ravi.ghswag.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import np.com.ravi.ghswag.receiver.NetworkStateChangeReceiver;
import np.com.ravi.ghswag.R;
import np.com.ravi.ghswag.api.ApiClient;
import np.com.ravi.ghswag.api.ApiInterface;
import np.com.ravi.ghswag.model.GithubUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static np.com.ravi.ghswag.receiver.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private ConstraintLayout mainLayout;
    private EditText inputUsername;
    private Button buttonShowSwag;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        inputUsername = (EditText) findViewById(R.id.edit_text_username);
        buttonShowSwag = (Button) findViewById(R.id.btn_show_swag);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //Showing Youtube like snackBar for network state change
        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("In ", "onReceive()");
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);

                if (!isNetworkAvailable) {
                    Snackbar.make(mainLayout, "Please connect to a network", Snackbar.LENGTH_INDEFINITE).show();
                    buttonShowSwag.setEnabled(false);
                } else {
                    Snackbar customSnackbar = Snackbar.make(mainLayout, "Connected", Snackbar.LENGTH_SHORT); //TODO: green color snackbar
                    View snackBarView = customSnackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.green));
                    customSnackbar.show();

                    buttonShowSwag.setEnabled(true);
                    setOnClickListenerOnButton();
                }
            }
        }, intentFilter);

    }

    private void setOnClickListenerOnButton() {
        buttonShowSwag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    isValidGHUser(inputUsername.getText().toString().trim());
                } else {
                    Snackbar.make(mainLayout, "The username field is empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validate() {
        if (inputUsername.getText().toString().trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void isValidGHUser(String userName) {
        Log.d(TAG, "Inside isValidGHUser() function");
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<GithubUser> call = apiService.getGithubUserDetails(userName);

        call.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                try {
                    int statusCode = response.code();
                    Log.d("StatusCode ", "for response: " + statusCode);
                    if (statusCode == 404) {
                        //user not found
                        Log.d("User ", "Not Found");
                        //isValid = false;
                        Log.d(TAG, "not a valid GH USer");
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(mainLayout, "The username is not a valid Github Username", Snackbar.LENGTH_SHORT).show();
                    } else if (statusCode == 200) {
                        //User exists
                        //isValid = true
                        Log.d(TAG, "valid GH USer, going to next activity");
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intentToProfileActivity = new Intent(LoginActivity.this, MainActivity.class);
                        intentToProfileActivity.putExtra("githubUsername", inputUsername.getText().toString().trim());
                        startActivity(intentToProfileActivity);
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    Log.d("Error ", e.getLocalizedMessage());
                    e.printStackTrace();
                    progressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(mainLayout, "Error connecting to GH API, please try again.", Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                Log.d("onFailure", t.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

}
