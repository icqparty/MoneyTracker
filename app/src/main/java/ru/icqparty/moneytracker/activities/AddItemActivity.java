package ru.icqparty.moneytracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import ru.icqparty.moneytracker.R;
import ru.icqparty.moneytracker.models.Item;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";
    public static final String TYPE_KEY = "type";


    private String type;

    private EditText nameItem;
    private EditText valueItem;
    private Button addButtonItem;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        type = getIntent().getStringExtra(TYPE_KEY);
        nameItem = findViewById(R.id.name_item);
        valueItem = findViewById(R.id.value_item);
        addButtonItem = findViewById(R.id.add_button_item);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextWatcher watcherChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (nameItem.getText().length() > 0 && valueItem.getText().length() > 0)
                    addButtonItem.setEnabled(true);
                else addButtonItem.setEnabled(false);
            }
        };

        //устанавливаем слушатель на изменение текста в полях
        nameItem.addTextChangedListener(watcherChange);
        valueItem.addTextChangedListener(watcherChange);

        //устанавливаем обрадотку клика по кнопке
        addButtonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = valueItem.getText().toString();
                String name = nameItem.getText().toString();
                String date = new Date().toString();

                Item item = new Item(type, name, value, date);
                Intent intent = new Intent();
                intent.putExtra("item", item);
                setResult(RESULT_OK, intent);
                Log.i(TAG, "onClick: add_button_item");
                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);


    }
}
