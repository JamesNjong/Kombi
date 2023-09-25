package xyz.thisisjames.boulevard.android.kombi.credentials;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.kombi.R;
import xyz.thisisjames.boulevard.android.kombi.databinding.FragmentCredentialRecoverBinding;
import xyz.thisisjames.boulevard.android.kombi.home.HomeActivity;

@AndroidEntryPoint
public class CredentialRecoverFragment extends Fragment {


    private String destination =  null ;

    private FragmentCredentialRecoverBinding binding;

    public CredentialRecoverFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCredentialRecoverBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}