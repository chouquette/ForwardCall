package fr.mustelidae.ForwardCall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.widget.Toast;

public final class FireReceiver extends BroadcastReceiver {

	@Override
	public void onReceive( final Context context, final Intent intent) 
	{
		if ( com.twofortyfouram.Intent.ACTION_FIRE_SETTING.equals( intent.getAction() ) )
		{
//			Toast.makeText( context, "Something happened !", Toast.LENGTH_LONG ).show();
			final boolean activated = intent.getBooleanExtra(Constants.INTENT_FORWARD_ACTIVATED, false);

			if ( activated )
				Toast.makeText( context, "Forwarding activated", Toast.LENGTH_LONG ).show();
			else
				Toast.makeText( context, "Forwarding deactivated", Toast.LENGTH_LONG ).show();
		}
	}

}
