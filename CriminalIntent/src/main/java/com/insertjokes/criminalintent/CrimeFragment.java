package com.insertjokes.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ben on 2014-05-26.
 */
public class CrimeFragment extends Fragment {
    public static final String EXTRA_CRIME_ID = "com.insertjokes.criminalintent.criminalintent.crime_id";
    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        UUID crimeID = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = Crimelab.get(getActivity()).getCrime(crimeID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_crime, parent, false);
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence c, int start, int count, int after) {
                mCrime.setTitle(c.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                //space left intentionally blank
            }

            @Override
            public void afterTextChanged(Editable c) {
                //this one too
            }
        });
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.crime_time);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragmentManager fm = getActivity().getSupportFragmentManager();
               PickerDialogFragment dialog = PickerDialogFragment.newInstance(mCrime.getDate());
               dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
               dialog.show(fm,DIALOG_TIME);
           }
       });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }

    public static CrimeFragment newInstance(UUID crimeID)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeID);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if (requestCode == REQUEST_DATE)
        {
            Log.d(CrimeFragment.class.toString(), "got a new date!");
            Date newDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Date previousDate = mCrime.getDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(newDate);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            calendar.setTime(previousDate);

            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);

            mCrime.setDate(calendar.getTime());
            updateDate();
        }
        if (requestCode == REQUEST_TIME)
        {
            Date newDate = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            Date previousDate = mCrime.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(previousDate);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            calendar.setTime(newDate);

            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);

            mCrime.setDate(calendar.getTime());
            updateDate();
        }
    }

    private void updateDate()
    {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
