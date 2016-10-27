package io.github.louistsaitszho.erg2000.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
  public final static int NUMBER_OF_PAGES = 3;        //History + Progress + Statistics

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
    /**
     * TODO not sure if this works
     * TODO change icon
     */
    tabLayout.addTab(tabLayout.newTab().setIcon(new IconicsDrawable(Main2Activity.this).icon(CommunityMaterial.Icon.cmd_history).color(Color.WHITE).actionBar()), 0);
    tabLayout.addTab(tabLayout.newTab().setIcon(new IconicsDrawable(Main2Activity.this).icon(CommunityMaterial.Icon.cmd_chart_line).color(Color.WHITE).actionBar()), 1);
    tabLayout.addTab(tabLayout.newTab().setIcon(new IconicsDrawable(Main2Activity.this).icon(CommunityMaterial.Icon.cmd_view_list).color(Color.WHITE).actionBar()), 2);
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
        return NUMBER_OF_PAGES;
      }
    });
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));      //viewPager listens to tabLayout changes
    tabLayout.setupWithViewPager(viewPager);                                                        //tabLayout listens to viewPager changes
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
          case 0:
            getSupportActionBar().setTitle("History");
            break;
          case 1:
            getSupportActionBar().setTitle("Progress");
            break;
          case 2:
            getSupportActionBar().setTitle("Statistics");
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

  private void openAddRecordActivity() {
    Intent intent = new Intent(this, AddRecordActivity.class);
    startActivity(intent);
  }
}
