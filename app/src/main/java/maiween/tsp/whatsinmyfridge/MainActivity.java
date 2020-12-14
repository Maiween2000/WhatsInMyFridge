package maiween.tsp.whatsinmyfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import maiween.tsp.whatsinmyfridge.R;

//GET https://api.spoonacular.com/recipes/complexSearch;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private AutoCompleteTextView autoCompleteTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---------- REFERENCEMENT DES VUES ----------------------

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        // Gérer interactions avec utilisateur
        button1.setEnabled(false);
        button2.setEnabled(true);//rend bouton grisé lorsque champ txt incomplet
        autoCompleteTextView.addTextChangedListener(new TextWatcher() { //Listener pour détecter activation du bouton
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                button1.setEnabled(s.toString().length() != 0); //dégrise le bouton
            }

            @Override
            public void afterTextChanged(Editable s) {
                // button2.setEnabled(quand un ingrédient est correctement ajouté);
            }
        });

        //--------------- Listener ADD -------------------------------------------
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {             //appelée à chaque fois que l'utilisateur clique sur le bouton ADD
                String firstname = autoCompleteTextView.getText().toString();
            }
        });

        //--------------- Listener NEXT ------------------------------------------
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {             //appelée à chaque fois que l'utilisateur clique sur le bouton NEXT
                Intent Screen2Intent = new Intent(MainActivity.this, Screen2.class);
                startActivity(Screen2Intent);  //fait démarrer la nouvelle activité
            }
        });

        //------------------- ASYNC TASK -------------------------------------------
        AsyncSpoonacularJSONData task = new AsyncSpoonacularJSONData(MainActivity.this);
        task.execute("https://api.spoonacular.com/food/ingredients/search" + "tags=luke_skywalker" + "&format=json");

    }

        @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}