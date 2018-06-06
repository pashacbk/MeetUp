package com.MeetUp.meetup.screens.auth.modules.signup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SignUpModel {

    public List<String> getCountryList() {
        List<String> countries = new ArrayList<>();
        for (String countryCode : Locale.getISOCountries()) {
            Locale locale = new Locale("", countryCode);
            countries.add(locale.getDisplayCountry());
        }
        return countries;
    }
}
