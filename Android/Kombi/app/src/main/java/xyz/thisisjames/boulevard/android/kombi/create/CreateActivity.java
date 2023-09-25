package xyz.thisisjames.boulevard.android.kombi.create;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import xyz.thisisjames.boulevard.android.kombi.Dependencies.Model;
import xyz.thisisjames.boulevard.android.kombi.databinding.ActivityCreateBinding;

import xyz.thisisjames.boulevard.android.kombi.R;

public class CreateActivity extends AppCompatActivity {

    private Model model ;

    private ActivityCreateBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        setContentView(binding.getRoot());

        View decorview = getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        model = new Model();
        setStaticValues();

        binding.inputTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    model.setTaskName("");
                }else{
                    model.setTaskName(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.inputTaskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    model.setTaskDescription("");
                }else{
                    model.setTaskDescription(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.createTagRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.tag_upcoming){
                    model.setTaskType("Upcoming");
                }else if (id == R.id.tag_important){
                    model.setTaskType("Important");
                }else{
                    model.setTaskType("Priority");
                }
            }
        });

        binding.taskDateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker dp = MaterialDatePicker.Builder.datePicker().setTitleText("Task Due Date").build();
                dp.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        Calendar calendat = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendat.setTimeInMillis(selection);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.US);
                        String formattedDate = sdf.format(calendat.getTime());
                        binding.taskDate.setText(formattedDate);
                        model.setTaskDate(selection);
                        model.setTaskDateString(formattedDate);
                    }
                });
                dp.show(getSupportFragmentManager(),"Select Date");
            }
        });

        binding.taskStarttimeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker tp = new MaterialTimePicker.Builder().setTitleText("Select Start Time")
                        .setTimeFormat(TimeFormat.CLOCK_24H).build();

                tp.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendat = Calendar.getInstance();
                        calendat.set(Calendar.HOUR_OF_DAY, tp.getHour());
                        calendat.set(Calendar.MINUTE, tp.getMinute());
                        calendat.setLenient(false);

                        String format = new SimpleDateFormat("HH:mm").format(calendat.getTime());
                        binding.taskStarttime.setText(format);

                        model.setTaskStartTime(calendat.getTimeInMillis());
                    }
                });

                tp.show(getSupportFragmentManager(),"Start Time");
            }
        });

        binding.taskStoptimeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker tp = new MaterialTimePicker.Builder().setTitleText("Select Start Time")
                        .setTimeFormat(TimeFormat.CLOCK_24H).build();

                tp.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendat = Calendar.getInstance();
                        calendat.set(Calendar.HOUR_OF_DAY, tp.getHour());
                        calendat.set(Calendar.MINUTE, tp.getMinute());
                        calendat.setLenient(false);

                        String format = new SimpleDateFormat("HH:mm").format(calendat.getTime());
                        binding.taskStoptime.setText(format);

                        model.setTaskEndTime(calendat.getTimeInMillis());

                    }
                });

                tp.show(getSupportFragmentManager(),"Start Time");
            }
        });

        binding.checkboxRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.createRepeatRadio.setVisibility(View.VISIBLE);
                    model.setTaskRepeatType("Don't Repeat");
                }else{
                    binding.createRepeatRadio.setVisibility(View.GONE);
                    setRepeatFrequency();
                }
            }
        });

        binding.createRepeatRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setRepeatFrequency();
            }
        });


        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                model.setTaskDescription(binding.inputTaskDescription.getText().toString().trim());
                model.setTaskName(binding.inputTaskName.getText().toString().trim());

                if (invalidateData()){
                    binding.createinputError.setVisibility(View.VISIBLE);
                    binding.createinputError.setText(model.getTaskDescription());
                }else{
                    uploadData();
                }
            }
        });

    }

    private void setRepeatFrequency() {
        int id = binding.createRepeatRadio.getCheckedRadioButtonId();

        if (id == R.id.repeat_monthly){
            model.setTaskRepeatType("Monthly");
        }else if (id == R.id.repeat_weekly){
            model.setTaskRepeatType("Weekly");
        }else{
            model.setTaskRepeatType("Daily");
        }
    }


    private boolean invalidateData(){
        if (model == null)
            return true;

        if (model.getTaskName().isEmpty()){
            return true;
        }else if (model.getTaskDescription().isEmpty()){
            return true;
        }else if (model.getTaskType().isEmpty()){
            return true;
        }else if (model.getTaskRepeatType().isEmpty()) {
            return true;
        } else if (model.getTaskDateString().isEmpty()) {
            return true;
        } else if (model.getTaskStatus().isEmpty()){
            return true;
        } else if (model.getTaskStartTime() == null) {
            return true;
        } else if (model.getTaskEndTime() == null) {
            return true;
        } else if (model.getTaskDate() == null){
            return true;
        } else if (model.getTaskRescheduled() == null){
            return true;
        }else if (model.getTaskIsHabit() == null){
            return true;
        }else if (model.getTaskDeleted() == null){
            return true;
        }

        return false;
    }


    private void setStaticValues(){
        model.setTaskRescheduled(false);
        model.setTaskIsHabit(false);
        model.setTaskDeleted(false);

        model.setTaskRepeatType("Don't Repeat");

        model.setTaskType("Upcoming");

        model.setTaskDescription("@n/a");
    }


    private void uploadData(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Core").child(uid).child("Task").push();
        database.child("Core").child(uid).child("Task").setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    binding.createinputError.setVisibility(View.INVISIBLE);
                    binding.createinputError.setText("Unable to upload task. Try again !");
                }else {
                    try {
                        onBackPressed();
                    }catch (Exception e){
                        
                    }
                }
            }
        });

    }

}