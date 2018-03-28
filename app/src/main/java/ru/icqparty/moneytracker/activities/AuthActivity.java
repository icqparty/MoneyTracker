package ru.icqparty.moneytracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import ru.icqparty.moneytracker.App;
import ru.icqparty.moneytracker.R;
import ru.icqparty.moneytracker.api.Api;
import ru.icqparty.moneytracker.models.AuthResult;

public class AuthActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 321;

    private static final String TAG = "AuthActivity";

    private GoogleSignInClient googleSignInClient;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        api = ((App) getApplication()).getApi();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        Button button = findViewById(R.id.singInButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


    }


    private void signIn() {
        Log.i(TAG, "signIn: ");
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.i(TAG, "signInResult:account" + requestCode + "dddd" + requestCode);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.i(TAG, "signInResult:account" + account);
            updateUI(account);
        } catch (ApiException e) {
            Log.i(TAG, "signInResult1:failed code=" + e.getMessage());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            String id = account.getId();
            api.auth(id).enqueue(new Callback<AuthResult>() {
                @Override
                public void onResponse(Call<AuthResult> call, retrofit2.Response<AuthResult> response) {
                    AuthResult result = response.body();
                    Log.i(TAG, "onResponse: OK");
                    ((App) getApplication()).setAuthToken(result.token);
                    finish();

                }

                @Override
                public void onFailure(Call<AuthResult> call, Throwable t) {
                    showMessage("Error", "Auth failed " + t.getMessage());
                }
            });
        }
    }

    private void showMessage(String type, String message) {
        Toast.makeText(this, type + ":" + message, Toast.LENGTH_SHORT).show();
    }


}
