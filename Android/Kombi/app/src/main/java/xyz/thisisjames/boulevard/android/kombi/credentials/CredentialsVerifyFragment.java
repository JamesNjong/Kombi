package xyz.thisisjames.boulevard.android.kombi.credentials;

import static java.lang.Thread.sleep;

import static xyz.thisisjames.boulevard.android.kombi.Dependencies.Shares.getEmailShares;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.kombi.R;
import xyz.thisisjames.boulevard.android.kombi.ViewModels.CredentialsViewModel;
import xyz.thisisjames.boulevard.android.kombi.databinding.FragmentCredentialsVerifyBinding;
import xyz.thisisjames.boulevard.android.kombi.home.HomeActivity;

@AndroidEntryPoint
public class CredentialsVerifyFragment extends Fragment {

    private FragmentCredentialsVerifyBinding binding;

    private String emailString ="" ;

    @Inject
    public CredentialsViewModel cvm;

    public CredentialsVerifyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        emailString = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCredentialsVerifyBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textPrompt.setText(String.format("We have sent a verification link to %s. Please click on it to proceed.",emailString));


        binding.buttonResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.buttonResend.setVisibility(View.GONE);
                binding.resending.setVisibility(View.VISIBLE);
                sendVerificationCode();
            }
        });

        binding.buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    private void sendVerificationCode(){
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
                            binding.buttonResend.setVisibility(View.VISIBLE);
                            binding.resending.setVisibility(View.GONE);
                        }
                    }
                }
        );

    }
}