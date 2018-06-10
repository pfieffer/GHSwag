package np.com.ravi.ghswag.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import np.com.ravi.ghswag.R;
import np.com.ravi.ghswag.adapter.BottombarAdapter;
import np.com.ravi.ghswag.fragments.ProfileFragment;
import np.com.ravi.ghswag.fragments.StatsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private BottombarAdapter bottombarAdapter;
    public String ghUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentFromLoginActivity = getIntent();
        ghUsername = intentFromLoginActivity.getStringExtra("githubUsername");

        viewPager = findViewById(R.id.viewpager_main);
        bottomNavigationView = findViewById(R.id.bnv_main);

        bottombarAdapter = new BottombarAdapter(getSupportFragmentManager());

        ProfileFragment profileFragment = new ProfileFragment();
        StatsFragment statsFragment = new StatsFragment();
//
        bottombarAdapter.addFragments(profileFragment); //0
        bottombarAdapter.addFragments(statsFragment); //1

        viewPager.setAdapter(bottombarAdapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_stats:
                Toast.makeText(this, "Stats", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
                break;
        }
        return true;
    }
}
