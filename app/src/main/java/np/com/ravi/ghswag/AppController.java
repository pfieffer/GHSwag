package np.com.ravi.ghswag;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

/**
 * Created by ravi on 12/27/17.
 */

public class AppController extends Application {

    private static final String WIFI_STATE_CHANGE_ACTION = "android.net.wifi.WIFI_STATE_CHANGED";

    @Override
    public void onCreate() {
        super.onCreate();
        registerForNetworkChangeEvents(this);
    }

    public static void registerForNetworkChangeEvents(final Context context) {
        NetworkStateChangeReceiver networkStateChangeReceiver = new NetworkStateChangeReceiver();
        context.registerReceiver(networkStateChangeReceiver, new IntentFilter(CONNECTIVITY_ACTION));
        context.registerReceiver(networkStateChangeReceiver, new IntentFilter(WIFI_STATE_CHANGE_ACTION));
    }
}
