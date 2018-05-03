package paci.iut.classroomcommunity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Vincent
 * on 03/05/2018.
 */

public class FragGame extends Fragment implements View.OnClickListener{
    @Nullable

    private String bonne_reponse;
    private int id;
    private int score;
    private String idGame;
    private Boolean passed;
    private TextView screen_quest;
    private TextView questionView;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private List<Question> questionList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_game, container, false);
        score=0;
        questionList = new ArrayList<>();
        Bundle args = getArguments();
        String jsonQuest = args.getString("json");
        idGame = args.getString("idGame");
        try {
            JSONObject json_global = new JSONObject(jsonQuest);
            if (json_global.getInt("response_code") == 0) {
                JSONArray list_questions = json_global.getJSONArray("results");
                for (int i =0;i<list_questions.length();i++
                        ) {
                    JSONObject quest = (JSONObject) list_questions.get(i);
                    JSONArray arr = (JSONArray) quest.get("incorrect_answers");
                    List<String> mauvaises = new ArrayList<String>();
                    for(int o = 0; o < arr.length(); o++){
                        mauvaises.add(arr.getString(o));
                    }


                    Question question = new Question(
                            quest.getString("category"),
                            quest.getString("difficulty"),
                            quest.getString("question"),
                            quest.getString("correct_answer"),
                            mauvaises
                    );
                    questionList.add(question);

                }



            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        screen_quest = (TextView) rootView.findViewById(R.id.question);
        btn1 = (Button) rootView.findViewById(R.id.rep1);
        btn2 = (Button) rootView.findViewById(R.id.rep2);
        btn3 = (Button) rootView.findViewById(R.id.rep3);
        btn4 = (Button) rootView.findViewById(R.id.rep4);

        id=0;
        passed=true;




        défilement();


        return rootView;

    }

    public void défilement(){
        if(id<questionList.size()) {
            Question quest = questionList.get(id);
            passed = false;
            bonne_reponse=quest.getBonne_réponse();
            screen_quest.setText(quest.getQuestion());
            btn1.setText(quest.getBonne_réponse());
            btn2.setText(quest.getMauvaises_réponses().get(0));
            btn3.setText(quest.getMauvaises_réponses().get(1));
            btn4.setText(quest.getMauvaises_réponses().get(2));


            btn1.setOnClickListener(this);
            btn2.setOnClickListener(this);
            btn3.setOnClickListener(this);
            btn4.setOnClickListener(this);

            id++;

        }
        else {




            FragmentTransaction ft = getFragmentManager().beginTransaction();
            FragQuiz quiz = new FragQuiz();
            ft.replace(R.id.fragment, quiz);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            if(score<0)score=0;
            builder.setMessage("La partie est terminée : \nTu as réalisé un score de "+score+"points.");
            HttpGetRequest updateScore = new HttpGetRequest();

            try {
                updateScore.execute("http://163.172.176.20/app_dev.php/api/update_score/"+idGame+"/10/"+score).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        }




    @Override
    public void onClick(View view) {
        Button bouton = (Button)view;
        if(bouton.getText()==bonne_reponse){
            score++;

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setCancelable(true);
            builder.setMessage("Bonne réponse!!!");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            score--;
        }
        défilement();
    }
}

