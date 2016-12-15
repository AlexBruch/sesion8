package com.s8.lasalle.sesion8;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sqllite.ItemsDatasource;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog progressDialog;
    private ListView Lista;
    private static String Jsonurl = "http://www.v2msoft.com/clientes/lasalle/curs-android/productos_supermercado.json";
    ArrayList<HashMap<String, String>> ProductosSupermercado;
    ListAdapter adapter;
    ItemsDatasource itemsDatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsDatasource = new ItemsDatasource(this);

        ProductosSupermercado = new ArrayList<>();
        Lista = (ListView) findViewById(R.id.Lista);

        //if de validacion si tenemos conexion

        new GetProducts().execute();

        //si no hubiese conexion
        CargarSQLite();

        Button update = (Button) findViewById(R.id.button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Limpiamos la lista vieja
                ProductosSupermercado.clear();
                // Y la actualizamos
                new GetProducts().execute();
                Toast toast = Toast.makeText(getApplicationContext(), "Lista actualizada", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


    public void CargarSQLite(){

    }

    private class GetProducts extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Cargando...");
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            // request to json data url and getting response
            String jsonString = httpHandler.makeServiceCall(Jsonurl);
            Log.e(TAG, "Response from url: " + jsonString);
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // Getting JSON Array node
                    JSONArray productos = jsonObject.getJSONArray("productos");

                    for (int i = 0; i < productos.length(); i++) {

                        JSONObject c = productos.getJSONObject(i);

                        String id = c.getString("id");
                        String fabricante = c.getString("fabricante");
                        String nombre = c.getString("nombre");
                        String precio = c.getString("precio");
                        String stock = c.getString("stock");

                        // tmp hash map for single contact
                        HashMap<String, String> product = new HashMap<>();

                        // adding each child node to HashMap key => value
                        product.put("id", id);
                        product.put("fabricante", fabricante);
                        product.put("nombre", nombre);
                        product.put("precio", precio+"€");
                        product.put("stock", stock);

                        //database
                        itemsDatasource.saveProduct(fabricante, nombre, precio, stock);

                        // adding contact to contact list
                        ProductosSupermercado.add(product);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Could not get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Could not get json from server.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
             adapter = new SimpleAdapter(
                    MainActivity.this,
                    ProductosSupermercado,
                    R.layout.item_list,
                    new String[]{"fabricante", "nombre",  "precio", "stock"},
                    new int[]{R.id.manufacturer, R.id.productname, R.id.price, R.id.stock}){

                @Override
                public View getView (int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView tv = (TextView) view.findViewById(R.id.stock);
                    // TODO: Add null checks
                    String s = tv.getText().toString();


                    if (position%2==0) {
                        view.setBackgroundColor(Color.parseColor("#d5efef"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#efefef"));
                    }
                    int intStock = Integer.parseInt(s);
                    if(intStock <= 0){
                        tv.setBackgroundColor(Color.parseColor("#fd6955"));
                        tv.setTextColor(Color.parseColor("#780000"));
                    }

                    return view;
                }
            };

            Lista.setAdapter(adapter);
        }
    }

}