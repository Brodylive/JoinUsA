package com.example.jbd.ju2;

/**
 * Created by jenniferbrody on 14/03/17.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jbd.ju2.DAO.UserDAO;
import com.example.jbd.ju2.R;
import com.example.jbd.ju2.models.LoginRegisterModel;
import com.example.jbd.ju2.models.RegisterBindingModel;


public class ConnectionActivity extends AppCompatActivity {


    Button connectButton;
    EditText user;
    EditText password;
    Button inscription;
    Button forgotPassword;
    String textExample;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        connectButton = (Button) findViewById(R.id.connection_button);
        user = (EditText) findViewById(R.id.connection_loginFields);
        password = (EditText) findViewById(R.id.connection_passwordFields);
        inscription = (Button) findViewById(R.id.connection_inscription);
        forgotPassword = (Button) findViewById(R.id.connection_forgetPassword);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);



        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginRegisterModel userModel = new LoginRegisterModel(user.getText().toString(), password.getText().toString());
                new LoginUser().execute(userModel);

            }
        });

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ConnectionActivity.this, InscriptionActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textExample = user.getText() +" "+password.getText();
                Toast.makeText(ConnectionActivity.this, textExample,Toast.LENGTH_SHORT).show();

            }
        });


    }


    private class LoginUser extends AsyncTask<LoginRegisterModel,Void,Void> {


        @Override
        protected Void doInBackground(LoginRegisterModel... params) {
            try{
                UserDAO userDAO = new UserDAO();
                if(!params[0].equals(null))
                {
                    String token = userDAO.loginUser(params[0]);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("token",token);
                    editor.commit();
                }

            }
            catch (Exception e) {
            }
            // si je ne met pas return null il me dit qu'il manque qqch ... sans doute
            // car void avec V maj donc objet. pas logique
            return null;
        }

    }
}