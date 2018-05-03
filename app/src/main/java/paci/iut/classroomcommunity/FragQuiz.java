package paci.iut.classroomcommunity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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



      String json  = null;
      try {
        json = new HttpGetRequest().execute("https://opentdb.com/api.php?amount=10").get();
        Log.e("test html", json);
        JSONObject jsonquestions = new JSONObject(json);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      } catch (JSONException e) {
        e.printStackTrace();
      }


      List<Integer> answersId = new ArrayList<Integer>();
        for (int i=0; i < rootView.getChildCount(); i++) {
            View v = rootView.getChildAt(i);

            if (v instanceof Button) {
                answersId.add(v.getId());
            }
        }

        for(int id : answersId){
            Button button = (Button) rootView.findViewById(id);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Button btn = (Button)v;
                    Bundle bundle = new Bundle();
                    bundle.putInt("answer", btn.getId());
                    Log.d("bundleInQuiz", bundle.toString());
                    QuizzResultsFragment frag = new QuizzResultsFragment();
                    frag.setArguments(bundle);

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, frag);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }

        return rootView;
    }
}
