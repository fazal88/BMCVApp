package com.androidvoyage.bmc.common;


import com.androidvoyage.bmc.models.DesiredCareer;
import com.androidvoyage.bmc.models.Employment;
import com.androidvoyage.bmc.models.Language;
import com.androidvoyage.bmc.models.OtherDetails;
import com.androidvoyage.bmc.models.Projects;
import com.androidvoyage.bmc.models.Qualification;
import com.androidvoyage.bmc.utils.MiscUtils;

import java.util.ArrayList;

/**
 * Created by Razrcorp on 8/9/2017.
 */

public class SingletonUserInfo {

    private static SingletonUserInfo singletonUserInfo = null;

    public int profileCompletion;
    public String profilePicUrl;
    public String fullName;
    public String designation;
    public String companyName;
    public String experience;
    public String city;
    public String country;
    public String ctc;
    public String currency;
    public String email;

    public String phone;
    public String altPhone;
    public String lastSeen;
    public String headline;// 250 limit
    public String summary;// 1000 limit

    public OtherDetails otherDetails;
    public ArrayList<Language> listKnownLanguages;
    public ArrayList<String> listSkills;
    public ArrayList<Employment> listEmployment;
    public ArrayList<Projects> listProjects;
    public ArrayList<Qualification> listQualifications;
    public ArrayList<String> listCertifications;
    public DesiredCareer desiredCareer;
    public boolean isExperienced;

    public static SingletonUserInfo getInstance() {
        if (singletonUserInfo == null)
            singletonUserInfo = new SingletonUserInfo();
        return singletonUserInfo;
    }

    public SingletonUserInfo getUserDetails() {
        if (this.singletonUserInfo != null) {
            return singletonUserInfo;
        }
        String userString = BCSharedPreference.getInstance()
                .getSharedPreferences()
                .getString(BCSharedPreference.PREF_USER_DETAILS, null);
        if (userString != null) {
            try {
                singletonUserInfo = MiscUtils.getGsonInstance().fromJson(userString, SingletonUserInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return new SingletonUserInfo();
        }
        return singletonUserInfo;
    }

    public void setUserDetails(SingletonUserInfo user) {
        this.singletonUserInfo = user;
        BCSharedPreference.getInstance()
                .getSharedPreferences()
                .edit()
                .putString(BCSharedPreference.PREF_USER_DETAILS, MiscUtils.getGsonInstance().toJson(singletonUserInfo))
                .apply();
    }

}
