package ru.icqparty.moneytracker.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ru.icqparty.moneytracker.R;
import ru.icqparty.moneytracker.adapters.MainPagesAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainPagesAdapter(getSupportFragmentManager(), this));
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle", "onStart run");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle", "onResume run");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle", "onPause run");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle", "onStop run");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle", "onStart run");
    }
}
