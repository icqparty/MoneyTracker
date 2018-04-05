package ru.icqparty.moneytracker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.icqparty.moneytracker.App;
import ru.icqparty.moneytracker.R;
import ru.icqparty.moneytracker.activities.RemoveDialog;
import ru.icqparty.moneytracker.activities.RemoveDialogListener;
import ru.icqparty.moneytracker.adapters.ItemsAdapter;
import ru.icqparty.moneytracker.adapters.ItemsAdapterListener;
import ru.icqparty.moneytracker.api.Api;
import ru.icqparty.moneytracker.models.AddItemResult;
import ru.icqparty.moneytracker.models.Item;
import ru.icqparty.moneytracker.models.RemoveItemResult;

/**
 * Created by icqparty on 17.03.2018.
 */

public class ItemsFragment extends Fragment {
    public static final int ADD_ITEM_REQUEST_CODE = 1;
    private static final String TAG = "ItemsFragment";
    private static final String TYPE_KEY = "type";
    private static final int RESULT_ADD_OK = 1;
    private ItemsAdapter itemsAdapter;
    private String type;

    private Api api;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode = null;


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
        itemsAdapter.setListener(new ClickListener());

        Bundle bundle = getArguments();
        type = bundle.getString(TYPE_KEY, Item.TYPE_UNKNOWN);

        if (type.equals(Item.TYPE_UNKNOWN)) {
            throw new IllegalArgumentException("Unknown type");
        }

        api = ((App) getActivity().getApplication()).getApi();
    }


    private void removeDialog() {
        RemoveDialog dialog = new RemoveDialog();
        dialog.show(getFragmentManager(), "ConfirmationDialog");
        dialog.setListener(new RemoveDialogListener() {
            @Override
            public void onPositiveBtnClicked() {

                removeSelectedItems();
            }

            @Override
            public void onNegativeBtnClicked() {
                actionMode.finish();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items_fragment, null, false);

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
            if (item.type.equals(type)) {
                addApiItem(item);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(itemsAdapter);
        loadItems();
    }

    private void loadItems() {
        api.getItems(type).enqueue(new Callback<List<Item>>() {
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

    private void addApiItem(final Item item) {
        api.addItem(item.value, item.name, item.type).enqueue(new Callback<AddItemResult>() {
            @Override
            public void onResponse(Call<AddItemResult> call, retrofit2.Response<AddItemResult> response) {
                Log.d(TAG, "onResponse: add item " + response.code() + " : " + item.status);

                if (response.code() > 200) {
                    item.status = 1;
                    Toast.makeText(getContext(), "Сервер временно не доступен", Toast.LENGTH_SHORT).show();

                }
                itemsAdapter.addItem(item);
            }

            @Override
            public void onFailure(Call<AddItemResult> call, Throwable t) {
                Log.d(TAG, "Error:" + t.getMessage());
            }
        });
    }

    /* ACTION MODE */
    private void removeSelectedItems() {
        Log.i(TAG, "removeSelectedItems: " + itemsAdapter.getSelectedItems().size());
        for (int i = itemsAdapter.getSelectedItems().size() - 1; i >= 0; i--) {
            Item item = itemsAdapter.remove(itemsAdapter.getSelectedItems().get(i));
            Call<RemoveItemResult> call = api.remove(item.id);
            call.enqueue(new Callback<RemoveItemResult>() {
                @Override
                public void onResponse(Call<RemoveItemResult> call, Response<RemoveItemResult> response) {

                }

                @Override
                public void onFailure(Call<RemoveItemResult> call, Throwable t) {

                }
            });

        }
        actionMode.finish();

    }

    private ActionMode.Callback actionModelCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = new MenuInflater(getContext());
            menuInflater.inflate(R.menu.items_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.remove: {
                    removeDialog();
                    break;
                }
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            itemsAdapter.clearSelection();
            actionMode = null;
        }
    };

    private class ClickListener implements ItemsAdapterListener {
        @Override
        public void onItemClick(Item item, int position) {
            if (isInActionMode()) {
                toggleSelection(position);
            }
        }

        @Override
        public void onItemLongCLick(Item item, int position) {
            if (isInActionMode()) {
                return;
            }

            actionMode = ((AppCompatActivity) Objects.requireNonNull(getActivity())).startSupportActionMode(actionModelCallback);
            toggleSelection(position);
        }


        private void toggleSelection(int position) {
            itemsAdapter.toggleSeletion(position);
        }
    }

    private boolean isInActionMode() {
        return actionMode != null;
    }
}
