package fr.mustelidae.ForwardCall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;

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
			{
				Toast.makeText( context, "Forwarding deactivated", Toast.LENGTH_LONG ).show();
				disableCallForwarding( context );
			}
		}
	}
	private void	disableCallForwarding( Context context )
	{
		final String	toDial = "tel:##21#";

		try
		{
			Intent	intent = new Intent( Intent.ACTION_CALL, Uri.parse( toDial ) );
			intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
			context.startActivity( intent );
		}
		catch (Exception e)
		{
			Log.e("ForwardCall", e.getMessage());
		}
		
	}

}
