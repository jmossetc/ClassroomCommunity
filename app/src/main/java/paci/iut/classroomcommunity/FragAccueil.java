package paci.iut.classroomcommunity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vincent
 * on 21/03/2018.
 */

public class FragAccueil extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_main, container, false);

            return rootView;
        }
    }

