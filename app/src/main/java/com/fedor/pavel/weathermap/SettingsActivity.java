package com.fedor.pavel.weathermap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fedor.pavel.weathermap.data.PreferencesManager;
import com.fedor.pavel.weathermap.models.CityModel;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private ListView lvCities;

    private ArrayAdapter<CityModel> citiesAdapter;

    public static final CityModel[] cities = {new CityModel("Dnepropetrovsk", 694165)

            , new CityModel("Kiev", 703448)

            , new CityModel("Lviv", 702550)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prepareToolbar();

        prepareListView();


    }

    private void prepareListView() {

        citiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, cities);

        lvCities = (ListView) findViewById(R.id.activity_settings_lv_cities);

        lvCities.setAdapter(citiesAdapter);

        lvCities.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        int position = citiesAdapter.getPosition(PreferencesManager.getInstance().loadCity());

        lvCities.setItemChecked(position, true);

        lvCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PreferencesManager.getInstance().saveCity(citiesAdapter.getItem(position));

                setResult(RESULT_OK);

                finish();
            }
        });

    }


    private void prepareToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setHomeButtonEnabled(true);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                this.finish();

                break;
        }

        return true;

    }
}
