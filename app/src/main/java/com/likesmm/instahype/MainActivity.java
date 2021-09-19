package com.likesmm.instahype;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.likesmm.instahype.main.Activity;
import com.likesmm.instahype.welcome.ApiClient;
import com.likesmm.instahype.welcome.ApiInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static PrefConfig prefConfig;

    public static ApiInterface apiInterface;
    public static String a="A1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration= new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        prefConfig = new PrefConfig(this);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        a="A2";

        if(!prefConfig.readLoginStatus()){



        }else {
            Intent intent = new Intent(this, Activity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


}