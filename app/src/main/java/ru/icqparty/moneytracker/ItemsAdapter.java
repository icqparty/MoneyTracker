package ru.icqparty.moneytracker;

import android.content.Context;
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

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.RecordVievHolder> {

    private List<Item> data = new ArrayList<>();


    public ItemsAdapter() {
        createData();
    }

    @Override
    public ItemsAdapter.RecordVievHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemsAdapter.RecordVievHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.RecordVievHolder holder, int position) {
        Item record = data.get(position);
        holder.addData(record);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void createData() {
        data.add(new Item("Обои", 1300, ""));
        data.add(new Item("Интрумент (сверла,дрель,зубило,промышленный пылесос )", 5000, ""));
        data.add(new Item("Налог на имущество", 540, ""));
        data.add(new Item("Питание", 150, ""));
        data.add(new Item("ЖКХ", 3000, ""));
        data.add(new Item("Мастеринская плата", 10000, ""));
        data.add(new Item("Месячная подписка на Sketch", 1000, ""));
        data.add(new Item("Аренда сервера", 5600, ""));
        data.add(new Item("Аренда квартиры", 15000, ""));
        data.add(new Item("Билеты в кино", 300, ""));
        data.add(new Item("Ресторан", 4300, ""));
        data.add(new Item("Ресторан", 4300, ""));
        data.add(new Item("Ресторан", 4300, ""));
        data.add(new Item("Ресторан", 4300, ""));
    }

    static class RecordVievHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView value;


        private Context context;

        public RecordVievHolder(View itemView) {
            super(itemView);

            this.context = itemView.getContext();

            name = itemView.findViewById(R.id.name);
            value = itemView.findViewById(R.id.value);
        }

        public void addData(Item record) {
            name.setText(record.getName());
            value.setText(String.format("%1$s%2$s", String.valueOf(record.getValue()), this.context.getResources().getString(R.string.char_rub)));
        }
    }

}
