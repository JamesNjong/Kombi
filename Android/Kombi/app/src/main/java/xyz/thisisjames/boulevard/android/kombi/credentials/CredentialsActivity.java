package xyz.thisisjames.boulevard.android.kombi.credentials;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.kombi.ViewModels.CredentialsViewModel;
import xyz.thisisjames.boulevard.android.kombi.databinding.ActivityCredentialsBinding;

import xyz.thisisjames.boulevard.android.kombi.R;


@AndroidEntryPoint
public class CredentialsActivity extends AppCompatActivity {


    private CredentialsViewModel cvm;

    private AppBarConfiguration appBarConfiguration;
    private ActivityCredentialsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCredentialsBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        setContentView(binding.getRoot());

        View decorview = getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        cvm = new ViewModelProvider(this).get( CredentialsViewModel.class);

        cvm.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_credentials);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}