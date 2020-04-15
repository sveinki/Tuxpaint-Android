package org.tuxpaint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.content.res.AssetManager;
import android.content.pm.PackageManager;

public class ConfigActivity extends Activity {
	private static final String TAG = ConfigActivity.class.getSimpleName();
   	String[] locales = null;
   	String[] printdelays = null;
	private Properties props = null;
	// current the configurable properties
	String autosave = null;
	String sound = null;
	String stereo = null;
	String locale = null;
	String savedir = null;
	String datadir = null;
	String saveover = null;
	String startblank = null;
	String newcolorsfirst = null;
	String print = null;
        String printdelay = null;
        String disablescreensaver = null;
        String orient = null;
	
	EditText savedirView = null;
	EditText datadirView = null;
	ToggleButton soundToggle = null;
	ToggleButton stereoToggle = null;
	ToggleButton autosaveToggle = null;
	ToggleButton startblankToggle = null;
	ToggleButton newcolorsfirstToggle = null;
	Spinner localeSpinner = null;
	RadioGroup saveoverGroup = null;
	ToggleButton printToggle = null;
	Spinner printdelaySpinner = null;
        ToggleButton disablescreensaverToggle = null;
        ToggleButton orientToggle = null;
        AssetManager mgr;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");
	super.onCreate(savedInstanceState);

        if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
	    this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
	}

		setContentView(R.layout.config);
		load ();
		
		savedirView = (EditText)this.findViewById(R.id.textSavedir);
		savedirView.setText(savedir);
		savedirView.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				savedir = savedirView.getText().toString();
			}
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void afterTextChanged(Editable s) {
			}
		});
		datadirView = (EditText)this.findViewById(R.id.textDatadir);
		datadirView.setText(datadir);
		datadirView.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				datadir = datadirView.getText().toString();
			}
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void afterTextChanged(Editable s) {
			}
		});
		
		soundToggle = (ToggleButton)this.findViewById(R.id.toggleSound);
		if (sound.compareTo("yes") == 0)
			soundToggle.setChecked(true);
		else
			soundToggle.setChecked(false);
		soundToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					sound = "yes";
				else
					sound = "no";
			}
		});

		stereoToggle = (ToggleButton)this.findViewById(R.id.toggleStereo);
		if (stereo.compareTo("stereo") == 0)
			stereoToggle.setChecked(true);
		else
			stereoToggle.setChecked(false);
		stereoToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					stereo = "yes";
				else
					stereo = "no";
			}
		});

       	autosaveToggle = (ToggleButton)this.findViewById(R.id.toggleAutosave);
		if (autosave.compareTo("yes") == 0)
			autosaveToggle.setChecked(true);
		else
			autosaveToggle.setChecked(false);
		autosaveToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					autosave = "yes";
				else
					autosave = "no";
			}
		});
		
       	startblankToggle = (ToggleButton)this.findViewById(R.id.toggleStartblank);
		if (startblank.compareTo("yes") == 0)
			startblankToggle.setChecked(true);
		else
			startblankToggle.setChecked(false);
		startblankToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					startblank = "yes";
				else
					startblank = "no";
			}
		});
		
       	newcolorsfirstToggle = (ToggleButton)this.findViewById(R.id.toggleNewcolorsfirst);
		if (newcolorsfirst.compareTo("yes") == 0)
			newcolorsfirstToggle.setChecked(true);
		else
			newcolorsfirstToggle.setChecked(false);
		newcolorsfirstToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					newcolorsfirst = "yes";
				else
					newcolorsfirst = "no";
			}
		});

		locales = getResources().getStringArray(R.array.locales);
		int index = 0;
		for (; index != locales.length; index++){
			if (locales[index].compareTo(locale) == 0)
				break;
		}
		if(index==locales.length)
		    index = 0;
       	localeSpinner = (Spinner) findViewById(R.id.spinnerLocale);
		localeSpinner.setSelection(index); 
		localeSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						locale = locales[arg2];
					}
					public void onNothingSelected(AdapterView<?> arg0) {
					}
                });
		
			saveoverGroup = (RadioGroup)this.findViewById(R.id.groupSaveover);
			if (saveover.compareTo("yes") == 0)
				((RadioButton)this.findViewById(R.id.radioYes)).setChecked(true);
			else if (saveover.compareTo("ask") == 0)
				 ((RadioButton)this.findViewById(R.id.radioAsk)).setChecked(true);
			else
				((RadioButton)this.findViewById(R.id.radioNew)).setChecked(true);
			saveoverGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					 int radioButtonId = group.getCheckedRadioButtonId();
					 if (radioButtonId == R.id.radioAsk)
						 saveover = "ask";
					 else if (radioButtonId == R.id.radioYes)
						 saveover = "yes";
					 else
						 saveover = "new";
				}
			});

		printToggle = (ToggleButton)this.findViewById(R.id.togglePrint);
		if (print.compareTo("yes") == 0)
			printToggle.setChecked(true);
		else
			printToggle.setChecked(false);
		printToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					print = "yes";
				else
					print = "no";
			}
		});

		printdelays = getResources().getStringArray(R.array.printdelays);
		int indexpd = 0;
		for (; indexpd != printdelays.length; indexpd++){
			if (printdelays[indexpd].compareTo(printdelay) == 0)
				break;
		}
		if(indexpd==printdelays.length)
		    indexpd = 0;
       	printdelaySpinner = (Spinner) findViewById(R.id.spinnerPrintdelay);
		printdelaySpinner.setSelection(indexpd); 
		printdelaySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						printdelay = printdelays[arg2];
					}
					public void onNothingSelected(AdapterView<?> arg0) {
					}
                });
		
	disablescreensaverToggle = (ToggleButton)this.findViewById(R.id.toggleDisablescreensaver);
		if (disablescreensaver.compareTo("yes") == 0)
			disablescreensaverToggle.setChecked(true);
		else
			disablescreensaverToggle.setChecked(false);
		disablescreensaverToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					disablescreensaver = "yes";
				else
					disablescreensaver = "no";
			}
		});

	orientToggle = (ToggleButton)this.findViewById(R.id.toggleOrient);
		if (orient.compareTo("landscape") == 0)
			orientToggle.setChecked(true);
		else
			orientToggle.setChecked(false);
		orientToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					orient = "landscape";
				else
					orient = "portrait";
			}
		});

	}
	
    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy()");
        save ();
        super.onDestroy();
    }

    /* Load first the defaults from the .cfg file inside assets, then try to overwrite them with the user defined config */
	private void load (){
	    try {
		mgr = getResources().getAssets();
		InputStream inassetscfg = mgr.open ("etc/tuxpaint.cfg");
		props = new Properties();
		props.load(inassetscfg);
		inassetscfg.close();
	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }

	    	File external = getExternalFilesDir(null);
	    	File cfg =  new File (external, "tuxpaint.cfg");
	    	try {
	    	InputStream in = new FileInputStream(cfg);
			props = new Properties();
	    	 props.load(in);
	    	 in.close();
		} catch (FileNotFoundException el){ /* do nothing, defaults have already been loaded */
		} catch (Exception e1) {
	    	 // TODO Auto-generated catch block
	    	 e1.printStackTrace();
	    	 }

		/* Fixme: Is this redundant after having added the load of the cfg file in assets? */
	    	 autosave = props.getProperty("autosave", "no");
	    	 sound = props.getProperty("sound", "no");
	    	 stereo = props.getProperty("stereo", "yes");
	    	 saveover = props.getProperty("saveover", "ask");
	    	 startblank = props.getProperty("startblank", "no");
	    	 newcolorsfirst = props.getProperty("newcolorsfirst", "yes");
	    	 savedir = props.getProperty("savedir", external.getAbsolutePath());
	    	 datadir = props.getProperty("datadir", external.getAbsolutePath());
	    	 locale = props.getProperty("locale", Locale.getDefault().toString());
	    	 print = props.getProperty("print", "no");
	    	 printdelay = props.getProperty("printdelay", "0");
		 disablescreensaver = props.getProperty("disablescreensaver", "no");
		 orient = props.getProperty("orient", "landscape");
	    	 
	         Log.v(TAG, autosave + " " + sound + " " + stereo + " " + saveover + " " + savedir+ " "+datadir+ " " + locale + " " + print + " " + printdelay + " " + disablescreensaver + " " + orient);;
	}
	
	private void save () {
    	File external = getExternalFilesDir(null);
     	File cfg =  new File (external, "tuxpaint.cfg");
        props.put("autosave", autosave);
        props.put("sound", sound);
        props.put("stereo", stereo);
        props.put("saveover", saveover);
        props.put("startblank", startblank);
        props.put("newcolorsfirst", newcolorsfirst);
        props.put("savedir", savedir);
        props.put("datadir", datadir);
        props.put("locale", locale);
	props.put("print", print);
        props.put("printdelay", printdelay);
	props.put("disablescreensaver", disablescreensaver);
	props.put("orient", orient);

		try {
	 	  	OutputStream	out = new FileOutputStream(cfg);
	        props.store(out, "");
	        out.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
         
	}
    
}
