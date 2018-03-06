package is.hi.apptionary.wireless;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import is.hi.apptionary.R;

public class P2P_Activity extends AppCompatActivity {
    WifiP2pManager.Channel mChannel;
    WifiP2pManager mManager;
    private final IntentFilter mIntentFilter = new IntentFilter();
    BroadcastReceiver mReceiver;
    String p2pTag = "P2p";
    String mServerAddress;
    ServerThread mServer;  ///Ef
    ArrayList<WifiP2pDevice> discoveredPeers; //Geymum peers sem við höfum fundið
    boolean attemptingToConnect, serverMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        //Frumstilla breytur
        discoveredPeers = new ArrayList();
        attemptingToConnect = false;
        serverMode = false;
        //
        setContentView(R.layout.activity_p2p_testing);
        testButtonSetup();
        P2P.addActionsToP2P(mIntentFilter);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);


        // Initiate the peer discovery. NOTE: Only initiating.
        // Discovery remains active until a connection is initiated or a P2P group is formed.
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank.

                Log.d("discoverpeers", "Discovery initation successful");
                requestPeers();
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                Log.d("discoverpeers", "Discovery initation NOT successful");

            }
        });

    }

    public void getGroupInfo() {
        mManager.requestGroupInfo(mChannel, new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(WifiP2pGroup group) {
                Log.d(p2pTag + "groupinfo", group.getOwner().toString());
            }
        });
    }

    public void requestPeers() {
        discoveredPeers.clear();
        mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                Collection<WifiP2pDevice> deviceList = peers.getDeviceList();
                if (deviceList.size() == 0) {
                    Log.d(p2pTag, "Size of peer devicelist 0");
                    return;
                }

                discoveredPeers.addAll(peers.getDeviceList());
                updatePeerList();
                WifiP2pDevice device = deviceList.iterator().next();
                connect(device);
            }
        });
    }


    public void connect(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        mServerAddress = device.deviceAddress;
        attemptingToConnect = true;
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                attemptingToConnect = false;
                TextView headerText = findViewById(R.id.headerText);
                headerText.setText("Connected");
                mManager.requestConnectionInfo(mChannel, new WifiP2pManager.ConnectionInfoListener() {

                    @Override
                    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
                        // InetAddress from WifiP2pInfo struct.
                        final String groupOwnerName = info.groupOwnerAddress.getHostName();
                        final Button rightButton = (Button) findViewById(R.id.button2);
                        // After the group negotiation, we can determine the group owner
                        // (server).
                        if (info.groupFormed && info.isGroupOwner) {
                            Log.d(p2pTag, "I am the group owner and the group is formed.");


                            rightButton.setText("Start server socket");
                            rightButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ServerThread thread = new ServerThread(v.getContext(),(TextView) findViewById(R.id.listOfPeers));
                                    thread.doInBackground(null);
                                    rightButton.setOnClickListener(null); //ekki hægt að klikka aftur
                                    rightButton.setText("Server socket started.");
                                }
                            });
                        } else if (info.groupFormed) {
                            // The other device acts as the peer (client). In this case,
                            // you'll want to create a peer thread that connects
                            // to the group owner.
                            rightButton.setText("Send as client");
                            rightButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ClientThread thread = new ClientThread();
                                    thread.doInBackground(new Object[]{groupOwnerName});
                                    rightButton.setOnClickListener(null); //ekki hægt að klikka aftur
                                    rightButton.setText("Sending data.");
                                }
                            });

                            Log.d(p2pTag, "Group is formed.");
                        }
                    }
                });
                Log.d(p2pTag, "Connection successful");
            }

            @Override
            public void onFailure(int reason) {
                attemptingToConnect = false;
                Log.d(p2pTag, "Unable to connection " + reason);
            }
        });

    }

    /***
     * Uppfærir viðmót með lista af fundnum tækjum
     */
    public void updatePeerList() {
        // Uppfæra viðmót lista með tækjum
        TextView listOfPeers = findViewById(R.id.listOfPeers);
        listOfPeers.setText("-");
        for (WifiP2pDevice d : discoveredPeers) {
            listOfPeers.setText(listOfPeers.getText() + d.deviceName);
        }

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
    protected void onDestroy() {
        // TO do disconnect wifidirect.

        super.onDestroy();
    }


    public static class ClientThread extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            String host = (String) objects[0];
            int port = 8888;
            int len;
            Socket socket = new Socket();
            byte buf[] = new byte[1024];

            try {
                /**
                 * Create a client socket with the host,
                 * port, and timeout information.
                 */
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), 500);

                /**
                 * Create a byte stream from a JPEG file and pipe it to the output stream
                 * of the socket. This data will be retrieved by the server device.
                 */
                OutputStream outputStream = socket.getOutputStream();


                outputStream.write(42);
                outputStream.close();
            } catch (FileNotFoundException e) {
                //catch logic
            } catch (IOException e) {
                //catch logic
            }

/**
         * Clean up any open sockets when done
         * transferring or if an exception occurred.
         */ finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            //catch logic
                        }
                    }
                }
            }
            return 1;
        }

    }


    public static class ServerThread extends AsyncTask {
        private Context context;
        private TextView statusText;

        public ServerThread(Context context, View statusText) {
            this.context = context;
            this.statusText = (TextView) statusText;
        }

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
                while (true) {
                    inputStreamStatus = inputstream.read();
                    Log.d("P2PStream", String.valueOf(inputStreamStatus));
                    statusText.setText(String.valueOf(inputStreamStatus));
                    if (inputStreamStatus < 0) {
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

