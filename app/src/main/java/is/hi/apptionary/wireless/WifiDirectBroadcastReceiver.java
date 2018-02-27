package is.hi.apptionary.wireless;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by steina on 15.2.2018.
 * A broadcast receiver allows you to receive intents broadcast by the Android system,
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

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            this.debug("State changed");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            this.debug("Peers changed");
            this.mActivity.requestPeers();
            //this.mActivity.requestPeers();
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or diconnections"
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


