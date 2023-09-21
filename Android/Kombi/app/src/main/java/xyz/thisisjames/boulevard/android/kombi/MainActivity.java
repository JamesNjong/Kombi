package xyz.thisisjames.boulevard.android.kombi;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import xyz.thisisjames.boulevard.android.kombi.databinding.ActivityMainBinding;
import xyz.thisisjames.boulevard.android.kombi.onboarding.OnboardingActivity;

public class MainActivity extends AppCompatActivity {


    private static CircularProgressIndicator circularProgress  ;

    private static void setProgress () {
        progressCount =3000;

        circularProgress.setVisibility(View.VISIBLE);
        circularProgress.setCurrentProgress(progressCount);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (progressCount == 3000){
                Intent intent = new Intent(getBaseContext(), OnboardingActivity.class);
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                setProgress();
                mHandler.postDelayed(this,2000);
            }
        }
    };

    private Handler mHandler  = new Handler() ;

    private static int progressCount = 30 ;

    private ActivityMainBinding _binding   = null;
    private ActivityMainBinding binding  = _binding ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View decorview = getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        circularProgress = binding.circleCenter;
        circularProgress.setMaxProgress(3000);
        circularProgress.setVisibility(View.GONE);

        mHandler.postDelayed(mRunnable,3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacks(mRunnable);
    }
}