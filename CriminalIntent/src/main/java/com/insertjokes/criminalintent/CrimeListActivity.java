package com.insertjokes.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Ben on 2014-05-26.
 */
public class CrimeListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
       return new CrimeListFragment();
    }
}
