package dk.tec.catchingtodo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.tec.catchingtodo.models.TodoItem;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestQueue = Volley.newRequestQueue(this);
        //getTodoItems();
        createTodoItem();
        //startActivity(new Intent(this, CreateTodoActivity.class));
    }

    void getTodoItems() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://192.168.0.246:8989/todoitem",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<List<TodoItem>>() {
                        }.getType();
                        List<TodoItem> todoItems = new Gson().fromJson(response, listType);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("***** Volley *****", "onErrorResponse: ", error);
                    }
                });

        requestQueue.add(request);
    }

    void createTodoItem(){
        TodoItem todoItem = new TodoItem();
todoItem.setName("Mmmmhhmmm mmm");

        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://192.168.0.246:8989/todoitem",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        int i = Integer.valueOf(response);
                    if (i == -1) Log.d("Create", "onResponse: failed ");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("todo", new Gson().toJson(todoItem));
                    String requestBody = jsonObject.toString();
                    return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    //throw new RuntimeException(e);
                    Log.e("TAG", "getBody: ", e);
                    return super.getBody();
                }

            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

requestQueue.add(request);
    }
}



