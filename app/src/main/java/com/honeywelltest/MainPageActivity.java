package com.honeywelltest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainPageActivity extends AppCompatActivity {

    private UsersListFragment usersListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_layout);

        usersListFragment = new UsersListFragment();

        getFragmentManager().beginTransaction().add(R.id.container, usersListFragment, "").commit();
    }


}
