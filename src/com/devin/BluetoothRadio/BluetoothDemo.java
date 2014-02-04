package com.devin.BluetoothRadio;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;


import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothDemo extends Activity {
    /** Called when the activity is first created. */

    public static boolean connectFlag = true;
    private boolean connectLoopFlag = true;

    ListView listViewPaired;
    ListView listViewDetected;
    ArrayList<String> arrayListpaired;
    Button buttonSearch,buttonOn,buttonDesc,buttonOff;
    ArrayAdapter<String> adapter,detectedAdapter;
    static HandleSeacrh handleSeacrh;
    BluetoothDevice bdDevice;
    BluetoothClass bdClass;
    ArrayList<BluetoothDevice> arrayListPairedBluetoothDevices;
    private ButtonClicked clicked;
    ListItemClickedonPaired listItemClickedonPaired;
    BluetoothAdapter bluetoothAdapter = null;
    ArrayList<BluetoothDevice> arrayListBluetoothDevices = null;
    ListItemClicked listItemClicked;

    public static BluetoothSocket btSocket;
    public static OutputStream outStream;
    public static InputStream inputStream;
    public static boolean receiveFlag;
    public static String receiveContent = "";
    public static int receiveCount = 0;
    byte[] blueOrderResponseEmpty = {(byte)0xFF, (byte)0x77, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private CustomProgressDialog progressBar;
    public static int modeCurrent = 0;

    public static BluetoothDemo mContext;

    private DownloadTask dTask;

    private volatile boolean _discoveryFinished;
    private Handler _handler = new Handler();
    private Runnable _discoveryWorkder = new Runnable() {
        public void run() {
            bluetoothAdapter.startDiscovery();
            for (;;){
                if (_discoveryFinished) {
                    break;
                }
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e){}
            }
        }
    };

    public Handler handlerWel = new Handler() {
        public void handleMessage(Message msg) {//定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 1:
                        //FF7705000000000D0A
                        /*byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x05, (byte)0x00,
                                (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder); Program would throw ERROR*/

                        progressBar.dismiss();

                        //gallery--temporary
                        /*Intent intent = new Intent(BluetoothDemo.this, Activity02.class);
                        BluetoothDemo.this.startActivity(intent);
                        BluetoothDemo.this.finish();*/

                        openSecurityWindow();

                        break;

                    case 2:
                        //setTitle(MathMethod.receiveForSecurity + " ===>" + MathMethod.contentForSecurity);
                        Log.e("gallery", "goodbye to BluetoothDemo");
                        Intent intentX = new Intent(BluetoothDemo.this, Activity02.class) ;
                        BluetoothDemo.this.startActivity(intentX);
                        BluetoothDemo.this.finish();

                        break;
                    case 3:
                        try {
                            showOpenBTDialog();
                        } catch(Exception e) {

                        }
                        break;
                    case 4:
                        try {
                            if(mAlertOption != null)
                                mAlertOption.dismiss();
                        } catch(Exception e) {

                        }
                        break;
                    case -1:
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };
    private static AlertDialog mAlertOption;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demomain);

        mContext = BluetoothDemo.this;

        listViewDetected = (ListView) findViewById(R.id.listViewDetected);
        listViewPaired = (ListView) findViewById(R.id.listViewPaired);

        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setVisibility(View.INVISIBLE);

        buttonOn = (Button) findViewById(R.id.buttonOn);
        buttonOn.setVisibility(View.INVISIBLE);

        buttonDesc = (Button) findViewById(R.id.buttonDesc);
        buttonDesc.setVisibility(View.INVISIBLE);

        buttonOff = (Button) findViewById(R.id.buttonOff);
        buttonOff.setVisibility(View.INVISIBLE);

        arrayListpaired = new ArrayList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        clicked = new ButtonClicked();
        handleSeacrh = new HandleSeacrh();
        arrayListPairedBluetoothDevices = new ArrayList<BluetoothDevice>();
        /*
         * the above declaration is just for getting the paired bluetooth devices;
         * this helps in the removing the bond between paired devices.
         */
        listItemClickedonPaired = new ListItemClickedonPaired();
        arrayListBluetoothDevices = new ArrayList<BluetoothDevice>();
        adapter= new ArrayAdapter<String>(BluetoothDemo.this, android.R.layout.simple_list_item_1, arrayListpaired);
        detectedAdapter = new ArrayAdapter<String>(BluetoothDemo.this, android.R.layout.simple_list_item_single_choice);
        listViewDetected.setAdapter(detectedAdapter);
        listItemClicked = new ListItemClicked();
        detectedAdapter.notifyDataSetChanged();
        listViewPaired.setAdapter(adapter);

        //gallery--temporary
        //showOpenBTDialog();
    }

    private void showOpenBTDialog() {
            mAlertOption = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Black)).setTitle("GotoBluetooth")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                            int state = adapter.getState();
                            if (state == BluetoothAdapter.STATE_OFF)
                                adapter.enable();
                            else if (state == BluetoothAdapter.STATE_ON)
                                adapter.disable();*/
                            startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            connectFlag = false;
                            if(!connectFlag) {
                                Intent intent = new Intent(BluetoothDemo.this, Activity02.class);
                                BluetoothDemo.this.startActivity(intent);
                                BluetoothDemo.this.finish();
                            }
                            return;

                        }
                    }).create();
        if (mAlertOption!=null&&!isFinishing()) {
            mAlertOption.show();
        }

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        connectFlag = true;
        getPairedDevices();

        //gallery--temporary
        /*for(int ddd=0; ddd<arrayListPairedBluetoothDevices.size(); ddd++) {
            bdDevice = arrayListPairedBluetoothDevices.get(ddd);
            if(bdDevice.getName().toUpperCase().contains("RADIO")) {
                new ConnectThread(bdDevice).start();
                break;
            }
        }*/

        buttonOn.setOnClickListener(clicked);
        buttonSearch.setOnClickListener(clicked);
        buttonDesc.setOnClickListener(clicked);
        buttonOff.setOnClickListener(clicked);
        listViewDetected.setOnItemClickListener(listItemClicked);
        listViewPaired.setOnItemClickListener(listItemClickedonPaired);

        int state = bluetoothAdapter.getState();
        if (state == BluetoothAdapter.STATE_OFF) {
            bluetoothAdapter.enable();
            startActivityForResult(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS), 0);
        }
        else {
            if(arrayListPairedBluetoothDevices.size() == 0) {
                Log.e("gallery", "arrayListPairedBluetoothDevices size === " + arrayListPairedBluetoothDevices.size());
                showOpenBTDialog();
            }
            else {
            dTask = new DownloadTask();
            dTask.execute();
            }
        }

        //notifyMeFriWill(5);
        /*if(arrayListPairedBluetoothDevices.size() == 0) {
            Log.e("gallery", "arrayListPairedBluetoothDevices size === " + arrayListPairedBluetoothDevices.size());
            showOpenBTDialog();
        }
        else {
            Log.e("gallery", "arrayListPairedBluetoothDevices size === " + arrayListPairedBluetoothDevices.size());
            for(int ddd=0; ddd<arrayListPairedBluetoothDevices.size(); ddd++) {
                bdDevice = arrayListPairedBluetoothDevices.get(ddd);
                if(bdDevice.getName().toUpperCase().contains("RADIO")) {
                    ConnectThread ctxxx = new ConnectThread(bdDevice);
                    if(receiveFlag) {
                        ctxxx.start();
                    }
                    else {
                        Log.e("gallery", "Paired connect loop");
                    }
                    //break;
                }
            }
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAlertOption != null) {
            mAlertOption.dismiss();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        connectFlag = true;

        /*notifyMeFriWill(4);

        Log.e("gallery", "arrayListPairedBluetoothDevices size === " + arrayListPairedBluetoothDevices.size());
        for(int ddd=0; ddd<arrayListPairedBluetoothDevices.size(); ddd++) {
            bdDevice = arrayListPairedBluetoothDevices.get(ddd);
            if(bdDevice.getName().toUpperCase().contains("RADIO")) {
                ConnectThread ctxxx = new ConnectThread(bdDevice);
                if(receiveFlag) {
                    ctxxx.start();
                }
                else {
                    Log.e("gallery", "Paired connect loop");
                }
                //break;
            }
        }*/
    }

    private void getPairedDevices() {
        Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
        if(pairedDevice.size()>0) {
            for(BluetoothDevice device : pairedDevice) {
                arrayListpaired.add(device.getName()+"\n"+device.getAddress());
                arrayListPairedBluetoothDevices.add(device);
            }
        }
        adapter.notifyDataSetChanged();
    }

    class ListItemClicked implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            bdDevice = arrayListBluetoothDevices.get(position);

            /*
             * here below we can do pairing without calling the callthread(), we can directly call the
             * connect(). but for the safer side we must use the threading object.
             */
            if(bdDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                //new ConnectThread(bdDevice).start();
            }
            else {

                //btSocket = bdDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                /*UUID uuid = bdDevice.getUuids()[0].getUuid();
                btSocket = bdDevice.createRfcommSocketToServiceRecord(uuid);*/

                Boolean isBonded = false;
                try {
                    isBonded = createBond(bdDevice);
                    if(isBonded) {
                        getPairedDevices();
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class ListItemClickedonPaired implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            bdDevice = arrayListPairedBluetoothDevices.get(position);
            /*try {
                Boolean removeBonding = removeBond(bdDevice);
                if(removeBonding)
                {
                    arrayListpaired.remove(position);
                    adapter.notifyDataSetChanged();
                }
                Log.i("Log", "Removed"+removeBonding);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            //try {
            //btSocket = bdDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

            /*Method m = bdDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
            btSocket = (BluetoothSocket) m.invoke(bdDevice, 1);*/

            /*} catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/

            //} catch (IOException e) {
            //   e.printStackTrace();
            //    setTitle("============= create socket fail");
            //}

            new ConnectThread(bdDevice).start();
            //notifyMeFriWill(1);

             //成功后进行连接
            /*try {

                //btSocket = bdDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                //btSocket.connect();

                //Log.e(TAG, " BT connection established, data transfer link open.");
                //mangeConnectedSocket(btSocket);//自定义函数进行蓝牙通信处理

            } catch (IOException e) {
                //Log.e(TAG, " Connection failed.", e);
                setTitle("============= connect fail");
            } catch (Exception eee) {
                //Log.e(TAG, " Connection failed.", e);
                setTitle("============= btSocket null");
            }*/
        }
    }
    /*private void callThread() {
        new Thread(){
            public void run() {
                Boolean isBonded = false;
                try {
                    isBonded = createBond(bdDevice);
                    if(isBonded)
                    {
                        arrayListpaired.add(bdDevice.getName()+"\n"+bdDevice.getAddress());
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }//connect(bdDevice);
                Log.i("Log", "The bond is created: "+isBonded);
            }
        }.start();
    }*/

    private void mangeConnectedSocket(BluetoothSocket xxx) {
        String msgBufferString = "Hello";
        byte[] msgBuffer = msgBufferString.getBytes();
        try {
            OutputStream outStream = xxx.getOutputStream();
            outStream.write(msgBuffer);
        } catch (java.io.IOException e) {
            setTitle("============== OutStream send fail");
        }
    }

    private Boolean connect(BluetoothDevice bdDevice) {
        Boolean bool = false;
        try {
            Log.i("Log", "service metohd is called ");
            Class cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class[] par = {};
            Method method = cl.getMethod("createBond", par);
            Object[] args = {};
            bool = (Boolean) method.invoke(bdDevice);//, args);// this invoke creates the detected devices paired.
        } catch (Exception e) {
            Log.i("Log", "Inside catch of serviceFromDevice Method");
            e.printStackTrace();
        }
        return bool.booleanValue();
    };


    public boolean removeBond(BluetoothDevice btDevice) throws Exception {
        Class btClass = Class.forName("android.bluetooth.BluetoothDevice");
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e("gallery", "device.createRfcommSocketToServiceRecord IOException and receiveFlag = false");
                receiveFlag = false;
                //return;
            }
            /*try {
                Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                tmp = (BluetoothSocket) m.invoke(device, 1);
            } catch (NoSuchMethodException ex1) {
                Log.e("gallery", "NoSuchMethodException");
            } catch (IllegalAccessException ex2) {
                Log.e("gallery", "IllegalAccessException");
            } catch (InvocationTargetException ex3) {
                Log.e("gallery", "InvocationTargetException");
            }*/

            mmSocket = tmp;

            //gallery==replace===A
            try{
                receiveFlag = true;
                mmSocket.connect();
                Log.e("gallery", "mmSocket connect ================== Success and receiveFlag = true");
                btSocket = mmSocket;
                outStream = mmSocket.getOutputStream();
                inputStream = mmSocket.getInputStream();
                notifyMeFriWill(1);
            } catch (IOException e) {
                Log.e("gallery", "mmSocket connect ================== IOException and receiveFlag = false");
                receiveFlag = false;
                //return;
            }
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            //bluetoothAdapter.cancelDiscovery();

            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception

            //gallery==replace===B
                /*mmSocket.connect();
                btSocket = mmSocket;
                outStream = mmSocket.getOutputStream();
                inputStream = mmSocket.getInputStream();
                notifyMeFriWill(1);
                receiveFlag = true;
                */

            //byte[] bytes = new byte[512];
            byte[] bytes = new byte[9];
            byte[] bytesForChar = new byte[256];
            receiveContent = "";

            while(receiveFlag) {

                try {
                    int read = -1;
                    for (; (read = inputStream.read(bytes)) > -1;) {
                    //while ((inputStream.read(bytes)) > -1) {

                        receiveContent = MathMethod.byte2HexStr(bytes);
                        /*if(temporary[2].equalsIgnoreCase("82")) {
                            if(temporary[3].equalsIgnoreCase("01")) {
                                Activity02.volumeValueFromRadio = Integer.parseInt(temporary[4], 16);
                                notifyMsgToRadioInterface(1);
                            }
                        }*/

                        //Log.e("gallery", " receive ===> " + read + " ==> " + receiveContent);

                        if(bytes[2] == (byte)0xFD) {
                            Log.e("gallery", "order: 0xFD");
                            if(bytes[3] == (byte)0x00) {
                                receiveFlag = false;
                                break;
                            }
                        }

                        if(bytes[2] == (byte)0x82) {
                            Log.e("gallery", "order: 0x82");
                            if(bytes[3] == (byte)0x01) {
                                Activity02.volumeValueFromRadio = MathMethod.byteHEXtoInt(bytes[4]);
                                notifyMsgToRadioInterface(1);

                            } else if(bytes[3] == (byte)0x00) {
                                Log.e("gallery", "Device say: mute");
                                notifyMsgToRadioInterface(4);
                            }
                        }
                        else if(bytes[2] == (byte)0x83) {
                            Log.e("gallery", "order: 0x83");
                            modeCurrent = MathMethod.byteHEXtoInt(bytes[3]);
                            Log.e("gallery", "modeCurrent ====================> " + modeCurrent);
                            //if(modeCurrent < 7)
                            notifyMsgToRadioInterface(12);
                            //FF7705xx0100000D0A
                            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x05, bytes[3],
                                    (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                            sendBlueOrder(blueOrder);
                        }
                        else if(bytes[2] == (byte)0x84) {
                            Log.e("gallery", "order: 0x84");
                            switch(bytes[3]) {
                                case (byte)0x01:
                                    Activity02.mFMArrayIndex = 0;
                                    Log.e("gallery", "mFMArrayIndex: " + Activity02.mFMArrayIndex);
                                    break;
                                case (byte)0x02:
                                    Activity02.mFMArrayIndex = 1;
                                    Log.e("gallery", "mFMArrayIndex: " + Activity02.mFMArrayIndex);
                                    break;
                                case (byte)0x03:
                                    Activity02.mFMArrayIndex = 2;
                                    Log.e("gallery", "mFMArrayIndex: " + Activity02.mFMArrayIndex);
                                    break;
                                case (byte)0x86: {
                                    //byte[] combineBytes = {(byte)0xE0, (byte)0xA5, (byte)0x01, (byte)0x00};
                                    //System.out.println("gallery: " + MathMethod.bytesToInt(textBytes));
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.maxFMvalue = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "maxFMvalue: " + Activity02.maxFMvalue);
                                    break;
                                }
                                case (byte)0x87: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.minFMvalue = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "minFMvalue: " + Activity02.minFMvalue);
                                    break;
                                }
                                case (byte)0x08: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x00, (byte)0x00};
                                    Activity02.mFmStepValue = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "mFmStepValue: " + Activity02.mFmStepValue);
                                    break;
                                }

                                case -1:
                                    break;
                            }
                            notifyMsgToRadioInterface(2);
                        }
                        else if(bytes[2] == (byte)0x85) {
                            Log.e("gallery", "order: 0x85");
                            Activity02.mFMValueShowState = 0;
                            switch(bytes[3]) {
                                case (byte)0x80: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.mFmCurrentValue = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "mFmCurrentValue: " + Activity02.mFmCurrentValue);
                                    notifyMsgToRadioInterface(3);
                                    break;
                                }
                                case (byte)0x81: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.mFMm1Value = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "mFMm1Value: " + Activity02.mFMm1Value);
                                    notifyMsgToRadioInterface(7);
                                    break;
                                }
                                case (byte)0x82: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.mFMm2Value = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "mFMm2Value: " + Activity02.mFMm2Value);
                                    notifyMsgToRadioInterface(7);
                                    break;
                                }
                                case (byte)0x83: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.mFMm3Value = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "mFMm3Value: " + Activity02.mFMm3Value);
                                    notifyMsgToRadioInterface(7);
                                    break;
                                }
                                case (byte)0x84: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.mFMm4Value = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "mFMm4Value: " + Activity02.mFMm4Value);
                                    notifyMsgToRadioInterface(7);
                                    break;
                                }
                                case (byte)0x85: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.mFMm5Value = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "mFMm5Value: " + Activity02.mFMm5Value);
                                    notifyMsgToRadioInterface(7);
                                    break;
                                }
                                case (byte)0x86: {
                                    byte[] combineBytes = {bytes[5], bytes[4], (byte)0x01, (byte)0x00};
                                    Activity02.mFMm6Value = MathMethod.bytesToInt(combineBytes);
                                    Log.e("gallery", "mFMm6Value: " + Activity02.mFMm6Value);
                                    notifyMsgToRadioInterface(7);
                                    break;
                                }
                            }

                        }
                        else if(bytes[2] == (byte)0x86) {
                        //FF 77 86 XX 00 00 00 0D 0A    XX:  0X03-0X07 对应是M1-M6
                            Log.e("gallery", "order: 0x86");
                            switch(bytes[3]) {
                                case (byte)0x00: {
                                    notifyMsgToRadioInterface(55);
                                    break;
                                }
                                case (byte)0x01: {
                                    Activity02.mFMValueShowState = 1;
                                    break;
                                }
                                case (byte)0x02: {
                                    Activity02.mFMValueShowState = 2;
                                    break;
                                }
                                case (byte)0x03: {
                                    Activity02.whichBtnFocus = 1;
                                    break;
                                }
                                case (byte)0x04: {
                                    Activity02.whichBtnFocus = 2;
                                    break;
                                }
                                case (byte)0x05: {
                                    Activity02.whichBtnFocus = 3;
                                    break;
                                }
                                case (byte)0x06: {
                                    Activity02.whichBtnFocus = 4;
                                    break;
                                }
                                case (byte)0x07: {
                                    Activity02.whichBtnFocus = 5;
                                    break;
                                }
                                case (byte)0x08: {
                                    Activity02.whichBtnFocus = 6;
                                    break;
                                }
                                case (byte)0x0A: {
                                    if(bytes[4] == (byte)0x00) {
                                        Activity02.mStereoTextEnableFlag = false;
                                    }
                                    else if(bytes[4] == (byte)0x01) {
                                        Activity02.mStereoTextEnableFlag = true;
                                    }
                                    break;
                                }
                            }
                            Log.e("gallery", "whichBtnFocus: " + Activity02.whichBtnFocus);
                            notifyMsgToRadioInterface(5);
                        }
                        else if(bytes[2] == (byte)0xFE) {
                            Log.e("gallery", "get key bytes[2] = " + bytes[2]);
                            Log.e("gallery", "get key bytes[5] = " + bytes[5]);
                            if(bytes[5] == MathMethod.contentForSecurity) {
                                MathMethod.receiveForSecurity = bytes[5];
                                Log.e("gallery", "get key bytes[5] = contentForSecurity = " + MathMethod.contentForSecurity);

                                notifyMeFriWill(2);
                            }
                        }
                        else if(bytes[2] == (byte)0x8B) {
                            //FF 77 8B 05 01 00 00 00 0D 0A  //BEEP ON
                            Log.e("gallery", "order: 0x8B");
                            switch(bytes[3]) {
                                case (byte)0x00: {
                                    //FF 77 8B 00 00 00 00 00 0D 0A
                                    if(bytes[4] == (byte)0x00) {
                                        SettingActivity.mlist_preference_eqIndex = 0;
                                    }
                                    else if(bytes[4] == (byte)0x01) {
                                        SettingActivity.mlist_preference_eqIndex = 1;
                                    }
                                    else if(bytes[4] == (byte)0x02) {
                                        SettingActivity.mlist_preference_eqIndex = 2;
                                    }
                                    else if(bytes[4] == (byte)0x03) {
                                        SettingActivity.mlist_preference_eqIndex = 3;
                                    }
                                    else if(bytes[4] == (byte)0x04) {
                                        SettingActivity.mlist_preference_eqIndex = 4;
                                    }
                                    notifyMsgToRadioInterface(101);
                                    break;
                                }
                                case (byte)0x03: {
                                    PreferencesFromXml.seekBarSwithFlag = true;
                                    //FF 77 8B 03 XX 00 00 00 0D 0A
                                    SettingActivity.mbalance_preferenceIndex = MathMethod.byteHEXtoInt(bytes[4]);
                                    notifyMsgToRadioInterface(102);
                                    break;
                                }
                                case (byte)0x04: {
                                    PreferencesFromXml.seekBarSwithFlag = false;
                                    //FF 77 8B 03 XX 00 00 00 0D 0A
                                    SettingActivity.mfader_preferenceIndex = MathMethod.byteHEXtoInt(bytes[4]);
                                    notifyMsgToRadioInterface(103);
                                    break;
                                }
                                case (byte)0x05: {
                                    if(bytes[4] == (byte)0x01) {
                                        //PreferencesFromXml.beepFlag = true;
                                        //Log.e("gallery", "beepFlag = " + PreferencesFromXml.beepFlag);
                                    }
                                    else if(bytes[4] == (byte)0x00) {
                                        //PreferencesFromXml.beepFlag = false;
                                        //Log.e("gallery", "beepFlag = " + PreferencesFromXml.beepFlag);
                                    }
                                    break;
                                }
                                case (byte)0x06: {
                                    if(bytes[4] == (byte)0x00) {
                                        SettingActivity.loudnessFlag = true;
                                    }
                                    else if(bytes[4] == (byte)0x01) {
                                        SettingActivity.loudnessFlag = false;
                                    }
                                    notifyMsgToRadioInterface(104);
                                    break;
                                }

                                case -1:
                                    break;
                            }
                            if(PreferencesFromXml.controlFlag) {
                                //notifyMsgToRadioInterface(6);
                            }
                        }
                        else if(bytes[2] == (byte)0x87) {
                            //Log.e("gallery", "order: 0x87");
                            switch(bytes[3]) {
                                case (byte)0x00:
                                    Activity02.tabUSBKillflag = true;
                                    Activity02.tabSDKillflag = true;
                                    notifyMsgToRadioInterface(11);
                                    break;
                                case (byte)0x01:
                                    Activity02.tabUSBKillflag = false;
                                    Activity02.tabSDKillflag = true;
                                    notifyMsgToRadioInterface(11);
                                    break;
                                case (byte)0x02:
                                    Activity02.tabUSBKillflag = true;
                                    Activity02.tabSDKillflag = false;
                                    notifyMsgToRadioInterface(11);
                                    break;
                                case (byte)0x03:
                                    Activity02.tabUSBKillflag = false;
                                    Activity02.tabSDKillflag = false;
                                    notifyMsgToRadioInterface(11);
                                    break;
                                case (byte)0x04: {
                                    if(bytes[4] == (byte)0x00) {
                                        if(Activity02.usbOrSD) {
                                            Activity02.usbPlayOrPauseFlag = true;
                                            Activity02.usbStopFlag = false;
                                        }
                                        else {
                                            Activity02.sdPlayOrPauseFlag = true;
                                            Activity02.sdStopFlag = false;
                                        }
                                    }
                                    else if(bytes[4] == (byte)0x01) {
                                        if(Activity02.usbOrSD) {
                                            Activity02.usbPlayOrPauseFlag = false;
                                            Activity02.usbStopFlag = false;
                                        }
                                        else {
                                            Activity02.sdPlayOrPauseFlag = false;
                                            Activity02.sdStopFlag = false;
                                        }
                                    }
                                    else if(bytes[4] == (byte)0x02) {
                                        if(Activity02.usbOrSD)
                                            Activity02.usbStopFlag = true;
                                        else
                                            Activity02.sdStopFlag = true;
                                    }
                                    else if(bytes[4] == (byte)0x03) {
                                        if(Activity02.usbOrSD)
                                            Activity02.mUSBPreNextShortClickEnable = true;
                                        //else
                                            //Activity02.mSDPreNextShortClickEnable = true;
                                    }
                                    else if(bytes[4] == (byte)0x04) {
                                        if(Activity02.usbOrSD)
                                            Activity02.mUSBPreNextShortClickEnable = true;
                                        //else
                                            //Activity02.mSDPreNextShortClickEnable = true;
                                    }
                                    notifyMsgToRadioInterface(13);
                                    break;
                                }
                                case (byte)0x05: {
                                    if(bytes[4] == (byte)0x00) {
                                        if(Activity02.usbOrSD)
                                            Activity02.usbPlayMode = 0;
                                        else
                                            Activity02.sdPlayMode = 0;
                                    }
                                    else if(bytes[4] == (byte)0x01) {
                                        if(Activity02.usbOrSD)
                                            Activity02.usbPlayMode = 1;
                                        else
                                            Activity02.sdPlayMode = 1;
                                    }
                                    else if(bytes[4] == (byte)0x02) {
                                        if(Activity02.usbOrSD)
                                            Activity02.usbPlayMode = 2;
                                        else
                                            Activity02.sdPlayMode = 2;
                                    }
                                    else if(bytes[4] == (byte)0x03) {
                                        if(Activity02.usbOrSD)
                                            Activity02.usbPlayMode = 3;
                                        else
                                            Activity02.sdPlayMode = 3;
                                    }
                                    notifyMsgToRadioInterface(16);
                                    break;
                                }
                                case (byte)0x06: {
                                    byte[] currentMusicIndexbyte = { bytes[5], bytes[4], (byte)0, (byte)0 };
                                    Activity02.usbCurrentIndex = MathMethod.bytesToInt(currentMusicIndexbyte);
                                    notifyMsgToRadioInterface(14);
                                    break;
                                }
                                case (byte)0x07: {
                                    byte[] totalMusicNumbyte = { bytes[5], bytes[4], (byte)0, (byte)0 };
                                    Activity02.usbTotalNum = MathMethod.bytesToInt(totalMusicNumbyte);
                                    notifyMsgToRadioInterface(14);
                                    break;
                                }
                                case (byte)0x08: {
                                    byte[] musicCurrentTimebyte = { bytes[5], bytes[4], (byte)0, (byte)0 };
                                    Activity02.usbCurrentTime = MathMethod.bytesToInt(musicCurrentTimebyte);
                                    //Log.e("gallery", "08 usbCurrentTime = " + Activity02.usbCurrentTime);
                                    notifyMsgToRadioInterface(14);
                                    break;
                                }
                                case (byte)0x09: {
                                    byte[] musicTotalTimebyte = { bytes[5], bytes[4], (byte)0, (byte)0 };
                                    Activity02.usbTotalTime = MathMethod.bytesToInt(musicTotalTimebyte);
                                    Log.e("gallery", "09 musicTotalTime = " + Activity02.usbTotalTime);
                                    notifyMsgToRadioInterface(14);

                                    break;
                                }
                            }

                        }
                        else if(bytes[2] == (byte)0x88) {
                            //FF 77 88 Tn 0D 0A  //music title
                            Log.e("gallery", "order: 0x88");
                            Arrays.fill(bytesForChar, (byte)0);
                            int charsLength = MathMethod.byteHEXtoInt(bytes[3]);
                            Log.e("gallery", "charsLength =========> " + charsLength+2);
                            if(charsLength == 0) {
                                Activity02.mUSBAuthorTextStr = "Unknown title";
                            }
                            else {
                                inputStream.read(bytesForChar, 0, charsLength+2);
                                //Log.e("gallery", "bytesForChar =========> " + new String(bytesForChar, "GBK"));
                                Activity02.mUSBAuthorTextStr = new String(bytesForChar, "GBK");
                            }
                            notifyMsgToRadioInterface(15);
                        }
                        else if(bytes[2] == (byte)0x89) {
                            //FF 77 88 Tn 0D 0A  //music title
                            Log.e("gallery", "order: 0x89");
                            Arrays.fill(bytesForChar, (byte)0);
                            int charsLength = MathMethod.byteHEXtoInt(bytes[3]);
                            Log.e("gallery", "charsLength =========> " + charsLength+2);
                            if(charsLength == 0) {
                                Activity02.uSBalbumText89 = "Unknown Album";
                            }
                            else {
                                inputStream.read(bytesForChar, 0, charsLength+2);
                                //Log.e("gallery", "bytesForChar =========> " + new String(bytesForChar, "GBK"));
                                Activity02.uSBalbumText89 = new String(bytesForChar, "GBK");
                            }
                            notifyMsgToRadioInterface(15);
                        }
                        else if(bytes[2] == (byte)0x8A) {
                            //FF 77 88 Tn 0D 0A  //music title
                            Log.e("gallery", "order: 0x8A");
                            Arrays.fill(bytesForChar, (byte)0);
                            int charsLength = MathMethod.byteHEXtoInt(bytes[3]);
                            Log.e("gallery", "charsLength =========> " + charsLength+2);
                            if(charsLength == 0) {
                                Activity02.uSBalbumText8A = "Unknown artist";
                            }
                            else {
                                inputStream.read(bytesForChar, 0, charsLength+2);
                                //Log.e("gallery", "bytesForChar =========> " + new String(bytesForChar, "GBK"));
                                Activity02.uSBalbumText8A = new String(bytesForChar, "GBK");
                            }

                            if(Activity02.usbOrSD)
                                Activity02.mUSBPreNextShortClickEnable = true;
                            //else
                                //Activity02.mSDPreNextShortClickEnable = true;

                            notifyMsgToRadioInterface(15);
                        }
                        else if(bytes[2] == (byte)0x8C) {
                            //FF 77 88 Tn 0D 0A  //music title
                            Log.e("gallery", "order: 0x8C");
                            Arrays.fill(bytesForChar, (byte)0);
                            int charsLength = MathMethod.byteHEXtoInt(bytes[3]);
                            Log.e("gallery", "charsLength =========> " + charsLength+2);
                            inputStream.read(bytesForChar, 0, charsLength+2);
                            byte[] bytesForChar2 = MathMethod.byteCharToByte(bytesForChar);
                            //Log.e("gallery", "bytesForChar =========> " + new String(bytesForChar2, "Unicode"));
                            Activity02.mUSBAlbumTextStr = new String(bytesForChar2, "Unicode");
                            notifyMsgToRadioInterface(15);

                            //notifyMsgToRadioInterface(100);
                            if(Activity02.usbOrSD)
                                Activity02.mUSBPreNextShortClickEnable = true;
                            //else
                                //Activity02.mSDPreNextShortClickEnable = true;
                        }
                        else if(bytes[2] == (byte)0x8D) {
                            Log.e("gallery", "order: 0x8D");
                            Arrays.fill(bytesForChar, (byte)0);
                            int charsLength = MathMethod.byteHEXtoInt(bytes[3]);
                            Log.e("gallery", "charsLength =========> " + charsLength+2);
                            inputStream.read(bytesForChar, 0, charsLength+2);
                            //Log.e("gallery", "bytesForChar =========> " + new String(bytesForChar, "GBK"));
                            Activity02.mUSBAlbumTextStr = new String(bytesForChar, "GBK");
                            notifyMsgToRadioInterface(15);

                            //notifyMsgToRadioInterface(100);
                            if(Activity02.usbOrSD)
                                Activity02.mUSBPreNextShortClickEnable = true;
                            //else
                                //Activity02.mSDPreNextShortClickEnable = true;
                        }
                        else if(bytes[2] == (byte)0x8E) {
                            //FF 77 88 Tn 0D 0A  //music title
                            Log.e("gallery", "order: 0x8C");
                            Arrays.fill(bytesForChar, (byte)0);
                            int charsLength = MathMethod.byteHEXtoInt(bytes[3]);
                            Log.e("gallery", "charsLength =========> " + charsLength+2);
                            inputStream.read(bytesForChar, 0, charsLength+2);
                            Log.e("gallery", "bytesForChar =========> " + new String(bytesForChar, "GBK"));
                            Activity02.mUSBAlbumTextStr = new String(bytesForChar, "GBK");
                            notifyMsgToRadioInterface(15);
                        }

                        //if(bytes[0] == (byte)0xff) {
                            //if(bytes[1] == (byte)0x77) {
                                sendBlueOrderResponseEmpty();
                                BluetoothDemo.receiveCount = BluetoothDemo.receiveCount + read;
                            //}
                        //}

                        if(receiveCount > 7) {
                            //notifyMsgToRadioInterface(10);
                        }

                        Arrays.fill(bytes, (byte)0);
                        receiveContent = "";
                    }

                    Log.e("gallery", " ===================================> get out receive === for === loop");

                } catch (IOException connectException) {
                    Log.e("gallery", "=================== run loop catch IOException ===================== ");

                    BluetoothDemo.receiveFlag = false;

                    try {
                        if (BluetoothDemo.outStream != null) {
                            BluetoothDemo.outStream.close();
                            BluetoothDemo.outStream = null;
                        }
                        if (BluetoothDemo.inputStream != null) {
                            BluetoothDemo.inputStream.close();
                            BluetoothDemo.inputStream = null;
                        }
                        if (BluetoothDemo.btSocket != null) {
                            BluetoothDemo.btSocket.close();
                            BluetoothDemo.btSocket = null;
                        }
                    } catch (IOException ioExceptionioException) {
                        Log.e("gallery", "=================== run loop catch IOExceptionIOException ===================== ");
                    }

                    return;
                }

                Log.e("gallery", " ===================================> get out receive === while === loop");
            }

            Log.e("gallery", " ===================================> get out receive loop");
            notifyMsgToRadioInterface(202);
        }

        /** Will cancel an in-progress connection, and close the socket */
        //gallery--temporary
        /*public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }*/
    }


    public boolean createBond(BluetoothDevice btDevice) throws Exception {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    class ButtonClicked implements OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonOn:
                    onBluetooth();
                    break;
                case R.id.buttonSearch:
                    arrayListBluetoothDevices.clear();
                    startSearching();
                    break;
                case R.id.buttonDesc:
                    makeDiscoverable();
                    break;
                case R.id.buttonOff:
                    offBluetooth();
                    break;
                default:
                    break;
            }
        }
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Message msg = Message.obtain();
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                Toast.makeText(context, "ACTION_FOUND", Toast.LENGTH_SHORT).show();

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try {
                    //device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
                    //device.getClass().getMethod("cancelPairingUserInput", boolean.class).invoke(device);
                }
                catch (Exception e) {
                    Log.i("Log", "Inside the exception: ");
                    e.printStackTrace();
                }

                if(arrayListBluetoothDevices.size()<1) // this checks if the size of bluetooth device is 0,then add the
                {                                           // device to the arraylist.
                    detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                    arrayListBluetoothDevices.add(device);
                    detectedAdapter.notifyDataSetChanged();
                }
                else
                {
                    boolean flag = true;    // flag to indicate that particular device is already in the arlist or not
                    for(int i = 0; i<arrayListBluetoothDevices.size();i++)
                    {
                        if(device.getAddress().equals(arrayListBluetoothDevices.get(i).getAddress()))
                        {
                            flag = false;
                        }
                    }
                    if(flag == true)
                    {
                        detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                        arrayListBluetoothDevices.add(device);
                        detectedAdapter.notifyDataSetChanged();
                    }
                }
                if(device.getName().toUpperCase().contains("RADIO")) {
                    _discoveryFinished = true;
                }
            }
        }
    };

    private void startSearching() {
        Log.i("Log", "in the start searching method");
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        BluetoothDemo.this.registerReceiver(myReceiver, intentFilter);

        //gallery==replace
        //bluetoothAdapter.startDiscovery();
        SamplesUtils.indeterminate(BluetoothDemo.this, _handler, "Scanning...", _discoveryWorkder, new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                for (; bluetoothAdapter.isDiscovering();) {
                    bluetoothAdapter.cancelDiscovery();
                }
                _discoveryFinished = true;
            }
        }, true);
    }

    private void onBluetooth() {
        if(!bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.enable();
            Log.i("Log", "Bluetooth is Enabled");
        }
    }

    private void offBluetooth() {
        if(bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.disable();
        }
    }

    private void makeDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Log.i("Log", "Discoverable ");
    }

    public void notifyMeFriWill(int flag) {
        Message msg = new Message();
        msg.what = flag;
        handlerWel.sendMessage(msg);
    }

    public static void notifyMsgToRadioInterface(int flag) {
        Message msg = new Message();
        msg.what = flag;
        Activity02.handlerRadioInterface.sendMessage(msg);
    }

    public static void sendBlueOrder(byte[] orderBytes) {
        if(connectFlag) {
            try {
                Log.e("gallery", "send =-------=> " + MathMethod.byte2HexStr(orderBytes));
                outStream.write(orderBytes);
                outStream.flush();
            } catch (java.io.IOException e) {
                Log.e("gallery ===> ", "============== OutStream send fail");
            } catch (java.lang.NullPointerException e2) {
                Toast.makeText(Activity02.galleryActivity, "Disconnecting bluetooth !", Toast.LENGTH_LONG).show();
                if(Activity02.usbOrSD) {
                    MathMethod.writeSharedPreferences("usbORsd", "USB");
                }
                else {
                    MathMethod.writeSharedPreferences("usbORsd", "SD");
                }
                BluetoothDemo.receiveFlag = false;
                notifyMsgToRadioInterface(202);
            }

        }
    }

    public void sendBlueOrderResponseEmpty() {
        if(connectFlag) {
            try {
                //Log.e("gallery", "send =--------=> " + MathMethod.byte2HexStr(blueOrderResponseEmpty));
                outStream.write(blueOrderResponseEmpty);
                outStream.flush();
            } catch (java.io.IOException e) {
                Log.e("gallery ===> ", "============== OutStream send fail");
            }
        }
    }

    class HandleSeacrh extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    break;

                default:
                    break;
            }
        }
    }

    private static void openSecurityWindow() {
        /*final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        final EditText input = new EditText(mContext);
        input.setKeyListener(new DigitsKeyListener(false,true));
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        alert.setView(input);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String passwordValue = input.getText().toString().trim();
                if(passwordValue.length() == 6) {
                    int valueSum = 0;
                    for(int i=0; i<passwordValue.length(); ) {
                        int wordXX = Integer.parseInt(passwordValue.substring(i, i+1));
                        int wordYY = Integer.parseInt(passwordValue.substring(i, i+1));
                        valueSum = valueSum + wordXX * wordYY;
                        i = i + 2;
                    }
                    byte valueSumByte = (byte)valueSum;
                    byte sendCiphertext = (byte) (valueSumByte ^ MathMethod.contentForSecurity);
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x7F, (byte)0x00, (byte)0x00, sendCiphertext, (byte)0x00, (byte)0x0D, (byte)0x0A};
                    sendBlueOrder(blueOrder);
                }
                else {
                    Toast.makeText(mContext, "The length of password should be 6", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();*//*final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        final EditText input = new EditText(mContext);
        input.setKeyListener(new DigitsKeyListener(false,true));
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        alert.setView(input);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String passwordValue = input.getText().toString().trim();
                if(passwordValue.length() == 6) {
                    int valueSum = 0;
                    for(int i=0; i<passwordValue.length(); ) {
                        int wordXX = Integer.parseInt(passwordValue.substring(i, i+1));
                        int wordYY = Integer.parseInt(passwordValue.substring(i, i+1));
                        valueSum = valueSum + wordXX * wordYY;
                        i = i + 2;
                    }
                    byte valueSumByte = (byte)valueSum;
                    byte sendCiphertext = (byte) (valueSumByte ^ MathMethod.contentForSecurity);
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x7F, (byte)0x00, (byte)0x00, sendCiphertext, (byte)0x00, (byte)0x0D, (byte)0x0A};
                    sendBlueOrder(blueOrder);
                }
                else {
                    Toast.makeText(mContext, "The length of password should be 6", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();*/

        String passwordValue = "862193";
        if(passwordValue.length() == 6) {
            int valueSum = 0;
            for(int i=0; i<passwordValue.length(); ) {
                int wordXX = Integer.parseInt(passwordValue.substring(i, i+1));
                int wordYY = Integer.parseInt(passwordValue.substring((i+1), i+2));
                valueSum = valueSum + wordXX * wordYY;
                i = i + 2;
            }
            byte valueSumByte = (byte)valueSum;
            byte sendCiphertext = (byte) (valueSumByte ^ MathMethod.contentForSecurity);
            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x7F, (byte)0x00, (byte)0x00, sendCiphertext,
                    (byte)0x00, (byte)0x0D, (byte)0x0A};
            Log.e("gallery", "send ciphertext: " + MathMethod.byte2HexStr(blueOrder));
            sendBlueOrder(blueOrder);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("gallery", "requestCode === " + requestCode);
        if(requestCode == 0) {
            notifyMeFriWill(4);

            int state = bluetoothAdapter.getState();
            if (state == BluetoothAdapter.STATE_OFF) {
                connectFlag = false;
                if(!connectFlag) {
                    Intent intent = new Intent(BluetoothDemo.this, Activity02.class);
                    BluetoothDemo.this.startActivity(intent);
                    BluetoothDemo.this.finish();
                }
            }
            else {
                if(dTask == null)
                    dTask = new DownloadTask();
                dTask.execute();
            }
        }
    }

    class DownloadTask extends AsyncTask<Integer, Integer, String> {
        //后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值 类型

        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();

            Log.e("gallery", "onPreExecute while receiveFlag =================== " + receiveFlag);

            progressBar = new CustomProgressDialog(mContext);
            progressBar.setCancelable(true);
            progressBar.setMessage("Connect Bluetoothe Device ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.getWindow().setGravity(Gravity.CENTER);
            progressBar.show();

            getPairedDevices();
        }

        @Override
        protected String doInBackground(Integer... params) {
            Log.e("gallery", "arrayListPairedBluetoothDevices size === " + arrayListPairedBluetoothDevices.size());
            for(int ddd=0; ddd<arrayListPairedBluetoothDevices.size(); ddd++) {
                if(!connectLoopFlag) {
                    break;
                }
                bdDevice = arrayListPairedBluetoothDevices.get(ddd);
                if(bdDevice.getName().toUpperCase().contains("RADIO")) {
                    ConnectThread ctxxx = new ConnectThread(bdDevice);
                    if(receiveFlag) {
                        connectLoopFlag = false;
                        ctxxx.start();
                    }
                    else {
                        connectLoopFlag = true;
                        ctxxx.mmDevice = null;
                        ctxxx.mmSocket = null;
                        Log.e("gallery", "Paired connect loop");
                    }
                }
            }
            Log.e("gallery", "doInBackground while receiveFlag =================== " + receiveFlag);
            return "执行完毕";
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e("gallery", "onPostExecute while receiveFlag =================== " + receiveFlag);
            if(!receiveFlag) {
                notifyMeFriWill(3);
            }
            if(progressBar != null) {
                progressBar.dismiss();
            }
            super.onPostExecute(result);
        }

    }
}


/*
byte[] arrayOfByte1 = new byte[1024];

while (true) {
        int j;
        try {
            int i = this.mmInStream.read(arrayOfByte1);
            if (i <= 0)
                continue;
            byte[] arrayOfByte2 = (byte[])arrayOfByte1.clone();
            j = 0;
            if (j >= arrayOfByte1.length) {
                BluetoothChatService.this.mHandler.obtainMessage(2, i, -1, arrayOfByte2).sendToTarget();
                continue;
            }
        }
        catch (IOException localIOException) {
            Log.e("BluetoothChatService", "disconnected", localIOException);
            BluetoothChatService.this.connectionLost();
            return;
        }
        arrayOfByte1[j] = 0;
        j++;
}*/
