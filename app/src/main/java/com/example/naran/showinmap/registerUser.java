package com.example.naran.showinmap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.naran.showinmap.Constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registerUser extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextContact, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private String iname,icontact,iemail,ipassword;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            startActivity(new Intent (this, HomeActivity.class));
            finish();
            return;
        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);


        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    private void registerUser(){
        final String username = editTextUsername.getText().toString().trim();
        final String contact = editTextContact.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();



        progressDialog.setMessage("Registering User");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("phone", contact);
                params.put("email", email);
                params.put("password",password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister)
            register();
            editTextPassword.setText("");
            editTextUsername.setText("");
            editTextContact.setText("");
            editTextEmail.setText("");
        if(view==textViewLogin)
            startActivity(new Intent(this, loginscreen.class));
    }

    /*new*/
    public void register(){
        initialize();
        if(!validate()){
            Toast.makeText(this,"Registering has failed",Toast.LENGTH_SHORT).show();
        }else{
            registerUser();
        }
    }

    public void initialize(){
        iname = editTextUsername.getText().toString().trim();
        icontact = editTextContact.getText().toString().trim();
        iemail=editTextEmail.getText().toString().trim();
        ipassword=editTextPassword.getText().toString().trim();
    }

    public boolean validate(){
        boolean valid = true;
        if(iname.isEmpty()){
            editTextUsername.setError("Please Enter Valid Username");
            valid=false;
        }

        if(iemail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(iemail).matches()){
            editTextEmail.setError("Please enter valid email address");
            valid=false;
        }
        if(icontact.isEmpty() || icontact.length()!=10){
            editTextContact.setError("Please enter valid contact no");
            valid=false;
        }
        if(ipassword.isEmpty()){
            editTextPassword.setError("Please enter password");
            valid=false;
        }
        return valid;
    }
}
