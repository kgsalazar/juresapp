package com.example.appjurescaym;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appjurescaym.model.ActividadesModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class ActividadesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    //Widgets
    EditText etFecha, etHora;
    ImageButton ibObtenerFecha, ibObtenerHora;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText etNombre,etLugar,etMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        etHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);

        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);

        ibObtenerFecha.setOnClickListener(this);
        ibObtenerHora.setOnClickListener(this);

        //
        etNombre = findViewById(R.id.etNombre);
        etLugar = findViewById(R.id.etLugar);
        etMonto = findViewById(R.id.etMonto);

        //
        inicializarFirebase();



    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;

                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
        },anio, mes, dia);

        recogerFecha.show();

    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }

        }, hora, minuto, false);

        recogerHora.show();
    }

    public void guardar(View view)
    {
        ActividadesModel am = new ActividadesModel();
        am.setUid(UUID.randomUUID().toString());
        am.setNombre(etNombre.getText().toString());
        am.setLugar(etLugar.getText().toString());
        am.setFecha(etFecha.getText().toString());
        am.setHora(etHora.getText().toString());
        am.setMonto(etMonto.getText().toString());
        databaseReference.child("Actividad").child(am.getUid()).setValue(am);
        Toast.makeText(this,"Actividad agregada correctamente",Toast.LENGTH_LONG).show();

        borrarCampos();

    }

    private void borrarCampos() {
        etNombre.setText("");
        etLugar.setText("");
        etFecha.setText("");
        etHora.setText("");
        etMonto.setText("");

    }

}
