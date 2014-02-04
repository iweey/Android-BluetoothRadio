package com.devin.BluetoothRadio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class ClientSocketActivity  extends Activity
{
    public static OutputStream outStream = null;
    private static final String TAG = ClientSocketActivity.class.getSimpleName();
	private static final int REQUEST_DISCOVERY = 0x1;;
	private Handler _handler = new Handler();
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.client_socket);
		if (!_bluetooth.isEnabled()) {
			finish();
			return;
		}
		Intent intent = new Intent(this, DiscoveryActivity.class);

		Toast.makeText(this, "select device to connect", Toast.LENGTH_SHORT).show();

		startActivityForResult(intent, REQUEST_DISCOVERY);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != REQUEST_DISCOVERY) {
			return;
		}
		if (resultCode != RESULT_OK) {
			return;
		}
		final BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		new Thread() {
			public void run() {

				connect(device);
			};
		}.start();
	}
	protected void connect(BluetoothDevice device) {
		BluetoothSocket socket = null;
		try {
			// socket = device.createRfcommSocketToServiceRecord(BluetoothProtocols.OBEX_OBJECT_PUSH_PROTOCOL_UUID);
			socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			socket.connect();

		} catch (IOException e) {
			Log.e(TAG, "", e);
            try {
                socket.close();
            } catch (IOException ex) {
                Log.e(TAG, "", ex);
            }
		}
        //gallery++
        try {
            outStream = socket.getOutputStream();
        } catch (IOException e) {
        }

        //gallery--
        /*finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
			}
		}*/
	}
}

