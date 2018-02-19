package is.hi.apptionary.wireless;

import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by steina on 14.2.2018.
 *
 */

public class P2P {

    /**
     * Activity utilizing the P2P class should upon creation (onCreate) call this function
     * in order to add a set of filters to a supplied IntentFilter object.
     * Mostly for convenience and de-cluttering purposes.
     */
    public static void addActionsToP2P(IntentFilter intentFilter){
        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        return;
    }





}
