package xyz.thisisjames.boulevard.android.kombi.credentials;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.thisisjames.boulevard.android.kombi.R;
import xyz.thisisjames.boulevard.android.kombi.databinding.FragmentCredentialsInputBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CredentialsInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CredentialsInputFragment extends Fragment {

    private FragmentCredentialsInputBinding binding;

    public CredentialsInputFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentCredentialsInputBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CredentialsInputFragment.this).navigate(
                        R.id.action_credentialsInputFragment_to_credentialsVerifyFragment
                );
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}