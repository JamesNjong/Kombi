package xyz.thisisjames.boulevard.android.kombi.onboarding.ui.main;

import static androidx.lifecycle.Transformations.map;

import android.graphics.drawable.Drawable;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import xyz.thisisjames.boulevard.android.kombi.R;

public class PageViewModel extends ViewModel {

    private MutableLiveData<String> _mLabel = new MutableLiveData<>();
    private LiveData<String> mLabel = map(_mLabel, input ->  input);

    private MutableLiveData<String> _mBrief = new MutableLiveData<>();

    private LiveData<String> mBrief = map(_mBrief, input -> input);

    private MutableLiveData<Integer> _mDrawable = new MutableLiveData<>();
    private LiveData<Integer> mDrawable = map(_mDrawable, input -> input);


    public void setIndex(int index) {
        if(index == 1) {
            _mLabel.setValue("Organize your life." );
            _mBrief.setValue("Let's help you organize your life and prioritize what matters, so you can leave stress free.");
            _mDrawable.setValue(R.drawable.luna_splash1);
        }else {
            _mDrawable.setValue(R.drawable.luna_splash2);
            _mLabel.setValue("Reach new heights." );
            _mBrief.setValue("We've been helping millions of people around the world unlock their potential by building healthy habits.");
        }

    }



    public LiveData<String> getLabel() {
         return mLabel;
    }

    public LiveData<String> getBrief(){
        return mBrief;
    }

    public LiveData<Integer> getImage(){
        return mDrawable;
    }
}