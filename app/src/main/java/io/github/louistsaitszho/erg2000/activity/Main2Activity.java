package io.github.louistsaitszho.erg2000.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;

public class Main2Activity extends AppCompatActivity {

  @BindView(R.id.stl) SmartTabLayout smartTabLayout;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.viewPager) ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    ButterKnife.bind(Main2Activity.this);


//    viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
//      @Override
//      public Fragment getItem(int position) {
//        return null;
//      }
//
//      @Override
//      public int getCount() {
//        return 3;
//      }
//    });

//    smartTabLayout.setViewPager(viewPager);

  }
}
