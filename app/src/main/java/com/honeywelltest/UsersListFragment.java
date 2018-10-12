package com.honeywelltest;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UsersListFragment extends Fragment implements ListemItemsListener {

    private RecyclerView itemsListRecyclerView;
    private ItemsAdapter itemsAdapter;
    ProgressDialog dialog;

    private TextView centerMessage;
    private List<Item> itemsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        //for crate home button
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        itemsListRecyclerView = view.findViewById(R.id.recycler_view);

        centerMessage = view.findViewById(R.id.centerMessage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        itemsListRecyclerView.setLayoutManager(linearLayoutManager);

        dialog = new ProgressDialog(getActivity());

        HoneywellUtils.getInstance().setListUpdateListner(this);

        itemsList = HoneywellUtils.getInstance().getData(getActivity());

        return view;
    }


    @Override
    public void onListUpdated(List<Item> itemList) {

        Log.d("suresh", "on list updated");
        itemsAdapter = new ItemsAdapter(getActivity(), this, this, itemList);
        itemsListRecyclerView.setAdapter(itemsAdapter);
        centerMessage.setVisibility(View.GONE);
    }

    @Override
    public void onErrorOccured(String message) {

        centerMessage.setVisibility(View.VISIBLE);
        centerMessage.setText(message);
        Log.d("suresh", "erro occured");
    }

    @Override
    public void onItemUpdated(Item item) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                HoneywellUtils.getInstance().getData(getActivity());
                break;
        }
        return true;
    }
}
