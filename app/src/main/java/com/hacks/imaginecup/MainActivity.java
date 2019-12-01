package com.hacks.imaginecup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hacks.imaginecup.model.Medicine;
import com.hacks.imaginecup.utils.ApiClient;
import com.hacks.imaginecup.utils.ApiInterface;
import com.ramotion.foldingcell.FoldingCell;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ApiInterface mApi;
    private ListView mListView;
    private SharedPreferences mSharedPref;
    private ArrayList<String> mAllergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.mainListView);
        mSharedPref = getSharedPreferences("allergy", 0);
        getMedicine();
    }

    private void getMedicine() {
        mApi = ApiClient.getRetrofit("http://192.168.1.106:3000/")
                .create(ApiInterface.class);
        Call<List<Medicine>> medicineList = mApi.getMedicineList();
        medicineList.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(@NonNull Call<List<Medicine>> call,
                                   @NonNull Response<List<Medicine>> response) {
                List<Medicine> medList = response.body();
                List<Medicine> customerList = new ArrayList<>();
                if (medList != null) {
                    for (Medicine m : medList) {
                        String owner = m.getOwner().substring(m.getOwner().length() - 2);
                        if (owner.contains("C") || owner.contains("c")) {
                            customerList.add(m);
                        }
                    }
                    if (customerList.size() != 0) {
                        for (Medicine m : customerList) {
                            List<String> med = m.getContents();
                            for (String s: med) {
                                String[] parts = s.split("#");
                                try {
                                    mAllergy = (ArrayList<String>) ObjectSerializer.
                                            deserialize(mSharedPref.getString("item",
                                                    ObjectSerializer.serialize(new ArrayList<String>())));
                                    for (String s1: mAllergy) {
                                        if (s1.toLowerCase().equals(parts[1].toLowerCase())) {
                                            Alerter.create(MainActivity.this)
                                                    .setTitle("Allergy found in " + m.getMedicineName())
                                                    .setText("Allergy is found. pls be aware")
                                                    .show();
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        final FoldingCellListAdapter adapter =
                                new FoldingCellListAdapter(MainActivity.this, customerList);
                        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                        mListView.setAdapter(adapter);

                        // set on click event listener to list view
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos,
                                                    long l) {
                                // toggle clicked cell state
                                ((FoldingCell) view).toggle(false);
                                // register in adapter that state for selected cell is toggled
                                adapter.registerToggle(pos);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Medicine>> call, Throwable t) {
                Log.d("MainAc", t.getLocalizedMessage());
                call.cancel();
            }
        });
    }
}
