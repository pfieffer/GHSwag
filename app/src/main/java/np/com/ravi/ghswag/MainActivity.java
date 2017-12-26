package np.com.ravi.ghswag;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

       if (!checkForInternet()){
           Snackbar.make(mainLayout, "Please connect to a network", Snackbar.LENGTH_INDEFINITE).show();
       } else {
           Snackbar.make(mainLayout, "Connected", Snackbar.LENGTH_SHORT).show(); //TODO: green color snackbar
           setOnClickListenerOnButton();
       }
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

    public boolean checkForInternet(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
