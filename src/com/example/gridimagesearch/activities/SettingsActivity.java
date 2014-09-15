package com.example.gridimagesearch.activities;

import com.example.gridimagesearch.R;
import com.example.gridimagesearch.R.layout;
import com.example.gridimagesearch.R.menu;
import com.example.gridimagesearch.models.Settings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class SettingsActivity extends Activity implements OnItemSelectedListener{

	private Spinner sizeSpinner;
	private Spinner colorSpinner;
	private Spinner typeSpinner;
	private TextView siteFilter;
	
	private Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//Get the intent for settings
		settings = (Settings) getIntent().getSerializableExtra("settings");
		
		//Set up the image size spinner
		sizeSpinner = (Spinner) findViewById(R.id.size_options);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
		        R.array.image_sizes, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		sizeSpinner.setAdapter(sizeAdapter);
		sizeSpinner.setOnItemSelectedListener(this);
		
		
		//Set up the image size spinner
		colorSpinner = (Spinner) findViewById(R.id.color_options);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
		        R.array.color_filter, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		colorSpinner.setAdapter(colorAdapter);
		colorSpinner.setOnItemSelectedListener(this);
		
		
		//Set up the image type spinner
		typeSpinner = (Spinner) findViewById(R.id.type_options);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
		        R.array.image_types, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		typeSpinner.setAdapter(typeAdapter);
		typeSpinner.setOnItemSelectedListener(this);
		
		
		siteFilter = (TextView) findViewById(R.id.site);
		
		
	}
	
	public void onSubmit(View v){
		  settings.setSiteFilter(siteFilter.getText().toString());
		  // Prepare data intent 
		  Intent data = new Intent();
		  // Pass relevant data back as a result
		  data.putExtra("settings", settings);
		  // Activity finished ok, return the data
		  setResult(RESULT_OK, data); // set result code and bundle data for response
		  finish(); // closes the activity, pass data to parent
	}
	

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		switch (parent.getId()) {
			
			case R.id.size_options:
				settings.setImageSize((String) parent.getItemAtPosition(pos));
				break;
			
			case R.id.color_options:
				settings.setColorFilter((String) parent.getItemAtPosition(pos));
			
			case R.id.type_options:
				settings.setImageType((String) parent.getItemAtPosition(pos));
				
			default:
				break;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
