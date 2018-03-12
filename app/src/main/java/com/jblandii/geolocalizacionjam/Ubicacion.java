package com.jblandii.geolocalizacionjam;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jairo on 11/03/2018.
 */

public class Ubicacion {

    private LatLng latitudLongitud;
    private String fecha;

    public Ubicacion() {
    }

    public Ubicacion(LatLng latLng, String date) {
        this.latitudLongitud = latLng;
        this.fecha = date;
    }

    public LatLng getLatitudLongitud() {
        return latitudLongitud;
    }

    public void setLatitudLongitud(LatLng latitudLongitud) {
        this.latitudLongitud = latitudLongitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
