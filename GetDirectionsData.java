package com.example.rohitranjan.vehiclepayment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GetDirectionsData extends AsyncTask<Object,String,String>{

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
    LatLng latLng;
    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];


        DownloadUrl downloadUrl = new DownloadUrl();
        try{
            googleDirectionsData = downloadUrl.readUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String result) {
        String[] directionsList;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(result);
        displayDirection(directionsList);

        /*HashMap<String,String> directionsList = null;
       DataParser parser = new DataParser();
       directionsList = parser.parseDirections(result);
       duration = directionsList.get("duration");
       distance = directionsList.get("distance");

       mMap.clear();
       MarkerOptions markerOptions = new MarkerOptions();
       markerOptions.position(latLng);
       markerOptions.draggable(true);
       markerOptions.title("Duration="+duration);
       markerOptions.snippet("Distance= "+distance);

       mMap.addMarker(markerOptions);*/
    }

    public void displayDirection(String[] directionList){

        int count = directionList.length;
        for (int i = 0; i<count;i++){
            PolylineOptions options = new PolylineOptions();
            options.color(Color.RED);
            options.width(10);
            options.addAll(PolyUtil.decode(directionList[i]));

            mMap.addPolyline(options);
        }
    }
}
