package com.example.laluna.ui;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        insertToDB();

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

    public void insertToDB(){
        DBHandler db = new DBHandler(this);

        this.deleteDatabase("laluna.db");

        List<Category> cat = new ArrayList<>();

        Category food = db.addCategory("Food",1500,R.drawable.food,"#09F9BF",new Date(121,7,1));
        Category clothes = db.addCategory("Clothes",3000,R.drawable.clothes,"#03A9F1",new Date(121,7,1));
        Category house = db.addCategory("House",2000,R.drawable.home,"#dd6800",new Date(121,7,1));
        Category entertainment = db.addCategory("Entertainment",3000,R.drawable.entertainment,"#dd68ca",new Date(121,7,1));
        Category health = db.addCategory("Health",1000,R.drawable.health,"#f30008",new Date(121,7,1));
        Category other = db.addCategory("Other",3000,R.drawable.other,"#737274",new Date(121,7,1));

        cat.add(food);
        cat.add(clothes);
        cat.add(house);
        cat.add(entertainment);
        cat.add(health);
        cat.add(other);


        addExpenses(cat,new Date(121,7,1), db);
        db.setCategoriesPreviousLimits(new Date(121,7,1));
        addExpenses(cat,new Date(121,8,1), db);
       // db.setCategoriesPreviousLimits(new Date(121,8,1));
       // addExpenses(cat,new Date(121,9,1), db);


    }

    private void addExpenses(List<Category> cat, Date date, DBHandler handler){

        handler.addExpense("MAX Burger", 100,date,cat.get(0));
        handler.addExpense("Pizza", 100,date,cat.get(0));
        handler.addExpense("Tikka masala", 100,date,cat.get(0));
        handler.addExpense("Godis", 100,date,cat.get(0));

        handler.addExpense("T shirt", 100,date,cat.get(1));
        handler.addExpense("Skor", 100,date,cat.get(1));
        handler.addExpense("Tröja", 100,date,cat.get(1));


        handler.addExpense("Lampor", 100,date,cat.get(2));
        handler.addExpense("Lampor", 100,date,cat.get(2));
        handler.addExpense("Lampor", 100,date,cat.get(2));


        handler.addExpense("Cs", 500,date,cat.get(3));
        handler.addExpense("Skarasommarland", 500,date,cat.get(3));
        handler.addExpense("Liseberg", 500,date,cat.get(3));


        handler.addExpense("Bil olja", 2000,date,cat.get(5));
        handler.addExpense("Tandläkare", 700,date,cat.get(4));
        handler.addExpense("Liseberg", 500,date,cat.get(3));
        handler.addExpense("Lampor", 100,date,cat.get(2));
        handler.addExpense("Jacka", 100,date,cat.get(1));
        handler.addExpense("Fisk", 100,date,cat.get(0));


    }

    public void tempInsertToDB(){

        DBHandler db = new DBHandler(this);

        this.deleteDatabase("laluna.db");


        Category c1 = db.addCategory("Food",3000,R.drawable.categorycar, Integer.toString(Color.MAGENTA) ,new Date(121,9,2));

        Category c18 = db.addCategory("Car",1000,R.drawable.food,Integer.toString(Color.rgb(3, 169, 241)),new Date(121,8,1));
        Category c28 = db.addCategory("Drinks",3000,R.drawable.ic_home_black_24dp,Integer.toString(Color.rgb(9, 249, 191)),new Date(121,8,1));


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
