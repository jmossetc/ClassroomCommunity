package paci.iut.classroomcommunity;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class    QuizzResultsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_quizz_results, container, false);

        Bundle b = this.getArguments();
        Log.d("BundleInResult", b.toString());

        int selectedAnswer = -1; // or other values
        if(b != null) {
            selectedAnswer = b.getInt("answer");
            Log.d("selectedAnswer", String.valueOf(selectedAnswer));
        }
        TextView text = (TextView) rootView.findViewById(R.id.quizz_confirmation);
        if(selectedAnswer == R.id.difficulty){
            text.setText("Bonne réponse");
        }
        else{
            text.setText("Mauvaise réponse");
        }
        return rootView;
    }

}
