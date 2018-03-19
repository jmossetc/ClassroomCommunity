package paci.iut.classroomcommunity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.String;
import java.lang.Exception;

public class QuizzConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_confirmation);


        Bundle b = getIntent().getExtras();
        int selectedAnswer = -1; // or other values
        if(b != null)
            selectedAnswer = b.getInt("answer");

        TextView text = (TextView) findViewById(R.id.quizz_confirmation);
        if (selectedAnswer == R.id.radio_3){
            text.setText(getString(R.string.good_answer));
        }
        else{
            text.setText(getString(R.string.bad_answer));
        }

    }
}
