package com.nativenote.realmwithviewmodel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nativenote.realmwithviewmodel.databinding.ActivityMainBinding;
import com.nativenote.realmwithviewmodel.list.ListActivity;
import com.nativenote.realmwithviewmodel.model.StoreInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readCSV();
    }

    private void readCSV() {

        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("superstore.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    saveData(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveData(final String[] data) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                StoreInfo storeInfo = new StoreInfo(
                        Long.valueOf(data[0]),
                        data[1],
                        data[2],
                        data[5],
                        data[6],
                        data[7],
                        data[8],
                        data[9],
                        data[13],
                        data[14],
                        data[15],
                        data[16],
                        Double.valueOf(data[17]),
                        Long.valueOf(data[18]),
                        Double.valueOf(data[19]),
                        Double.valueOf(data[20])
                );

                realm1.copyToRealmOrUpdate(storeInfo);
            });
        } catch (Exception e) {
            if (realm != null)
                realm.close();
        }
    }
}
