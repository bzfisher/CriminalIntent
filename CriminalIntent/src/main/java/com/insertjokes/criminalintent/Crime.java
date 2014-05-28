package com.insertjokes.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Ben on 2014-05-26.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime()
    {
        //generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    @Override
    public String toString()
    {
        return mTitle;
    }
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean Solved) {
        this.mSolved = Solved;
    }
}
