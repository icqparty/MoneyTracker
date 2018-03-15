package ru.icqparty.moneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle","onStart run");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle","onResume run");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle","onPause run");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle","onStop run");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle","onStart run");
    }
}
