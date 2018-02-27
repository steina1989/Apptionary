package is.hi.apptionary.wireless;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Collection;

import is.hi.apptionary.R;

public class P2P_Activity extends AppCompatActivity {
    WifiP2pManager.Channel mChannel;
    WifiP2pManager mManager;
    private final IntentFilter mIntentFilter = new IntentFilter();
    BroadcastReceiver mReceiver;
    String p2pTag = "P2p";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p_testing);
        testButtonSetup();
        P2P.addActionsToP2P(mIntentFilter);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);
    }

    public void requestPeers() {
        mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                Collection<WifiP2pDevice> deviceList= peers.getDeviceList();
                if (deviceList.size() == 0) {
                    Log.d(p2pTag, "Size of peer devicelist empty");
                    return;
                }
                updatePeerList(peers);
                WifiP2pDevice device = deviceList.iterator().next();
                connect(device);
            }
        });
    }

    public void connect(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(p2pTag, "Connection successful");
                mManager.requestGroupInfo(mChannel, new WifiP2pManager.GroupInfoListener() {
                    @Override
                    public void onGroupInfoAvailable(WifiP2pGroup group) {
                        Log.d(p2pTag + "groupinfo", group.getOwner().toString());
                    }
                });
            }

            @Override
            public void onFailure(int reason) {
                //failure logic
            }
        });

    }

    public void updatePeerList(WifiP2pDeviceList deviceList) {
        // Uppfæra viðmót lista með tækjum
    }

    public void sendWifiData(CharSequence s) {

    }

    public void testButtonSetup() {
        Button leftButton = (Button) findViewById(R.id.button3);
        leftButton.setText("RequestPeers()");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPeers();
            }
        });

        Button rightButton = (Button) findViewById(R.id.button2);
        rightButton.setText("jarb");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
}

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
