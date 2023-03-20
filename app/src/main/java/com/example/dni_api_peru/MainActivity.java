package com.example.dni_api_peru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Definimos las variables a utilizar
    private String dni;
    private Button btn_consultar, btn_limpiar;
    private EditText txt_dni, txt_nom, txt_ape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Casteamos los edittext y Botones
        txt_dni = (EditText) findViewById(R.id.txt_dni);
        txt_nom = (EditText) findViewById(R.id.txt_nombre);
        txt_ape = (EditText) findViewById(R.id.txt_ape);
        btn_consultar = (Button) findViewById(R.id.btn_consultar);
        btn_limpiar = (Button) findViewById(R.id.btn_limpiar);

        //Hacemos no editables los edittext de los nombres y apellidos
        txt_nom.setFocusable(false);
        txt_ape.setFocusable(false);

        //Eventos Boton Consultar
        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardamos el dni obtenido del formulario
                dni = txt_dni.getText().toString();
                //Lanzamos el metodo para obtener los datos
                obtener();
            }
        });
        //Eventos Boton Limpiar
        btn_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
    }

    private void obtener(){
        //URL de la pagina apisperu
        //en dni enviamos el que ponemos en el edittext
        String url = "https://dniruc.apisperu.com/api/v1/dni/"+dni+"?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1YXIuZXptYW40QGdtYWlsLmNvbSJ9.Nkqu_IqVWEqRvMJ_CHoVA5wn82-GQJInaMr_yVXvSF0";
        //Usamos la dependencia Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Analiza el JSON para extraer los nombres que deseas mostrar
                    String nombres = response.getString("nombres");
                    // Actualiza el EditText con el texto extraído del JSON
                    txt_nom.setText(nombres);
                    // Analiza el JSON para extraer los apellidos que deseas mostrar
                    String apellido_pat = response.getString("apellidoPaterno");
                    String apellido_mat = response.getString("apellidoMaterno");
                    // Actualiza el EditText con el texto extraído del JSON
                    txt_ape.setText(apellido_pat+" "+apellido_mat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }

    //Metodo para limpiar los campos de los edittext
    private void limpiar(){
        txt_dni.setText("");
        txt_nom.setText("");
        txt_ape.setText("");
    }
}