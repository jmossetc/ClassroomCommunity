package paci.iut.classroomcommunity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.lang.reflect.AccessibleObject;

import javax.xml.datatype.Duration;

/**
 * Created by charpent4 on 15/03/2018.
 */

public class Accueil extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"Connexion réussi", Toast.LENGTH_LONG);
    }
}
