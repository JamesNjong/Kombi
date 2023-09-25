package xyz.thisisjames.boulevard.android.kombi.credentials;

import static androidx.core.content.ContextCompat.getSystemService;

import static xyz.thisisjames.boulevard.android.kombi.Dependencies.Shares.setEmailPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.kombi.R;
import xyz.thisisjames.boulevard.android.kombi.ViewModels.CredentialsViewModel;
import xyz.thisisjames.boulevard.android.kombi.databinding.FragmentCredentialsLoginBinding;
import xyz.thisisjames.boulevard.android.kombi.home.HomeActivity;

@AndroidEntryPoint
public class CredentialsLoginFragment extends Fragment {

    private FragmentCredentialsLoginBinding binding;

    @Inject public CredentialsViewModel creds;

    public CredentialsLoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentCredentialsLoginBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] content  = getText();
                Activity activity = getActivity();

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if (verifyContent(content)){
                    updateViews(true);
                    try{
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(content[0], content[1]).addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            moveOn(content[0]);
                                        }else{
                                            updateViews(false);
                                            binding.invalidCreds.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                        );
                    }catch (Exception e){

                    }
                }

            }
        });

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CredentialsLoginFragment.this).navigate(
                        R.id.action_credentialsLoginFragment_to_credentialsSignupFragment
                );
            }
        });

        binding.buttonRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CredentialsLoginFragment.this).navigate(
                        R.id.action_credentialsLoginFragment_to_credentialRecoverFragment
                );
            }
        });

        binding.passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               creds.setPasswordVisibilityStatus();
            }
        });

        // Create the observer which updates the UI.
        final Observer<Boolean> passwordObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean isPasswordVisible) {
                if (isPasswordVisible) {
                    // Show the password
                    binding.inputPassword.setTransformationMethod(null);
                    binding.passwordButton.setImageResource(R.drawable.pdshow_luna);
                } else {
                    // Hide the password
                    binding.inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.passwordButton.setImageResource(R.drawable.pdhide_luna); // icon names are interchanged
                }
            }
        };
        creds.getPasswordVisibilityStatus().observe(getViewLifecycleOwner(),passwordObserver);
    }

    private void moveOn(String email) {
        setEmailPreferences(getActivity(),email);
        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            Intent intent = new Intent(getContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getContext(), VerifyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private String[] getText(){
        String[] content  = {
                binding.inputEmail.getText().toString().trim(),
                binding.inputPassword.getText().toString().trim()
        };
        return content;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                toggleSubmitButton();
                binding.invalidCreds.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                toggleSubmitButton();
                binding.invalidCreds.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private Boolean verifyContent(String[] content){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(content[1].length() < 6){
            return false;
        }

        return Pattern.compile(regexPattern)
                .matcher(content[0])
                .matches();
    }

    private void updateViews(boolean blean){
        if (blean){
            binding.buttonRecover.setEnabled(false);
            binding.buttonSignup.setEnabled(false);
            binding.buttonVerify.setVisibility(View.GONE);
            binding.signingup.setVisibility(View.VISIBLE);
        }else{
            binding.buttonRecover.setEnabled(true);
            binding.buttonSignup.setEnabled(true);
            binding.buttonVerify.setVisibility(View.VISIBLE);
            binding.signingup.setVisibility(View.GONE);
        }
    }


    private void toggleSubmitButton(){
        if (verifyContent(getText())){
            binding.buttonVerify.setVisibility(View.VISIBLE);
        }else {
            binding.buttonVerify.setVisibility(View.GONE);
        }

    }



}