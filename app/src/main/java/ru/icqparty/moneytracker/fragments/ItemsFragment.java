package ru.icqparty.moneytracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.icqparty.moneytracker.App;
import ru.icqparty.moneytracker.R;
import ru.icqparty.moneytracker.adapters.ItemsAdapter;
import ru.icqparty.moneytracker.api.Api;
import ru.icqparty.moneytracker.models.Item;

/**
 * Created by icqparty on 17.03.2018.
 */

public class ItemsFragment extends Fragment {
    private static final String TAG = "ItemsFragment";

    private static final String TYPE_KEY = "type";
    private ItemsAdapter itemsAdapter;
    private String type;

    private Api api;

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
        return view;
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
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
