package np.com.ravi.ghswag.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import np.com.ravi.ghswag.NetworkStateChangeReceiver;
import np.com.ravi.ghswag.R;

import static np.com.ravi.ghswag.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mainLayout;
    EditText inputUsername;
    Button buttonShowSwag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        inputUsername = (EditText) findViewById(R.id.edit_text_username);
        buttonShowSwag = (Button) findViewById(R.id.btn_show_swag);

        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("In ", "onReceive()");
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);

                if (!isNetworkAvailable){
                    Snackbar.make(mainLayout, "Please connect to a network", Snackbar.LENGTH_INDEFINITE).show();
                } else {
                    Snackbar customSnackbar = Snackbar.make(mainLayout, "Connected", Snackbar.LENGTH_SHORT); //TODO: green color snackbar
                    View snackBarView = customSnackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.green));
                    customSnackbar.show();
                    setOnClickListenerOnButton();
                }
            }
        }, intentFilter);

    }

    private void setOnClickListenerOnButton() {
        buttonShowSwag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    Intent intentToProfileActivity = new Intent(MainActivity.this, ProfileActivity.class);
                    intentToProfileActivity.putExtra("githubUsername", inputUsername.getText().toString());
                    startActivity(intentToProfileActivity);
                } else {
                    Snackbar.make(mainLayout, "The username field is empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validate(){
        if (inputUsername.getText().toString().length() == 0){
            return false;
        } else {
            return true;
        }
    }

}
