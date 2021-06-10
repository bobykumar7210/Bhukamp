package info.techasylum.bhukamp.model;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.List;

import info.techasylum.bhukamp.R;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {
    private static final String LOCATION_SEPARATOR = " of ";
    public EarthQuakeAdapter(@NonNull Context context, @NonNull List<EarthQuake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final EarthQuake currentEarthquakeItem=getItem(position);

        View listItemView=convertView;


        if (listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);

        }
        final String detailUrl=currentEarthquakeItem.getDetailUrl();

        TextView magTextView= (TextView) listItemView.findViewById(R.id.magnitudeTextView);
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquakeItem.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        String formattedMagnitude = formatMagnitude(currentEarthquakeItem.getMagnitude());
        // Display the magnitude of the current earthquake in that TextView
        magTextView.setText(formattedMagnitude);

        TextView locationOffsetTextView=(TextView) listItemView.findViewById(R.id.locationOffsetTextView);

        TextView placeTextView=(TextView) listItemView.findViewById(R.id.placeTextView);
        String originalLocation =currentEarthquakeItem.getPlace();

        String[] value=getLocationAndOffset(originalLocation);
        locationOffsetTextView.setText(value[0]);
        placeTextView.setText(value[1]);


        TextView dateTextView=(TextView) listItemView.findViewById(R.id.dateTextView);
        dateTextView.setText(currentEarthquakeItem.getDate());
        TextView timeTextView=(TextView) listItemView.findViewById(R.id.timeTextView);
        timeTextView.setText(currentEarthquakeItem.getTime());

        return listItemView;
    }

    private String[] getLocationAndOffset(String originalLocation) {
        String primaryLocation;
        String locationOffset;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        return new String[]{locationOffset, primaryLocation};
    }


    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
