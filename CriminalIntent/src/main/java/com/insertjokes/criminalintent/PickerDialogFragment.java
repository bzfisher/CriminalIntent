package com.insertjokes.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ben on 2014-05-28.
 */
public class PickerDialogFragment extends DialogFragment
{
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    public static final String EXTRA_DATE_TIME = "com.insertjokes.criminalintent.timeAndDate";
    private Date mDate;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE_TIME);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_picker_title).setItems(R.array.pickerDialogArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //Picked date
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            DatePickerFragment dialog = DatePickerFragment.newInstance(mDate);
                            dialog.setTargetFragment(PickerDialogFragment.this, REQUEST_DATE);
                            dialog.show(fm, CrimeFragment.DIALOG_DATE);
                        } else {
                            //picked time
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            TimePickerFragment dialog = TimePickerFragment.newInstance(mDate);
                            dialog.setTargetFragment(PickerDialogFragment.this, REQUEST_TIME);
                            dialog.show(fm, CrimeFragment.DIALOG_DATE);
                        }
                    }
                }).create();
    }

    public static PickerDialogFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE_TIME, date);

        PickerDialogFragment fragment = new PickerDialogFragment();
        fragment.setArguments(args);
        Log.d(PickerDialogFragment.class.toString(), "set Arguments!");
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
            this.setTargetFragment(getTargetFragment(), REQUEST_DATE);

            Date newDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Intent i = new Intent();
            i.putExtra(DatePickerFragment.EXTRA_DATE, newDate);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }

        if (requestCode == REQUEST_TIME)
        {
            this.setTargetFragment(getTargetFragment(), REQUEST_TIME);

            Date newDate = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            Intent i = new Intent();
            i.putExtra(TimePickerFragment.EXTRA_TIME, newDate);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }
    }
}
