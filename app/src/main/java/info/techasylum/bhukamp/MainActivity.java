package info.techasylum.bhukamp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import info.techasylum.bhukamp.loaders.EarthQuakeLoader;
import info.techasylum.bhukamp.model.EarthQuake;
import info.techasylum.bhukamp.model.EarthQuakeAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {
    public static final String TAG = MainActivity.class.getName();
    private static final String SAMPLE_JSON_RESPONSE_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=magnitude&limit=20";
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    public static final int LOADER_ID=1;
    public static final String DETAIL_URL="detail-url";
    ListView mListView;
    EarthQuakeAdapter earthQuakeAdapter;
    List<EarthQuake> earthQuakeList = new ArrayList<>();
    LoaderManager loaderManager;
    TextView emptySateTextView;
    ProgressBar progressBarSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptySateTextView=findViewById(R.id.emptyView);
        progressBarSpinner=findViewById(R.id.loading_spinner);
        mListView = findViewById(R.id.list);
        mListView.setEmptyView(emptySateTextView);
        earthQuakeAdapter=new EarthQuakeAdapter(this,earthQuakeList);
        mListView.setAdapter(earthQuakeAdapter);
       loaderManager=LoaderManager.getInstance(this);
       loaderManager.initLoader(LOADER_ID,null,this);
        onlistItemClick();
    }

    private void onlistItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthQuake currentItem = earthQuakeList.get(position);
                String detailUrl = currentItem.getDetailUrl();
                // Toast.makeText(EarthquakeActivity.this, ""+list.get(i).getPlace(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,OpenDetailActivity.class);
                intent.putExtra(DETAIL_URL,detailUrl);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;
            default:
                return false;
        }
    }

    @NonNull
    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, @Nullable Bundle args) {
        progressBarSpinner.setVisibility(View.VISIBLE);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "50");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthQuakeLoader(this, uriBuilder.toString());

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthQuake>> loader, List<EarthQuake> earthquakes) {
        progressBarSpinner.setVisibility(View.GONE);
        if (!isNetworkConnected()) {
            progressBarSpinner.setVisibility(View.GONE);
            emptySateTextView.setText("No internet connection");
            return;
        }
        earthQuakeAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            earthQuakeAdapter.addAll(earthquakes);
        }
        else {

            emptySateTextView.setText("something went wrong");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<EarthQuake>> loader) {

        earthQuakeAdapter.clear();

    }

    //Check internet connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("boby");

        } catch (Exception e) {
            return false;
        }
    }


}