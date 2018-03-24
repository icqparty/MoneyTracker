package ru.icqparty.moneytracker.adapters;

import ru.icqparty.moneytracker.models.Item;

public interface ItemsAdapterListener {
    void onItemClick(Item item, int position);

    void onItemLongCLick(Item item, int position);
}