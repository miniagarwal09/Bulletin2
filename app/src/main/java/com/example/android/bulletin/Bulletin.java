package com.example.android.bulletin;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import com.google.firebase.iid.FirebaseInstanceId;
import com.uibinder.client.GreetingService;
import com.uibinder.client.GreetingServiceAsync;
import com.uibinder.shared.Article;

public class Bulletin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    SectionsPagerAdapter mSectionsPagerAdapter;
    public static GreetingServiceAsync greetingServiceAsync;
    ViewPager mViewPager;
    static TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  if( FirebaseInstanceId.getInstance().getToken()!=null){
        //String token = FirebaseInstanceId.getInstance().getToken();
        //new RemoteCall(this).execute(token);
        //}
   //     Log.d("___",token);
        tinyDB=new TinyDB(getApplicationContext());


        //Toolbar
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  toggle = new ActionBarDrawerToggle(
         //       this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //ViewPager
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //toggle.onConfigurationChanged(newConfig);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.bookmark) {
            Intent intent=new Intent(this,BookMark.class);
            startActivity(intent);

        } else if (id == R.id.interest) {
            Intent intent=new Intent(this,BookMark.class);
            startActivity(intent);
        } else if (id == R.id.notification) {
            Intent intent=new Intent(this,BookMark.class);
            startActivity(intent);;

        } else if (id == R.id.aboutus) {
            Intent intent=new Intent(this,BookMark.class);
            startActivity(intent);

        }else if (id ==R.id.termsofuse) {
            Intent intent=new Intent(this,BookMark.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Tab1 tab1;
            Tab1 tab2;
            Tab1 tab3;
            Tab1 tab4;
            Tab1 tab5;
            Tab1 tab6;
            Bundle b;

            switch (position){
                case 0:

                        tab1 = new Tab1();
                        b=new Bundle();
                        b.putString("Category","Trending");
                        tab1.setArguments(b);


                case 1:
                    tab2 = new Tab1();
                    b=new Bundle();
                    b.putString("Category","Trending");
                    tab2.setArguments(b);
                    return tab2;

                case 2:
                    tab3 = new Tab1();
                    b=new Bundle();
                    b.putString("Category","International");
                    tab3.setArguments(b);
                    return tab3;
                case 3:
                    tab4 = new Tab1();
                    b=new Bundle();
                    b.putString("Category","Sports");
                    tab4.setArguments(b);
                    return tab4;
                case 4:
                    tab5 = new Tab1();
                    b=new Bundle();
                    b.putString("Category","Entertainment");
                    tab5.setArguments(b);
                    return tab5;
                case 5:
                    tab6 = new Tab1();
                    b=new Bundle();
                    b.putString("Category","Other");
                    tab6.setArguments(b);

                    return tab6;

            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Trending News";
                case 1:
                    return "National";
                case 2:
                    return "International";
                case 3:
                    return  "Sports";
                case 4:
                    return  "Entertainment";
                case 5:
                    return  "Other";
            }
            return null;
        }
    }
}
