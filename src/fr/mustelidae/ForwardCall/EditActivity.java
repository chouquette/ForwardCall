package fr.mustelidae.ForwardCall;

import com.twofortyfouram.SharedResources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class EditActivity extends Activity 
{
	private static final int	MENU_SAVE = 1;
	private static final int	MENU_DONT_SAVE = 2;
	private boolean				isCancelled = false;
	
	@Override
	public void	onCreate( final Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.main );
		CheckBox	checkBox = (CheckBox)findViewById( R.id.activated );
		TextView	phoneNumberInput = (TextView)findViewById( R.id.phone_number );
		
		checkBox.setOnCheckedChangeListener( new OnActivatedChecked( phoneNumberInput) );
		phoneNumberInput.setEnabled( checkBox.isChecked() );
		
		final String breadcrumbString = getIntent().getStringExtra( com.twofortyfouram.Intent.EXTRA_STRING_BREADCRUMB );
		if ( breadcrumbString != null )
			setTitle( String.format( "%s%s%s", breadcrumbString, com.twofortyfouram.Intent.BREADCRUMB_SEPARATOR, getString(R.string.app_name) ) );
		
		((LinearLayout)findViewById( R.id.frame )).setBackgroundDrawable( SharedResources.getDrawableResource(getPackageManager(), SharedResources.DRAWABLE_LOCALE_BORDER ) );
		
		if ( savedInstanceState == null )
		{
			final Bundle forwaredBundle = getIntent().getBundleExtra(com.twofortyfouram.Intent.EXTRA_BUNDLE);
			if ( forwaredBundle != null )
			{
				final String text = getIntent().getStringExtra( Constants.INTENT_PHONE_NUMBER );
				if ( text != null )
				{
					((EditText)findViewById(R.id.phone_number)).setText(text);
				}
			}
		}
	}
	
	@Override
	public void	finish()
	{
		if ( isCancelled )
			setResult(RESULT_CANCELED);
		else
		{
			final boolean	activated = ((CheckBox) findViewById(R.id.activated)).isChecked();
			final String	phoneNumber = ((EditText) findViewById(R.id.phone_number)).getText().toString();
			final String	blurb;
			
			if ( activated == true )
				blurb = phoneNumber;
			else
				blurb = "OFF";
			if ( activated == true && phoneNumber.length() == 0 )
			{
				setResult( com.twofortyfouram.Intent.RESULT_REMOVE );
			}
			else
			{
				final Intent 	returnIntent = new Intent();
				final Bundle	storeAndForwardExtras = new Bundle();
				
				storeAndForwardExtras.putString(Constants.INTENT_PHONE_NUMBER, phoneNumber );
				storeAndForwardExtras.putBoolean(Constants.INTENT_FORWARD_ACTIVATED, activated );

				returnIntent.putExtra( com.twofortyfouram.Intent.EXTRA_BUNDLE, storeAndForwardExtras );
				if ( phoneNumber.length() > com.twofortyfouram.Intent.MAXIMUM_BLURB_LENGTH )
					returnIntent.putExtra( com.twofortyfouram.Intent.EXTRA_STRING_BLURB, blurb.substring(0, com.twofortyfouram.Intent.MAXIMUM_BLURB_LENGTH ) );
				else
					returnIntent.putExtra( com.twofortyfouram.Intent.EXTRA_STRING_BLURB, blurb );
				setResult( RESULT_OK, returnIntent );
			}
		}
		super.finish();
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(findViewById(R.id.phone_number).getWindowToken(), 0 );
	}
	
	@Override
	public boolean onCreateOptionsMenu( final Menu menu )
	{
		super.onCreateOptionsMenu(menu);
		
		final PackageManager	pm = getPackageManager();
		final Intent			helpIntent = new Intent(com.twofortyfouram.Intent.ACTION_HELP);
		
		helpIntent.putExtra(com.twofortyfouram.Intent.EXTRA_STRING_HELP_URL, "http://www.google.com" );
		helpIntent.putExtra(com.twofortyfouram.Intent.EXTRA_STRING_BREADCRUMB, getTitle().toString() );
		
		menu.add( SharedResources.getTextResource(pm, SharedResources.STRING_MENU_HELP )).
			setIcon( SharedResources.getDrawableResource(pm, SharedResources.DRAWABLE_MENU_HELP ) ).setIntent(helpIntent);
		menu.add( 0, MENU_DONT_SAVE, 0, SharedResources.getTextResource(pm, SharedResources.STRING_MENU_DONTSAVE ) ).
			setIcon( SharedResources.getDrawableResource(pm, SharedResources.DRAWABLE_MENU_DONTSAVE ) ).getItemId();
		menu.add( 0, MENU_SAVE, 0, SharedResources.getTextResource( pm, SharedResources.STRING_MENU_SAVE ) ).
			setIcon( SharedResources.getDrawableResource( pm, SharedResources.DRAWABLE_MENU_SAVE ) ).getItemId();
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected( final int featureId, final MenuItem item )
	{
		switch ( item.getItemId() )
		{
			case MENU_SAVE:
			{
				finish();
				return true;
			}
			case MENU_DONT_SAVE:
			{
				isCancelled = true;
				finish();
				return true;
			}
		}
		return super.onOptionsItemSelected( item );
	}
}
