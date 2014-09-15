package com.example.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gridimagesearch.R;
import com.example.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.gridimagesearch.models.ImageResult;
import com.example.gridimagesearch.models.Settings;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
//import android.widget.GridView;
import com.etsy.android.grid.StaggeredGridView;

public class SearchActivity extends Activity {
	
	private EditText etQuery;
	private StaggeredGridView gvImages;
	
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	
	private int start = 0;
	
	private Settings settings;
	
	private static final int REQUEST_CODE = 5;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpViews();
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvImages.setAdapter(aImageResults);
		gvImages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				ImageResult result = imageResults.get(position);
				intent.putExtra("image_result", result);
				startActivity(intent);
			}
		});
		
		gvImages.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
				customLoadMoreDataFromApi(page * 8); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
				
			}
		});
		
		//default settings
		settings = new Settings();
		
	}
	
	protected void customLoadMoreDataFromApi(int page) {
		this.start = page;
		onImageSearch(gvImages);
	}

	private void setUpViews(){
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvImages = (StaggeredGridView) findViewById(R.id.gvImages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	
	public void onLaunchSettings(MenuItem mi){
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		settingsIntent.putExtra("settings", settings);
		startActivityForResult(settingsIntent, REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // REQUEST_CODE is defined above
		  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
		     settings = (Settings) data.getSerializableExtra("settings");
		  }
	} 
	
	public void onImageSearch(View v){
		if(v.getId() == R.id.buttonSearch) {
			imageResults.clear();
			aImageResults.notifyDataSetChanged();
			this.start = 0;
		}
		String query = this.etQuery.getText().toString();
		String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?q=" + query 
				+ "&v=1.0&rsz=8&start=" 
				+ this.start 
				+ "&imgsz=" + settings.getImageSize()
				+ "&imgcolor=" + settings.getColorFilter()
				+ "&imgtype=" + settings.getImageType()
				+ "&as_sitesearch=" + settings.getSiteFilter();
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(searchUrl, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
					Log.d("DEBUG", response.toString());
					JSONArray imageResultsJson = null;
					try {
						imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
						aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.i("INFO", imageResults.toString());
			}
		});
		
	}

}
