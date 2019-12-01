package com.hacks.imaginecup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.ArrayList;

public class AllergyActivity extends AppCompatActivity {
    private MaterialSpinner spinner;
    private SharedPreferences sharedPreferences;
    private static int i = 0;
    private Button mDone;
    private ListView mListView;
    private ArrayList<String> mAllergy;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy);

        sharedPreferences = getSharedPreferences("allergy", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            mAllergy = (ArrayList<String>) ObjectSerializer.
                    deserialize(sharedPreferences.getString("item",
                            ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        spinner = findViewById(R.id.spinner);
        mDone = findViewById(R.id.done);
        mListView = findViewById(R.id.listview);

        spinner.setItems("Select your item", "Sulphur", "Chlorine", "Potassium", "Sodium");
        spinner.setBackgroundColor(getColor(R.color.gray));
        spinner.setTextColor(getColor(R.color.white));
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id,
                                                 String item) {
                i += 1;
                Toast.makeText(AllergyActivity.this, item + " Selected", Toast.LENGTH_SHORT)
                        .show();
                mAllergy.add(item);
                try {
                    editor.putString("item", ObjectSerializer.serialize(mAllergy));
                    editor.apply();
                    String[] items = new String[mAllergy.size()];
                    int i = 0;
                    for (String s: mAllergy) {
                        items[i++] = s;
                    }
                    ArrayAdapter<String> arrayAdapter =
                            new ArrayAdapter<String>(AllergyActivity.this,
                                    R.layout.item_list, R.id.textView, items);
                    mListView.setAdapter(arrayAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllergyActivity.this, MainActivity.class));
            }
        });
    }
}
