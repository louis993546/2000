package io.github.louistsaitszho.erg2000.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.fragment.HistoryFragment;

public class Main2Activity extends AppCompatActivity {

  @BindView(R.id.toolbar)   Toolbar toolbar;
  @BindView(R.id.tabLayout) TabLayout tabLayout;
  @BindView(R.id.fab)       FloatingActionButton fab;
  @BindView(R.id.viewPager) ViewPager viewPager;
  public final static String[] PAGES = new String[]{"History", "Progress", "Statistics"};
  private static final int REQUEST_CODE = 2152;       //Just a random number

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme_NoActionBar);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    ButterKnife.bind(Main2Activity.this);
    inflateEverything();
  }

  /**
   *
   */
  private void inflateEverything() {
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("History");
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setIcon(R.drawable.actionbar_icon);
    tabLayout.setupWithViewPager(viewPager);
    /**
     * TODO not working (not visible)
     * TODO change icon
     */
    tabLayout.addTab(tabLayout.newTab().setText(PAGES[0]));
    tabLayout.addTab(tabLayout.newTab().setText(PAGES[1]));
    tabLayout.addTab(tabLayout.newTab().setText(PAGES[2]));
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
      /**
       * TODO return redesign fragments
       * TODO listeners to scroll each one back to top
       * @param position of the page the system is requesting
       * @return the fragment
       */
      @Override
      public Fragment getItem(int position) {
        return new HistoryFragment();
      }

      @Override
      public int getCount() {
        return PAGES.length;
      }
    });
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));      //viewPager listens to tabLayout changes     //tabLayout listens to viewPager changes
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
          case 0:
            getSupportActionBar().setTitle("History");
            //TODO change fab onclick
            break;
          case 1:
            getSupportActionBar().setTitle("Progress");
            //TODO change fab onclick
            break;
          case 2:
            getSupportActionBar().setTitle("Statistics");
            //TODO change fab onclick
            break;
          default:
            getSupportActionBar().setTitle("WTF");
        }
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
        //TODO scroll to top listeners
      }
    });

    //TODO download a better "+" icon to drawable
    fab.setImageDrawable(new IconicsDrawable(Main2Activity.this).icon(CommunityMaterial.Icon.cmd_plus).color(Color.WHITE).actionBar());
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openAddRecordActivity();
      }
    });
  }

  /**
   * TODO serach icon too large (ugly)
   * @param menu
   * @return
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main_2, menu);
    menu.getItem(0).setIcon(new IconicsDrawable(Main2Activity.this).icon(CommunityMaterial.Icon.cmd_magnify).color(Color.WHITE).actionBar());
    return super.onCreateOptionsMenu(menu);
  }

  /**
   * TODO a lot of things to be fixed
   * @param item
   * @return
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        //TODO new activity?
        break;
      case R.id.action_settings:
        //TODO
        break;
      case R.id.action_about:
        openAboutActivity();
        break;
      case R.id.action_changelog:
        //TODO drop ckChangeLog to something more visually pleasing
        break;
      case R.id.action_play_store:
        openPlayStore();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Open new activity for adding record
   */
  private void openAddRecordActivity() {
    Intent intent = new Intent(this, AddRecordActivity.class);
    startActivity(intent);
  }

  /**
   * Display AboutLibraries
   */
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

  /**
   * Open Play Store and ask for review
   * if Play Store not found, open webpage on browser
   */
  private void openPlayStore() {
    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    } catch (android.content.ActivityNotFoundException anfe) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
    }
  }

  private boolean hasWriteExternalStoragePermission() {
    return ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
  }

  private void writeToStoragePermissionHandling() {
    if (!hasWriteExternalStoragePermission()) {
      ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }
  }
}
