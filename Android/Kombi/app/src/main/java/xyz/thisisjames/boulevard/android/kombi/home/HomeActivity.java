package xyz.thisisjames.boulevard.android.kombi.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import xyz.thisisjames.boulevard.android.kombi.create.CreateActivity;
import xyz.thisisjames.boulevard.android.kombi.databinding.ActivityHomeBinding;

import xyz.thisisjames.boulevard.android.kombi.R;
import xyz.thisisjames.boulevard.android.kombi.home.ui.SectionsPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;

    private AppCompatImageButton currentSection;

    private ViewPager viewPager;

    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        setContentView(binding.getRoot());

        View decorview = getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        currentSection = binding.navHome;

        viewPager = binding.getRoot().findViewById(R.id.view_pager);
        binding.navSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationManager(view.getId());
            }
        });
        binding.navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationManager(view.getId());
            }
        });
        binding.navHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationManager(view.getId());
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreateActivity.class));
            }
        });

    }





    private void navigationManager(int itemId){
        if (itemId == R.id.nav_home) {
            binding.navHome.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_dark40));
            currentSection.setBackgroundDrawable(null);
            currentSection = binding.navHome;
        }else if (itemId == R.id.nav_habits){
            binding.navHabits.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_dark40));
            currentSection.setBackgroundDrawable(null);
            currentSection = binding.navHabits;
        }else{
            binding.navSettings.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_dark40));
            currentSection.setBackgroundDrawable(null);
            currentSection = binding.navSettings;
        }
    }


    private void manageViewPager(int pages, Fragment fragment){
        sectionsPagerAdapter = new SectionsPagerAdapter(this,pages,fragment,getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

    }
}