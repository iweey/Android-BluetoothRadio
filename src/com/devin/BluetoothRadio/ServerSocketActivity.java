package com.devin.BluetoothRadio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

public class ServerSocketActivity extends ListActivity
{

	public static final String PROTOCOL_SCHEME_L2CAP = "btl2cap";
	public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";
	public static final String PROTOCOL_SCHEME_BT_OBEX = "btgoep";
	public static final String PROTOCOL_SCHEME_TCP_OBEX = "tcpobex";
	private static final String TAG = ServerSocketActivity.class.getSimpleName();
	private Handler _handler = new Handler();

	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();

	private BluetoothServerSocket _serverSocket;

	private Thread _serverWorker = new Thread() {
		public void run() {
			listen();
		};
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.server_socket);
		if (!_bluetooth.isEnabled()) {
			finish();
			return;
		}

		_serverWorker.start();
	}
	protected void onDestroy() {
		super.onDestroy();
		shutdownServer();
	}
	protected void finalize() throws Throwable {
		super.finalize();
		shutdownServer();
	}

	private void shutdownServer() {
		new Thread() {
			public void run() {
				_serverWorker.interrupt();
				if (_serverSocket != null) {
					try {

						_serverSocket.close();
					} catch (IOException e) {
						Log.e(TAG, "", e);
					}
					_serverSocket = null;
				}
			};
		}.start();
	}
	public void onButtonClicked(View view) {
		shutdownServer();
	}
	protected void listen() {
		try {

			_serverSocket = _bluetooth.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,
					UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));

			final List<String> lines = new ArrayList<String>();
			_handler.post(new Runnable() {
				public void run() {
					lines.add("Rfcomm server started...");
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							ServerSocketActivity.this,
							android.R.layout.simple_list_item_1, lines);
					setListAdapter(adapter);
				}
			});

			BluetoothSocket socket = _serverSocket.accept();

			if (socket != null) {
				InputStream inputStream = socket.getInputStream();
				int read = -1;
				final byte[] bytes = new byte[2048];
				for (; (read = inputStream.read(bytes)) > -1;) {
					final int count = read;
					_handler.post(new Runnable() {
						public void run() {
							StringBuilder b = new StringBuilder();
							for (int i = 0; i < count; ++i) {
								if (i > 0) {
									b.append(' ');
								}
								String s = Integer.toHexString(bytes[i] & 0xFF);
								if (s.length() < 2) {

									b.append('0');
								}
								b.append(s);
							}
							String s = b.toString();
							lines.add(s);
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									ServerSocketActivity.this,
									android.R.layout.simple_list_item_1, lines);
							setListAdapter(adapter);
						}
					});
				}
			}
		} catch (IOException e) {
			Log.e(TAG, "", e);
		} finally {

		}
	}
}

