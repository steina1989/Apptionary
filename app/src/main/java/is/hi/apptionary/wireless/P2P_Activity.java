package is.hi.apptionary.wireless;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Collection;
import java.util.Iterator;

import is.hi.apptionary.R;



public class P2P_Activity extends AppCompatActivity {
    WifiP2pManager.Channel mChannel;
    WifiP2pManager mManager;
    private final IntentFilter mIntentFilter = new IntentFilter();
    BroadcastReceiver mReceiver;

    Collection<WifiP2pDevice> deviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p_testing);

        P2P.addActionsToP2P(mIntentFilter);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

                Log.d("mManager", "Successfully discovered a peer.");

            }

            @Override
            public void onFailure(int reasonCode) {

            }
        });

        mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                Log.d("onPeersAvailable", peers.toString());
                deviceList = peers.getDeviceList();
                for (Iterator<WifiP2pDevice> d = deviceList.iterator(); d.hasNext();) {

                    WifiP2pDevice device = d.next();
                    WifiP2pConfig config = new WifiP2pConfig();
                    config.deviceAddress = device.deviceAddress;

                }

                }
        });


        WifiP2pConfig config = new WifiP2pConfig();

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                //success logic
            }

            @Override
            public void onFailure(int reason) {
                //failure logic
            }
        });



    

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }





}
