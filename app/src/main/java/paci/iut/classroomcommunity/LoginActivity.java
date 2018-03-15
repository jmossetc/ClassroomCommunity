package paci.iut.classroomcommunity;

import android.content.Intent;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText identifiant = (EditText)findViewById(R.id.identifiant);
        final EditText pass = (EditText)findViewById(R.id.mdp);
        Button bouton = (Button) findViewById(R.id.connexion);

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(identifiant.getText().equals("id")&&pass.getText().equals("pass")){
                    Intent intent = new Intent(LoginActivity.this, Accueil.class);
                    startActivity(intent);
                }
            }
        });
    }
}
