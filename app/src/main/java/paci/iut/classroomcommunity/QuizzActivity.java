package paci.iut.classroomcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class QuizzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
    }
    public void HandleClick(View arg0) {
        Button btn = (Button)arg0;

        Intent answers = new Intent(this, QuizzConfirmationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("answer", btn.getId());
        answers.putExtras(bundle);
        startActivity(answers);
        finish();
    }
}
