package com.codename26.childanalysis;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.codename26.childanalysis.Ads.AdManager;
import com.codename26.childanalysis.Analysis.AnalysisActivity;
import com.codename26.childanalysis.DB.DataBaseHelper;
import com.codename26.childanalysis.DB.MyDatabase;
import com.codename26.childanalysis.Subcategories.SubcategoriesActivity;
import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.Calendar;

import ru.apperate.ads.delegate.InterstitialAdListener;
import ru.apperate.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    public static final String CATEGORIES_TABLE_NAME = "categories";
    public static final String COLUMN_ID = "_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_NAME_LC = "category_name_lc";
    public static final String SUBCATEGORIES_TABLE_NAME = "subcategory";
    public static final String SUBCATEGORIES_ANALYSIS_TABLE_NAME = "subcategories_analysis";
    public static final String SUBCATEGORY_NAME = "subcategory_name";
    public static final String SUBCATEGORY_NAME_LC = "subcategory_name_lc";
    public static final String CATEGORY_ID = "category_id";
    public static final String SUBCATEGORY_ID = "subcategory_id";
    public static final String ANALYSIS_ID = "analysis_id";
    public static final String ANALYSIS_TABLE_NAME = "analysis";
    public static final String ANALYSIS_NAME = "analysis_name";
    public static final String SEX = "sex";
    public static final String AGE = "age";
    public static final String VALUE = "analysis_value";
    public static final String UNITS = "units";
    public static final String AGE_TABLE_NAME = "age";
    public static final String AGE_NAME = "age_name";
    public static final String SEX_TABLE_NAME = "sex_table";
    public static final String SEX_NAME = "sex_name";
    public static final String CATEGORY = "category";
    public static final String ANALYSIS_ARRAY = "analysis_array";
    public static final String EXTRA_SEX = "EXTRA_SEX";
    public static final String EXTRA_AGE = "EXTRA_AGE";
    public static final String EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    public static final String SUBCATEGORY_COLUMN_ID = "SUBCATEGORY_COLUMN_ID";
    public static final String CATEGORIES_COLUMN_ID = "CATEGORIES_COLUMN_ID";
    public static final String ANALYSIS_COLUMN_ID = "ANALYSIS_COLUMN_ID";
    public static final String ACTION_SEARCH_RESULT = "ACTION_SEARCH_RESULT";
    public static final String ACTION_SUBCATEGORY_ANALYSIS = "ACTION_SUBCATEGORY_ANALYSIS";
    public static final String CATEGORY_HAS_SUBCATEGORIES = "has_subcategory";
    public static final String ACTION_CATEGORY = "ACTION_CATEGORY";
    public static final String CATEGORY_ANALYSIS_TABLE_NAME = "category_analysis";
    private static final String ACTION_SUBCATEGORY = "ACTION_SUBCATEGORY";
    public static final String SUBCATEGORY_TABLE_NAME = "subcategory";
    public static final String ANALYSIS_NAME_LC = "analysis_name_lc";
    public static final String URL = "analysis_url";
    public static final String CATEGORY_ICON = "category_icon";
    public static final String WEBVIEWURL = "web_view_url";
    public static final String COMPLEX_ANALYSIS_TABLE_NAME = "analysis_complex";
    public static final String COMPLEX_ANALYSIS_PARENT_ID = "parent_id";
    public static final String COMPLEX_ANALYSIS_TEXT = "text";
    public static final String COMPLEX_ANALYSIS_VALUE = "_value";
    public static final String COMPLEX_ANALYSIS_UNITS = "units";
    public static final String COMPLEX_ANALYSIS_SEX = "sex";
    public static final String COMPLEX_ANALYSIS_GROUP = "_group";
    public static final String COMPLEX_ANALYSIS_GROUP_NAME = "group_name";
    public static final String PRIVACY_POLICY_URL = "https://gradusnik.net/privacy-policy-politika-konfidentsialnosti/";
    public static String SUBCATEGORY_ANALYSIS_TABLE_NAME = "subcategory_analysis";
    private static final String SHOW_ADS = "show_ads";

    private ArrayList<Category> categories;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyDatabase mMyDatabase;
    private DataBaseHelper helper;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private long cacheExpiration = 0;

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.ADMOB_APP_ID));
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        mMyDatabase = new MyDatabase(MainActivity.this);
        helper = new DataBaseHelper(MainActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //
        categories = mMyDatabase.getCategories(0);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(this, R.layout.list_item, categories);
        mRecyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new RecyclerAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(Category category) {
//                Log.d("Category_has_sub", String.valueOf(category.getHasSubcategory()));
                if (category.getHasSubcategory() > 0) {
                    Intent intent = new Intent(MainActivity.this, SubcategoriesActivity.class);
                    intent.putExtra(MainActivity.EXTRA_CATEGORY, category);
                    intent.setAction(MainActivity.ACTION_SUBCATEGORY);
                    startActivity(intent);
                } else if (category.getHasSubcategory() == 0) {
                    Intent intent = new Intent(MainActivity.this, AnalysisActivity.class);
                    Log.d("Send Category", category.getCategoryName());
                    intent.putExtra(MainActivity.EXTRA_CATEGORY, category);
                    intent.setAction(MainActivity.ACTION_CATEGORY);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Firebase Remote Config
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
//                            Toast.makeText(MainActivity.this, "Fetch succesfull", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, String.valueOf(mFirebaseRemoteConfig.getBoolean(SHOW_ADS)), Toast.LENGTH_SHORT).show();
                            if (mFirebaseRemoteConfig.getBoolean(SHOW_ADS)){
//                                Log.d("Show ads ", "True");
                                initAd();
                            }
                        }
                    }
                });

    }

    private void initAd() {
//        Log.d("InitAd ", "True");
        AdManager adManager = new AdManager(this);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (day != adManager.getDay()) {
            adManager.setDay(day);

            InterstitialAd interstitialAd = new InterstitialAd(this);

            interstitialAd.setInterstitialAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialLoaded(InterstitialAd interstitialAd) {
                    Log.d("onInterstitialLoaded","Show ad");
                    interstitialAd.show();

                }

                @Override
                public void onInterstitialShowed() {

                }

                @Override
                public void onInterstitialClicked() {

                }

                @Override
                public void onInterstitialClosed() {

                }

                @Override
                public void onInterstitialError(Error error) {

                }
            });
//            Log.d("interstitialAd.load","interstitialAd.load");
            interstitialAd.load();

        }
    }


    //Shows Dialog with 3 buttons when Back is pressed
    @Override
    public void onBackPressed() {
        Dialog mDialog = new Dialog(MainActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.on_back_pressed_dialog);
        mDialog.setCancelable(true);
        mDialog.show();
        TextView tvExit = (TextView) mDialog.getWindow().findViewById(R.id.tvClose);
        TextView tvRate = mDialog.getWindow().findViewById(R.id.tvRateThisApp);
        //  TextView tvOtherApps = mDialog.getWindow().findViewById(R.id.tvOtherApps);
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
      /*  tvOtherApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                String devName = getResources().getString(R.string.dev_name);
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=" + devName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + devName)));
                }
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                // ATTENTION: This was auto-generated to handle app links.
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(PRIVACY_POLICY_URL));
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyDatabase.close();
        helper.close();

    }
}
