package info.techasylum.bhukamp.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;
import java.util.List;

import info.techasylum.bhukamp.model.EarthQuake;
import info.techasylum.bhukamp.networkUtil.EarthquakeUtil;

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    private String mUrl;
    public EarthQuakeLoader(@NonNull Context context,String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<EarthQuake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<EarthQuake> earthquakes = EarthquakeUtil.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
