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
import xyz.thisisjames.boulevard.android.kombi.databinding.FragmentCredentialChoiceBinding;


public class CredentialChoiceFragment extends Fragment {


    private FragmentCredentialChoiceBinding binding;

    public CredentialChoiceFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCredentialChoiceBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CredentialChoiceFragment.this).navigate(
                        R.id.action_credentialChoiceFragment_to_credentialsInputFragment
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