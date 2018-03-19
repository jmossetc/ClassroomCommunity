package paci.iut.classroomcommunity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by PC-Vincent on 19/03/2018.
 */

public class ListeAmis extends ListActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listamis);

    // set the list adapter
    String[] entities = {"Users", "Books", "Orders", "States"};
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entities);
    setListAdapter(adapter);
  }
  public void onListItemClick(ListView parent, View v, int position, long id) {
    String item = (String) getListAdapter().getItem(position);
    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();

    // alternatively, use 'position' with a string array defined in your class:
    //selection.setText(projectsAsStrings[position]);
  }
}
