package com.example.appjurescaym;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appjurescaym.model.ActividadesModel;
import com.example.appjurescaym.model.InscripcionModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class InscripcionFragment extends Fragment {


    private DatabaseReference myRef;
    String names[] = {"Red","Blue","Green"};
    private List<ActividadesModel> listActividades  = new ArrayList<ActividadesModel>();
    ArrayAdapter<ActividadesModel> arrayAdapterActividades;
    Spinner sp,spMinisterios ;
    String ministerios[] = {"Música","Formación","Danza","Obras misericordia","Comunicación","Intercesión"};
    ArrayAdapter<String> arrayMinisterios;
    Button btGuardarInscripcion;
    EditText etNombre,etApellido1,etApellido2,etTelefono,etEdad,etTelefonoEncargado,etPadecimiento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscripcion, container, false);

        sp = view.findViewById(R.id.spActividades);
        spMinisterios = view.findViewById(R.id.spMinisterios);
        btGuardarInscripcion = view.findViewById(R.id.btnGuardar);
        etNombre = view.findViewById(R.id.etNombre);
        etApellido1 = view.findViewById(R.id.etApellido1);
        etApellido2 = view.findViewById(R.id.etApellido2);
        etTelefono = view.findViewById(R.id.etTelefono);
        etEdad = view.findViewById(R.id.etEdad);
        etTelefonoEncargado = view.findViewById(R.id.etTelefonoEncargado);
        etPadecimiento= view.findViewById(R.id.etPadecimiento);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Actividad");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listActividades.clear();
                for (DataSnapshot objSnaptchot : dataSnapshot.getChildren()) {
                    ActividadesModel i = objSnaptchot.getValue(ActividadesModel.class);
                    listActividades.add(i);
                    arrayAdapterActividades  = new ArrayAdapter<ActividadesModel>(getContext(), android.R.layout.simple_list_item_1,listActividades);
                    sp.setAdapter(arrayAdapterActividades);

                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        arrayMinisterios  = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,ministerios);
        spMinisterios.setAdapter(arrayMinisterios);


        btGuardarInscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InscripcionModel im = new InscripcionModel();
                im.setId(UUID.randomUUID().toString());
                im.setNombre(etNombre.getText().toString());
                im.setApellido1(etApellido1.getText().toString());
                im.setApellido2(etApellido2.getText().toString());
                im.setTelefono(etTelefono.getText().toString());
                im.setActividad(sp.getSelectedItem().toString());
                im.setMinisterio(spMinisterios.getSelectedItem().toString());
                im.setEdad(etEdad.getText().toString());
                im.setTelefonoEncargado(etTelefonoEncargado.getText().toString());
                im.setPadecimiento(etPadecimiento.getText().toString());


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                myRef = database.getReference();
                myRef.child("Inscripcion").child(im.getId()).setValue(im);
                Toast.makeText(getContext(),"Inscripción agregada correctamente",Toast.LENGTH_LONG).show();
                borrarCampos();
            }
        });
        return view;

    }

    private void borrarCampos() {
        etNombre.setText("");
        etApellido1.setText("");
        etApellido2.setText("");
        etTelefono.setText("");
        etEdad.setText("");
        etTelefonoEncargado.setText("");
        etPadecimiento.setText("");
    }

}
