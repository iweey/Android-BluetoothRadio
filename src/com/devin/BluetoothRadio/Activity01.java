package com.devin.BluetoothRadio;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity01 extends Activity
{

	private BluetoothAdapter	_bluetooth				= BluetoothAdapter.getDefaultAdapter();


	private static final int	REQUEST_ENABLE			= 0x1;

	private static final int	REQUEST_DISCOVERABLE	= 0x2;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}



	public void onEnableButtonClicked(View view)
	{

		//Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		//startActivityForResult(enabler, REQUEST_ENABLE);

		_bluetooth.enable();
	}



	public void onDisableButtonClicked(View view)
	{

		_bluetooth.disable();
	}



	public void onMakeDiscoverableButtonClicked(View view)
	{

		Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		startActivityForResult(enabler, REQUEST_DISCOVERABLE);
	}



	public void onStartDiscoveryButtonClicked(View view)
	{

		Intent enabler = new Intent(this, DiscoveryActivity.class);
		startActivity(enabler);
	}



	public void onOpenClientSocketButtonClicked(View view)
	{

		Intent enabler = new Intent(this, ClientSocketActivity.class);
		startActivity(enabler);
	}



	public void onOpenServerSocketButtonClicked(View view)
	{

		Intent enabler = new Intent(this, ServerSocketActivity.class);
		startActivity(enabler);
	}



	public void onOpenOBEXServerSocketButtonClicked(View view)
	{

		Intent enabler = new Intent(this, OBEXActivity.class);
		startActivity(enabler);
	}

    public void sendSocketButtonClicked(View view)
    {
        String msgBufferString = "Hello";
        byte[] msgBuffer = msgBufferString.getBytes();
        try {
            ClientSocketActivity.outStream.write(msgBuffer);
        } catch (java.io.IOException e) {

        }
    }
}
