package paci.iut.classroomcommunity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.auth0.android.Auth0;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Vincent
 * on 21/03/2018.
 */

public class FragQuiz extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_quizz, container, false);
        final HttpGetRequest get = new HttpGetRequest();


        Auth0 auth0 = new Auth0(getActivity());


        List<CategoryQuestion> listCat= new ArrayList<>();
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(get.execute("https://opentdb.com/api_category.php").get());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(jsonObj==null){
            Log.e("internet connection","not found");}
        else{
            try {



                JSONArray categories = jsonObj.getJSONArray("trivia_categories");

                // looping through All Contacts
                for (int i = 0; i < categories.length(); i++) {
                    JSONObject c = categories.getJSONObject(i);

                    int id = c.getInt("id");
                    String name = c.getString("name");


                    CategoryQuestion categoryQuestion = new CategoryQuestion(id, name);


                    listCat.add(categoryQuestion);
                }
            }
            catch(JSONException je) {
                je.printStackTrace();
            }
        }
        listCat.add(new CategoryQuestion(-1,"Random"));
        final EditText nbrquest = rootView.findViewById(R.id.nbrquest);
        final Spinner category = rootView.findViewById(R.id.category);
        final Spinner difficulty = rootView.findViewById(R.id.difficulty);
        List<String> diflist = new ArrayList<String>();
        diflist.add("Random");
        diflist.add("Hard");
        diflist.add("Medium");
        diflist.add("Easy");
        SpinnerAdapter catadapt  = new ArrayAdapter<CategoryQuestion>(getActivity(),R.layout.support_simple_spinner_dropdown_item,listCat);
        SpinnerAdapter difadapt = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,diflist);
        difficulty.setAdapter(difadapt);
        category.setAdapter(catadapt);


        Button valid = rootView.findViewById(R.id.valid);
        final TextView resultview = rootView.findViewById(R.id.result);


        valid.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                String nbquest=nbrquest.getText().toString();
                String result = "https://opentdb.com/api.php?amount="+nbrquest.getText()+"&type=multiple";
                CategoryQuestion selectedCat = (CategoryQuestion) category.getSelectedItem();
                if(selectedCat.getId()!=-1){
                    result=result+"&category="+selectedCat.getId();
                }
                if(difficulty.getSelectedItem()!="Random") {
                    result = result + "&difficulty=" + difficulty.getSelectedItem().toString().toLowerCase();

                }
                String resultat="";

                try {
                     HttpGetRequest getResult = new HttpGetRequest();
                     resultat = getResult.execute(result).get();
                     Log.e("result",resultat);
                    resultview.setText(resultat);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                HttpGetRequest createGame = new HttpGetRequest();
                String jsonidGame="id";
                String idGame="provisoire";
                try {
                    jsonidGame = createGame.execute("http://163.172.176.20/app_dev.php/api/create_game/"+selectedCat.getCat().replaceAll("\\s", "")+"/10").get();
                    JSONObject json = new JSONObject(jsonidGame);
                    idGame=json.getString("idGame");
                    Log.e("idGame",""+idGame);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                FragGame game = new FragGame();
                Bundle args = new Bundle();
                args.putString("json",resultat);
                args.putString("idGame",idGame);
                game.setArguments(args);
                ft.replace(R.id.fragment, game);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });











        return rootView;
    }
}
