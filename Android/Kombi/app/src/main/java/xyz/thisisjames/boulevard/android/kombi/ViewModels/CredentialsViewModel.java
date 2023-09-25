package xyz.thisisjames.boulevard.android.kombi.ViewModels;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CredentialsViewModel  extends ViewModel{


    public @Inject CredentialsViewModel(){
        super();
    }

    public FirebaseAuth mAuth;

    private Boolean isPasswordVisible = false ;

    private MutableLiveData<Boolean> signInStatus = new MutableLiveData<>();

    private MutableLiveData<Boolean> _isPasswordVisible = new MutableLiveData<>();


    public MutableLiveData<Boolean> getPasswordVisibilityStatus() {
        return _isPasswordVisible  ;
    }

    public void setPasswordVisibilityStatus(){
        isPasswordVisible = ! isPasswordVisible;
        _isPasswordVisible.setValue(isPasswordVisible);
    }

    public void signUpUsers(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    signInStatus.setValue(true);
                }else{
                    signInStatus.setValue(false);
                }
            }

        });
    }

    public void loginUser(String[] args){
        mAuth.signInWithEmailAndPassword(args[0], args[1]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });


    }


}
