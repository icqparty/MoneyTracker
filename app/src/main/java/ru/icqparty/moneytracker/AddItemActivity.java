package ru.icqparty.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    private EditText nameItem;
    private EditText valueItem;
    private Button addButtonItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        nameItem = findViewById(R.id.name_item);
        valueItem = findViewById(R.id.value_item);
        addButtonItem = findViewById(R.id.add_button_item);

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
                Log.i(TAG, "onClick: add_button_item");
            }
        });


    }
}
