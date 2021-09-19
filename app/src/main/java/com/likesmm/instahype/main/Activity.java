package com.likesmm.instahype.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.likesmm.instahype.Data;
import com.likesmm.instahype.MainActivity;
import com.likesmm.instahype.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.likesmm.instahype.welcome.ApiClient;
import com.likesmm.instahype.welcome.ApiInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;

public class Activity extends AppCompatActivity {
    public static ApiInterface apiInterface1;
public static Data DATA1;
public static String a="a1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_main);
        NavigationUI.setupWithNavController(navigationView,navController);

        apiInterface1 = ApiClient.getApiClient().create(ApiInterface.class);
        a="a2";
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_exit){
            MainActivity.prefConfig.writeLoginStatus(false);
            MainActivity.prefConfig.writeEmail("");
            Intent intent=new Intent(Activity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
