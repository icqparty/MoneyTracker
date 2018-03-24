package ru.icqparty.moneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.icqparty.moneytracker.R;
import ru.icqparty.moneytracker.models.Item;

/**
 * Created by icqparty on 17.03.2018.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.RecordVievHolder> {

    private List<Item> data = new ArrayList<>();

    public void loadData(List<Item> data) {
        this.data = data;
        notifyItemInserted(data.size());
    }

    @Override
    public RecordVievHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordVievHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecordVievHolder holder, int position) {
        Item item = data.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(Item item) {
        data.add(item);
        notifyDataSetChanged();
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

        public void setData(Item item) {
            name.setText(item.name);
            value.setText(String.format("%1$s %2$s", String.valueOf(item.value), this.context.getResources().getString(R.string.char_rub)));
        }
    }

}
