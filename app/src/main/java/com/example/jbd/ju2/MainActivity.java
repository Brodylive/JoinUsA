package com.example.jbd.ju2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.jbd.ju2.DAO.CategorieDAO;
import com.example.jbd.ju2.models.Categorie;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView event;
    private TableLayout eventDesc;
    private ListView list;
    //LocalSQLiteOpenHelper sqLiteOpenHelper = new LocalSQLiteOpenHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        event = (TextView) findViewById(R.id.e0);
        eventDesc = (TableLayout) findViewById(R.id.te0);

        list = (ListView) findViewById(R.id.main_list);


        event.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentChild = null;
                intentChild = new Intent(MainActivity.this, EventActivity.class);
                startActivity(intentChild);
            }
        });

        eventDesc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentChild = null;
                intentChild = new Intent(MainActivity.this, EventActivity.class);
                startActivity(intentChild);
            }
        });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        ArrayList<Categorie> categorieList = Categorie.getCategorieList(this);
        CategorieAdapter categorieAdapter = new CategorieAdapter(this, categorieList);
        list.setAdapter(categorieAdapter);
    }

    private class LoadCategorie extends AsyncTask<String,Void,ArrayList<Categorie>>{

        @Override
        protected ArrayList<Categorie> doInBackground(String... params) {
            CategorieDAO categorieDAO = new CategorieDAO();
            ArrayList<Categorie> categories = new ArrayList<>();
            try {
                categories = categorieDAO.getAllCategories();
            } catch (Exception e) {

            }
            return categories;
        }
        @Override
        protected void onPostExecute(ArrayList<Categorie> categories){
            //categorieList.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intentChild = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create) {
            intentChild = new Intent(this, CreateEventActivity.class);
        } else if (id == R.id.action_search) {
            intentChild = new Intent(this, SearchEventActivity.class);
        }
        startActivity(intentChild);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intentChild = null;

        if (id == R.id.nav_flux) {
            intentChild = new Intent(this, MainActivity.class);
        } else if (id == R.id.nav_profil) {
            intentChild = new Intent(this, YourProfilActivity.class);
        } else if (id == R.id.nav_event) {
            intentChild = new Intent(this, EventsActivity.class);
        } else if (id == R.id.nav_search) {
            intentChild = new Intent(this, SearchEventActivity.class);
        } else if (id == R.id.nav_create) {
            intentChild = new Intent(this, CreateEventActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        startActivity(intentChild);
        return true;
    }
}
