package paci.iut.classroomcommunity;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Vincent
 * on 21/03/2018.
 */

public class FragAmis extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.activity_amis,container,false);
        String[] entities = {"Users", "Books", "Orders", "States"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, entities);
        setListAdapter(adapter);
    return rootView;
    }



    public void onListItemClick(ListView parent, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(getActivity(), item + " selected", Toast.LENGTH_LONG).show();

        // alternatively, use 'position' with a string array defined in your class:
        //selection.setText(projectsAsStrings[position]);
    }
}
