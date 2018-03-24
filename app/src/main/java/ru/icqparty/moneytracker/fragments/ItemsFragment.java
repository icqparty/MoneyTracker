package ru.icqparty.moneytracker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.icqparty.moneytracker.App;
import ru.icqparty.moneytracker.R;
import ru.icqparty.moneytracker.activities.AddItemActivity;
import ru.icqparty.moneytracker.adapters.ItemsAdapter;
import ru.icqparty.moneytracker.api.Api;
import ru.icqparty.moneytracker.models.Item;

/**
 * Created by icqparty on 17.03.2018.
 */

public class ItemsFragment extends Fragment {
    private static final String TAG = "ItemsFragment";

    private static final String TYPE_KEY = "type";
    private static final int ADD_ITEM_REQUEST_CODE = 1;
    private static final int RESULT_ADD_OK = 1;
    private ItemsAdapter itemsAdapter;
    private String type;

    private Api api;

    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ItemsFragment createItemsFragment(String type) {
        ItemsFragment itemsFragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.TYPE_KEY, type);
        itemsFragment.setArguments(bundle);
        return itemsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapter = new ItemsAdapter();

        Bundle bundle = getArguments();
        type = bundle.getString(TYPE_KEY, Item.TYPE_UNKNOWN);

        if (type.equals(Item.TYPE_UNKNOWN)) {
            throw new IllegalArgumentException("Unknown type");
        }

        api = ((App) getActivity().getApplication()).getApi();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items_fragment, null, false);

        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: add item button");

                Intent intentAddItem = new Intent(getContext(), AddItemActivity.class);
                intentAddItem.putExtra(AddItemActivity.TYPE_KEY, type);
                startActivityForResult(intentAddItem, ADD_ITEM_REQUEST_CODE);
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });
        
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Item item = data.getParcelableExtra("item");
            Log.d(TAG, "onActivityResult: get item" + item);
            itemsAdapter.addItem(item);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(itemsAdapter);

        loadItems();
    }

    private void loadItems() {
        api.get(type).enqueue(new Callback<List<Item>>() {
            @Override

            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                itemsAdapter.loadData(response.body());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
