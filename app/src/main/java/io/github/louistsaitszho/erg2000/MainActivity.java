package io.github.louistsaitszho.erg2000;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.fragment.HistoryFragment;
import io.github.louistsaitszho.erg2000.fragment.SearchFragment;
import io.github.louistsaitszho.erg2000.fragment.StatisticFragment;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = MainActivity.class.getSimpleName();

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
  @BindView(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;
  @BindView(R.id.fragment_container) FrameLayout fragmentContainer;
  @BindView(R.id.fab) FloatingActionButton floatingActionButton;

  HistoryFragment historyFragment;
  SearchFragment searchFragment;
  StatisticFragment statisticFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme_NoActionBar);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

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
    AHBottomNavigationItem ahbniHistory = new AHBottomNavigationItem("History", new IconicsDrawable(this).sizeDp(22).icon(CommunityMaterial.Icon.cmd_history));
    AHBottomNavigationItem ahbniSearch = new AHBottomNavigationItem("Search", new IconicsDrawable(this).sizeDp(22).icon(CommunityMaterial.Icon.cmd_magnify));
    AHBottomNavigationItem ahbniStatistics = new AHBottomNavigationItem("Statistics", new IconicsDrawable(this).sizeDp(22).icon(CommunityMaterial.Icon.cmd_chart_line));

    bottomNavigation.addItem(ahbniHistory);
    bottomNavigation.addItem(ahbniSearch);
    bottomNavigation.addItem(ahbniStatistics);

    bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

    bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
      @Override
      public boolean onTabSelected(int position, boolean wasSelected) {
        Log.d(TAG, "wasSelected = " + String.valueOf(wasSelected));
        //TODO show/hide FAB
        switch (position) {
          case Consts.FRAGMENT_HISTORY:
            if (wasSelected) {
              //TODO call fragment to smooth scroll the RV to top
            } else {
              if (historyFragment == null)
                historyFragment = HistoryFragment.newInstance();
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, historyFragment).addToBackStack(null).commit();
              floatingActionButton.setImageDrawable(new IconicsDrawable(MainActivity.this).colorRes(R.color.colorPrimary).icon(CommunityMaterial.Icon.cmd_plus));
            }
            break;
          case Consts.FRAGMENT_SEARCH:
            if (wasSelected) {
              //TODO call fragment to smooth scroll the RV to top
            } else {
              if (searchFragment == null)
                searchFragment = SearchFragment.newInstance();
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).addToBackStack(null).commit();
//              floatingActionButton.setImageDrawable(new IconicsDrawable(MainActivity.this).colorRes(R.color.colorPrimary).icon(CommunityMaterial.Icon.cmd_plus));
            }
            break;
          case Consts.FRAGMENT_STATISTIC:
            if (wasSelected) {
              //TODO call fragment to smooth scroll the RV to top
            } else {
              if (statisticFragment == null)
                statisticFragment = StatisticFragment.newInstance();
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, statisticFragment).addToBackStack(null).commit();
              floatingActionButton.setImageDrawable(new IconicsDrawable(MainActivity.this).colorRes(R.color.colorPrimary).icon(CommunityMaterial.Icon.cmd_filter));
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
        break;
      case R.id.action_about:
        openAboutActivity();
        break;
      case R.id.action_changelog:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void openAboutActivity() {
    new LibsBuilder()
        .withVersionShown(true)
        .withAboutIconShown(true)
        .withAboutVersionShown(true)
        .withAboutVersionShownName(true)
        .withAboutVersionShownCode(true)
        .withActivityTitle("About")
        .withAboutAppName("2000")
        .withAboutDescription("Keep track of your indoor rowing record")
        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
        .start(this);
  }

  private void openAddRecordActivity() {
    Intent intent = new Intent(this, AddRecordActivity.class);
    startActivity(intent);
  }

}
