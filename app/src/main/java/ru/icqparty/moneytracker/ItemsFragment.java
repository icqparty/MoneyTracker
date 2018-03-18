package ru.icqparty.moneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by icqparty on 17.03.2018.
 */

public class ItemsFragment extends Fragment {

    public static final int TYPE_INCOMES = 0;
    public static final int TYPE_EXPENSES = 1;
    private static final int TYPE_UNKNOWN = -1;

    private static final String TYPE_KEY = "type";
    private ItemsAdapter itemsAdapter;

    public static ItemsFragment createItemsFragment(int type) {
        ItemsFragment itemsFragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ItemsFragment.TYPE_KEY, type);
        itemsFragment.setArguments(bundle);
        return itemsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapter = new ItemsAdapter();

        Bundle bundle = getArguments();

        if (bundle.getInt(TYPE_KEY, TYPE_UNKNOWN) == TYPE_UNKNOWN) {
            Toast.makeText(getContext(), "Type UNKNOWN", Toast.LENGTH_SHORT).show();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items_fragment, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(itemsAdapter);
    }
}
