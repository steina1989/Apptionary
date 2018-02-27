package is.hi.apptionary.wireless;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
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

    public void getGroupInfo(){

        mManager.requestGroupInfo(mChannel, new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(WifiP2pGroup group) {

                Log.d(p2pTag + "groupinfo", group.getOwner().toString());
            }
        });


    }
    public void requestPeers() {

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList peers) {
                        Collection<WifiP2pDevice> deviceList= peers.getDeviceList();
                        if (deviceList.size() == 0) {
                            Log.d(p2pTag, "Size of peer devicelist 0");
                            return;
                        }
                        updatePeerList(peers);
                        WifiP2pDevice device = deviceList.iterator().next();
                        connect(device);
                    }
                });

            }

            @Override
            public void onFailure(int reason) {

            }
        });


    }



    public void connect(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
       // config.groupOwnerIntent = 0;
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(p2pTag, "Connection successful");
                ServerThread sThread=new ServerThread();
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
        rightButton.setText("groupinfo");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGroupInfo();
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

    @Override
    protected void onDestroy(){
        // TO do disconnect wifidirect.
        super.onDestroy();
    }


    public static class ServerThread extends AsyncTask {
        protected Object doInBackground(Object[] objects) {
                int inputStreamStatus;

                /**
                 * Create a server socket and wait for client connections. This
                 * call blocks until a connection is accepted from a client
                 */
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8888);
                Socket client = serverSocket.accept();
                InputStream inputstream = client.getInputStream();
                while(true){
                    inputStreamStatus=inputstream.read();
                    Log.d("P2PStream", String.valueOf(inputStreamStatus));
                    if(inputStreamStatus<0){
                        break;
                    }
                }

                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return 1;
        }



    }


}
