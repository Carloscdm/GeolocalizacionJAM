package com.jblandii.geolocalizacionjam;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.AndroidSupport;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;

import java.io.IOException;
import java.util.ArrayList;

public class DB4O {

    private ObjectContainer objectContainer;

    public EmbeddedConfiguration getDb4oConfig() throws IOException {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().add(new AndroidSupport());
        configuration.common().objectClass(Ubicacion.class).
                objectField("date").indexed(true);
        return configuration;
    }

    private ObjectContainer openDataBase(String ruta) {
        try {
            objectContainer = Db4oEmbedded.openFile(getDb4oConfig(), ruta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectContainer;
    }

    public void addTracking(Ubicacion trackingObject, String ruta){
        objectContainer = openDataBase(ruta);
        objectContainer.store(trackingObject);
        objectContainer.commit();
        objectContainer.close();
    }

    public ArrayList<Ubicacion> getLocationByDate(final String date, String ruta){
        objectContainer = openDataBase(ruta);
        ArrayList<Ubicacion> result = new ArrayList<Ubicacion>();

        ObjectSet<Ubicacion> localizaciones = objectContainer.query(
                new Predicate<Ubicacion>() {
                    @Override
                    public boolean match(Ubicacion loc) {
                        return loc.getFecha().equals(date);
                    }
                });

        for(Ubicacion localizacion: localizaciones){
            result.add(localizacion);
        }

        objectContainer.close();

        return result;
    }
}

