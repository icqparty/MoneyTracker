package ru.icqparty.moneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private ItemsAdapterListener itemsAdapterListener = null;
    private SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
    private static final String TAG = "ItemsAdapter";

    public void setListener(ItemsAdapterListener listener) {
        this.itemsAdapterListener = listener;
    }

    public void loadData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecordVievHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordVievHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecordVievHolder holder, int position) {
        Item item = data.get(position);
        holder.setData(item, position, itemsAdapterListener, sparseBooleanArray.get(position, false));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(Item item) {
        data.add(item);
        notifyItemInserted(data.size());
    }

    public void toggleSeletion(int position) {
        if (sparseBooleanArray.get(position, false)) {
            sparseBooleanArray.delete(position);
        } else {
            sparseBooleanArray.put(position, true);
        }

        notifyDataSetChanged();
    }

    public void clearSelection() {
        sparseBooleanArray.clear();
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(sparseBooleanArray.size());
        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            Log.i("log", "getSelectedItems: +");
            items.add(sparseBooleanArray.keyAt(i));
        }
        return items;
    }

    public Item remove(int position) {
        final Item item = data.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    static class RecordVievHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView value;
        private ImageView status;

        private Context context;

        public RecordVievHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            name = itemView.findViewById(R.id.name);
            value = itemView.findViewById(R.id.value);
            status = itemView.findViewById(R.id.status);

        }

        public void setData(final Item item, final int position, final ItemsAdapterListener itemsAdapterListener, boolean selected) {
            name.setText(item.name);
            value.setText(String.format("%1$s %2$s", String.valueOf(item.value), this.context.getResources().getString(R.string.char_rub)));
            Log.d(TAG, "setData: " + item.status);
            if (item.status == 0) {
                status.setVisibility(View.GONE);
            } else {
                status.setVisibility(View.VISIBLE);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemsAdapterListener != null) {
                        itemsAdapterListener.onItemClick(item, position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (itemsAdapterListener != null) {
                        itemsAdapterListener.onItemLongCLick(item, position);
                    }
                    return true;
                }
            });

            itemView.setActivated(selected);
        }
    }

}
