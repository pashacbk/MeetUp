package com.MeetUp.meetup.utils.validate;


import java.util.regex.Pattern;


public class RegexValidator implements Validator<CharSequence> {

    private Pattern pattern;

    public RegexValidator(Pattern pattern) {
        this.pattern = pattern;
    }
    @Override
    public boolean isValid(CharSequence data) {
        return pattern.matcher(data).matches();
    }
}