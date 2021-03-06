package com.jblandii.geolocalizacionjam;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText et_Fecha;
    private Button btn_Fecha, btn_VerMapa;
    private int dia, mes, anio;
    private String fechaElegida;
    private String permission;
    static final int PERMISSION_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlPermission();

        et_Fecha = findViewById(R.id.et_Fecha);
        btn_Fecha = findViewById(R.id.btn_Fecha);
        btn_VerMapa = findViewById(R.id.btn_VerMapa);

        et_Fecha.setEnabled(false);

        btn_Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (dayOfMonth < 10 && monthOfYear < 10) {
                            fechaElegida = "0" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
                        } else if (dayOfMonth < 10) {
                            fechaElegida = "0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        } else if (monthOfYear < 10) {
                            fechaElegida = "" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
                        } else {
                            fechaElegida = "" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        }
                        et_Fecha.setText(fechaElegida);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        btn_VerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMapa = new Intent(MainActivity.this, MapsActivity.class);
                intentMapa.putExtra("fechaElegida", fechaElegida);
                startActivity(intentMapa);
            }
        });
    }

    private void controlPermission() {
        permission = Manifest.permission.ACCESS_FINE_LOCATION;
        checkPermission();
    }

    private void checkPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                permission);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Ha aceptado
        } else {
            // Ha denegado o es la primera vez que se le pregunta
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // No se le ha preguntado aún
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_LOCATION);
            } else {
                // Ha denegado
                Toast.makeText(this, "Por favor, acepta los permisos.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Estamos en el caso del teléfono
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION:

                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(permission)) {
                    // Comprobar si ha sido aceptado o denegado la petición de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        // Concedió su permiso
                    } else {
                        // No concendió su permiso
                        Toast.makeText(this, "Has denegado el acceso.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }


}
