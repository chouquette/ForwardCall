package fr.mustelidae.ForwardCall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.util.Log;

public final class FireReceiver extends BroadcastReceiver {

	@Override
	public void onReceive( final Context context, final Intent intent) 
	{
		if ( com.twofortyfouram.Intent.ACTION_FIRE_SETTING.equals( intent.getAction() ) )
		{
//			Toast.makeText( context, "Something happened !", Toast.LENGTH_LONG ).show();
			final boolean activated = intent.getBooleanExtra(Constants.INTENT_FORWARD_ACTIVATED, false);

			if ( activated )
			{
				final String	phoneNumber = intent.getStringExtra( Constants.INTENT_PHONE_NUMBER );
				enableCallForwarding(context, phoneNumber);
			}
			else
			{
				disableCallForwarding( context );
			}
		}
	}
	
	private void	enableCallForwarding( Context context, final String phoneNumber )
	{
		final String	toDial = "tel:**21*" + phoneNumber + Uri.encode("#");

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
	
	private void	disableCallForwarding( Context context )
	{
		final String	sharpEncoded = Uri.encode("#");
		final String	toDial = String.format("tel:%s%s21%s", sharpEncoded, sharpEncoded, sharpEncoded );

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
