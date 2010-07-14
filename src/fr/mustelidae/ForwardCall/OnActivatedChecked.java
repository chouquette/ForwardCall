package fr.mustelidae.ForwardCall;

import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class OnActivatedChecked implements OnCheckedChangeListener 
{
	TextView	m_toActivate;
	
	public	OnActivatedChecked( TextView toActivate )
	{
		m_toActivate = toActivate;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
	{
		m_toActivate.setEnabled( isChecked );
	}

}