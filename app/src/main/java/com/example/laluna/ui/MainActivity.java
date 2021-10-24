package com.example.laluna.ui;

import android.graphics.Color;
import android.os.Bundle;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;
import com.example.laluna.R;
import com.example.laluna.ui.home.addUpdateExpense.AddExpenseViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents the main page of the application that includes three fragments.
 */
public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel =
                ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.init(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_analysis,R.id.navigation_categories, R.id.navigation_help)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    /**
     * A method for initializing the database object and adding the default categories that have been chosen by this application's developers.
     * Its adds some expenses for the testing as well.
     */


    }


