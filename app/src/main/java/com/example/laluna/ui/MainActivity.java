package com.example.laluna.ui;

import android.os.Bundle;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.DBHandler;
import com.example.laluna.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tempInsertToDB();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_analysis,R.id.navigation_categories, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    public void tempInsertToDB(){

        DBHandler db = new DBHandler(this);

        this.deleteDatabase("laluna.db");


        Category c1 = db.addCategory("Food",3000,R.drawable.categorycar,null,new Date(121,9,2));

        Category c18 = db.addCategory("Car",1000,R.drawable.food,null,new Date(121,8,1));
        Category c28 = db.addCategory("Drinks",3000,R.drawable.ic_home_black_24dp,null,new Date(121,8,1));


        db.addExpense("Pizza", 80,new Date(),c1);
        db.addExpense("KFC", 20,new Date(),c1);
        db.addExpense("Pizza", 100,new Date(),c1);
        db.addExpense("Cheese", 300,new Date(),c1);
        db.addExpense("Tacos", 200,new Date(),c1);

        //does not count



        db.addExpense("Window", 80,new Date(121,8,2),c18);
        db.addExpense("Gear", 20,new Date(121,8,2),c18);
        db.addExpense("Pip", 100,new Date(121,8,2),c18);
        db.addExpense("Oil", 300,new Date(121,8,2),c18);
        db.addExpense("Oil", 200,new Date(121,8,2),c18);

        //does not count

        db.addExpense("Vodka", 80,new Date(121,8,2),c28);
        db.addExpense("Bear", 500,new Date(121,8,2),c28);
        db.addExpense("Coca cola", 200,new Date(121,8,2),c28);
        db.addExpense("Pepsi", 500,new Date(121,8,2),c28);

        db.addExpense("Fanta", 700,new Date(),c28);
        db.addExpense("whiskey", 300,new Date(),c18);

        db.addExpense("Oil", 80,new Date(121,8,2),c18);
        db.addExpense("Motor", 80,new Date(121,8,2),c18);
        db.addExpense("Burger", 80,new Date(),c1);
        db.addExpense("Fish", 80,new Date(),c1);

        db.setCategoriesPreviousLimits(new Date(121,8,1));


    }


}
