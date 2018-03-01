package is.hi.apptionary.wireless;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.internal.ParcelableSparseArray;
import android.util.Log;

/**
 * Created by steina on 15.2.2018.
 * A broadcast receiver allows us to receive intents broadcast by the Android system,
 * so that we can respond to events that we are interested in.
 */

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private P2P_Activity mActivity;



    public WifiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       P2P_Activity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();


        // Broadcast intent action to indicate whether Wi-Fi p2p is enabled or disabled.
        // An extra EXTRA_WIFI_STATE provides the state information.
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int wifiState = intent.getIntExtra("EXTRA_WIFI_STATE",-1);
            // To do: wifistate always evaluates to the default value -1.
            if (WifiP2pManager.WIFI_P2P_STATE_DISABLED == wifiState) {
                debug("Wifi is disabled yo.");
            }
        }

        // Broadcast intent action indicating that the available peer list has changed. This can be
        // sent as a result of peers being found, lost or updated.
        // An extra EXTRA_P2P_DEVICE_LIST provides the full list of current peers. The full list of
        // peers can also be obtained any time with requestPeers() method of the manager.
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            debug("Peers changed");
            mActivity.requestPeers();

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections"
            this.debug("Connection changed");
            //this.mActivity.getGroupInfo();
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing

        }
    }
    private void debug(String msg) {
        Log.d("BCReceiver",msg);
    }
}


