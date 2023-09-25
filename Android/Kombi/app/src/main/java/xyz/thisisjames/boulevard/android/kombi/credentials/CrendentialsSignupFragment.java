package xyz.thisisjames.boulevard.android.kombi.credentials;

import static xyz.thisisjames.boulevard.android.kombi.Dependencies.Shares.setEmailPreferences;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ak.ui.CountryCodePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.kombi.R;
import xyz.thisisjames.boulevard.android.kombi.ViewModels.CredentialsViewModel;
import xyz.thisisjames.boulevard.android.kombi.databinding.FragmentCrendentialsSignupBinding;


@AndroidEntryPoint
public class CrendentialsSignupFragment extends Fragment {

    @Inject
    public CredentialsViewModel cvm;
    private FragmentCrendentialsSignupBinding  binding;

    private String[] content = new String[3];
    private FirebaseAuth mAuth;


    public CrendentialsSignupFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCrendentialsSignupBinding.inflate(getLayoutInflater());

        mAuth = FirebaseAuth.getInstance();

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        binding.buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }



    private void createUser(){

        manageViews(true);

        mAuth.createUserWithEmailAndPassword(content[0],content[1])
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sendVerificationCode(content[0]);
                    }else {
                        manageViews(false);
                    }
                }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        binding.inputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                content[2] = charSequence.toString();
                toggleSubmitButton();
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
                content[0] = charSequence.toString();
                toggleSubmitButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                content[1] = charSequence.toString();
                toggleSubmitButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void manageViews(boolean bool){
        if (bool){
            binding.buttonVerify.setVisibility(View.GONE);
            binding.creatingAccount.setVisibility(View.VISIBLE);
        }else{
            binding.buttonVerify.setVisibility(View.VISIBLE);
            binding.creatingAccount.setVisibility(View.GONE);
        }
    }

    private void toggleSubmitButton(){
        try{
            if (verifyInput()){
                binding.buttonVerify.setVisibility(View.VISIBLE);
            }else {
                binding.buttonVerify.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }
    }

    private boolean verifyInput(){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(content[1].length() < 6 || content[2].isEmpty()){
            return false;
        }

        return Pattern.compile(regexPattern)
                .matcher(content[0])
                .matches();
    }

    private void sendVerificationCode(String emailString){
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "xyz.thisisjames.kombi.android",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();

        FirebaseAuth.getInstance().sendSignInLinkToEmail(emailString,actionCodeSettings).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){

                            setEmailPreferences(getActivity(),emailString);

                            NavHostFragment.findNavController(CrendentialsSignupFragment.this).
                                    navigate(R.id.action_credentialsInputFragment_to_credentialsVerifyFragment);
                        }
                    }
                }
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null ;
    }
}