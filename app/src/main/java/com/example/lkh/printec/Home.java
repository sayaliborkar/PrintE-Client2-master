package com.example.lkh.printec;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference db_reference1,db_ref_jobs;
    private FirebaseAuth firebaseAuth;
    String shopId,userId;
    List<save_job_info> values;
    List<String> displayJobs;
    ArrayAdapter<String> adapter;
    private String hno;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        final Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = firebaseAuth.getCurrentUser().getUid();
                if(switch1.isChecked()){
                    db_reference1.child("shop_owner").child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            save_user_info user = dataSnapshot.getValue(save_user_info.class);
                            hno = user.h_no;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    db_reference1.child("Online_printers").child(id).setValue(hno);
                }
                else
                {
                    db_reference1.child("Online_printers").child(id).setValue(null);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView user_nm = (TextView) header.findViewById(R.id.user_name);
        TextView user_ml = (TextView) header.findViewById(R.id.user_mail);
        String temp = getIntent().getExtras().getString("name");
        String name_string = "Hello " + temp;
        String mail_string = getIntent().getExtras().getString("mail");
        user_nm.setText(name_string);
        user_ml.setText(mail_string);
        Toast.makeText(Home.this, "Welcome " + temp,
                Toast.LENGTH_LONG).show();

        db_reference1 = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        final ListView listView = findViewById(R.id.listview);

        //String[] values = new String[] { "Job1","Job2","Job3"};
        db_ref_jobs = db_reference1.child("jobs");
        values = new ArrayList<save_job_info>();
        displayJobs = new ArrayList<>();
        /*adapter = new ArrayAdapter<String>(Home.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, displayJobs);*/
        db_ref_jobs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                values.clear();
                displayJobs.clear();
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    save_job_info user = single.getValue(save_job_info.class);
                    if(user.shop_id.equals(firebaseAuth.getCurrentUser().getUid()))
                    {
                        values.add(single.getValue(save_job_info.class));
                        displayJobs.add(single.getKey());
                    }
                    //System.out.println(single.getKey());
                }

                //listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition     = position;
                String  itemValue    = (String) listView.getItemAtPosition(position);
                save_job_info x = values.get(itemPosition);
                Intent intent = new Intent(Home.this, Jobs.class);
                intent.putExtra("obj",x);
                intent.putExtra("jobId",displayJobs.get(itemPosition));
                startActivity(intent);
            }

        });

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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_profile) {
            String id1 = firebaseAuth.getCurrentUser().getUid();
            db_reference1.child("shop_owner").child(id1).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            save_user_info user = dataSnapshot.getValue(save_user_info.class);


                            Intent intent = new Intent(Home.this, edit_profile.class);
                            intent.putExtra("name",user.name);
                            intent.putExtra("mail", user.email);
                            intent.putExtra("mobile", user.mobile);
                            intent.putExtra("hostel",user.h_no);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Home.this, "Connection lost...Try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_logout) {
            firebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(Home.this, "Successfully Logged out...",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
