package com.insertjokes.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Ben on 2014-05-26.
 */
public class Crimelab {
    private ArrayList<Crime> mCrimes;
    private static Crimelab sCrimeLab;
    private Context mAppContext;

    private Crimelab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
        for (int i=0; i<100; i++){
           Crime c = new Crime();
            c.setTitle("Crime #"+i);
            c.setSolved(i%2 ==0);
            mCrimes.add(c);
        }
    }

    public static Crimelab get(Context c) {
        if (sCrimeLab == null)
        {
            sCrimeLab = new Crimelab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id)
    {
        for (Crime c:mCrimes)
        {
            if (c.getId().equals(id))
            {
                return c;
            }
        }
        return null;
    }
}
