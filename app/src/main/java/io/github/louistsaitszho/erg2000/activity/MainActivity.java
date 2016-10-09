package io.github.louistsaitszho.erg2000.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.cketti.library.changelog.ChangeLog;
import io.github.louistsaitszho.erg2000.Consts;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.SearchParams;
import io.github.louistsaitszho.erg2000.fragment.HistoryFragment;
import io.github.louistsaitszho.erg2000.fragment.SearchFragment;
import io.github.louistsaitszho.erg2000.fragment.StatisticFragment;
import io.github.louistsaitszho.erg2000.interfaces.HideFAB;
import io.github.louistsaitszho.erg2000.realm.RealmController;

public class MainActivity extends AppCompatActivity implements HideFAB, ActivityCompat.OnRequestPermissionsResultCallback {

  public static final String TAG = MainActivity.class.getSimpleName();
  private static final int REQUEST_CODE = 6174;

  ChangeLog cl;

  @BindView(R.id.toolbar)             Toolbar toolbar;
  @BindView(R.id.coordinatorLayout)   CoordinatorLayout coordinatorLayout;
  @BindView(R.id.bottom_navigation)   AHBottomNavigation bottomNavigation;
  @BindView(R.id.fragment_container)  FrameLayout fragmentContainer;
  @BindView(R.id.fab)                 FloatingActionButton floatingActionButton;

  HistoryFragment historyFragment;
  SearchFragment searchFragment;
  StatisticFragment statisticFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme_NoActionBar);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    //TODO replace it with changeloglib?
    cl = new ChangeLog(this);

    writeToStoragePermissionHandling();

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setIcon(R.drawable.actionbar_icon);
      getSupportActionBar().setTitle(R.string.history);
    }

    inflateBottomNavigationBar();
    floatingActionButton.setImageDrawable(new IconicsDrawable(this).colorRes(R.color.colorPrimary).icon(CommunityMaterial.Icon.cmd_plus).sizeDp(12));
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openAddRecordActivity();
      }
    });

    if (fragmentContainer != null) {
      if (savedInstanceState != null) {
        return;
      }
      historyFragment = HistoryFragment.newInstance();
      getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, historyFragment).commit();
    }

  }

  private void inflateBottomNavigationBar() {
    AHBottomNavigationItem ahbniHistory = new AHBottomNavigationItem(getString(R.string.history), new IconicsDrawable(this).sizeDp(22).icon(CommunityMaterial.Icon.cmd_history));
    AHBottomNavigationItem ahbniSearch = new AHBottomNavigationItem(getString(R.string.search), new IconicsDrawable(this).sizeDp(22).icon(CommunityMaterial.Icon.cmd_magnify));
    AHBottomNavigationItem ahbniStatistics = new AHBottomNavigationItem(getString(R.string.statistics), new IconicsDrawable(this).sizeDp(22).icon(CommunityMaterial.Icon.cmd_chart_line));

    bottomNavigation.addItem(ahbniHistory);
    bottomNavigation.addItem(ahbniSearch);
    bottomNavigation.addItem(ahbniStatistics);

    bottomNavigation.setBehaviorTranslationEnabled(true);

    bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

    bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
      @Override
      public boolean onTabSelected(int position, boolean wasSelected) {
        Log.d(TAG, "wasSelected = " + String.valueOf(wasSelected));
        switch (position) {
          case Consts.FRAGMENT_HISTORY:
            if (wasSelected && historyFragment != null) {
              historyFragment.scrollToTop();
            } else {
              if (historyFragment == null)
                historyFragment = HistoryFragment.newInstance();
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, historyFragment).addToBackStack(null).commit();
              getSupportActionBar().setTitle(R.string.history);
              floatingActionButton.setVisibility(View.VISIBLE);
              floatingActionButton.setImageDrawable(new IconicsDrawable(MainActivity.this).colorRes(R.color.colorPrimary).icon(CommunityMaterial.Icon.cmd_plus));
              floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  openAddRecordActivity();
                }
              });
            }
            break;
          case Consts.FRAGMENT_SEARCH:
            if (wasSelected) {
              //TODO call fragment to smooth scroll the RV to top
            } else {
              if (searchFragment == null)
                searchFragment = SearchFragment.newInstance();
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).addToBackStack(null).commit();
              getSupportActionBar().setTitle(R.string.search);
              floatingActionButton.setVisibility(View.VISIBLE);
              floatingActionButton.setImageDrawable(new IconicsDrawable(MainActivity.this).colorRes(R.color.colorPrimary).icon(CommunityMaterial.Icon.cmd_magnify));
              floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Log.d(TAG, "fab at search");
                  if (searchFragment != null) {
                    SearchParams searchParams = searchFragment.getSearchParams();
                    openSearchResultActivity(searchParams);
                  }
                }
              });
            }
            break;
          case Consts.FRAGMENT_STATISTIC:
            if (wasSelected) {
              //TODO call fragment to smooth scroll the RV to top
            } else {
              if (statisticFragment == null)
                statisticFragment = StatisticFragment.newInstance();
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, statisticFragment).addToBackStack(null).commit();
              getSupportActionBar().setTitle(R.string.statistics);
              floatingActionButton.setVisibility(View.VISIBLE);
              floatingActionButton.setImageDrawable(new IconicsDrawable(MainActivity.this).colorRes(R.color.colorPrimary).icon(CommunityMaterial.Icon.cmd_filter));
              floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Log.d(TAG, "fab at statistics");
                  //TODO
                  Toast.makeText(MainActivity.this, R.string.coming_soon, Toast.LENGTH_LONG).show();
                }
              });
            }
            break;
        }
        return true;  //return false if anything goes wrong (stay in this tab)
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        Toast.makeText(MainActivity.this, R.string.coming_soon, Toast.LENGTH_SHORT).show();
        new MaterialDialog.Builder(MainActivity.this).onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if (hasWriteExternalStoragePermission()) {
              RealmController.with(MainActivity.this).exportDatabase();
              Toast.makeText(MainActivity.this, "check download 2000", Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(MainActivity.this, "please grand me permission and try again", Toast.LENGTH_SHORT).show();
              writeToStoragePermissionHandling();
            }
          }
        }).positiveText(R.string.backup).show();
        break;
      case R.id.action_about:
        openAboutActivity();
        break;
      case R.id.action_changelog:
        cl.getLogDialog().show();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void openSettings() {
    //TODO
  }

  private void openAboutActivity() {
    new LibsBuilder()
        .withVersionShown(true)
        .withAboutIconShown(true)
        .withAboutVersionShown(true)
        .withAboutVersionShownName(true)
        .withAboutVersionShownCode(true)
        .withActivityTitle(getString(R.string.about))
        .withAboutAppName(getString(R.string.app_name))
        .withAboutDescription(getString(R.string.about_description))
        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
        .start(this);
  }

  private void openAddRecordActivity() {
    Intent intent = new Intent(this, AddRecordActivity.class);
    startActivity(intent);
  }

  private void openSearchResultActivity(SearchParams searchParams) {
    //TODO
    Toast.makeText(MainActivity.this, R.string.coming_soon, Toast.LENGTH_LONG).show();
  }

  private boolean hasWriteExternalStoragePermission() {
    return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
  }

  private void writeToStoragePermissionHandling() {
    if (!hasWriteExternalStoragePermission()) {
      ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Log.d(TAG, "permission granted");

      }
    }
  }

  @Override
  public void hideFAB() {
    if (floatingActionButton != null)
      floatingActionButton.setVisibility(View.GONE);
  }
}
