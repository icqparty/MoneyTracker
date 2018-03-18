package ru.icqparty.moneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icqparty on 17.03.2018.
 */

public class ItemListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Record> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        recyclerView = findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        createData();  //заполняем лист данными
        recyclerView.setAdapter(new ItemListAdapter());
    }

    private void createData() {
        mData.add(new Record("Обои", 1300, ""));
        mData.add(new Record("Интрумент (сверла,дрель,зубило,промышленный пылесос )", 5000, ""));
        mData.add(new Record("Налог на имущество", 540, ""));
        mData.add(new Record("Питание", 150, ""));
        mData.add(new Record("ЖКХ", 3000, ""));
        mData.add(new Record("Мастеринская плата", 10000, ""));
        mData.add(new Record("Месячная подписка на Sketch", 1000, ""));
        mData.add(new Record("Аренда сервера", 5600, ""));
        mData.add(new Record("Аренда квартиры", 15000, ""));
        mData.add(new Record("Билеты в кино", 300, ""));
        mData.add(new Record("Ресторан", 4300, ""));
        mData.add(new Record("Ресторан", 4300, ""));
        mData.add(new Record("Ресторан", 4300, ""));
        mData.add(new Record("Ресторан", 4300, ""));
    }

    private class ItemListAdapter extends RecyclerView.Adapter<RecordVievHolder> {
        @Override
        public RecordVievHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecordVievHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false));
        }

        @Override
        public void onBindViewHolder(RecordVievHolder holder, int position) {
            Record record = mData.get(position);
            holder.addData(record);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }

    private class RecordVievHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView value;

        public RecordVievHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            value = itemView.findViewById(R.id.value);
        }

        public void addData(Record record) {
            name.setText(record.getName());
            value.setText(String.format("%1$s%2$s", String.valueOf(record.getValue()), getResources().getString(R.string.char_rub)));
        }
    }
}
