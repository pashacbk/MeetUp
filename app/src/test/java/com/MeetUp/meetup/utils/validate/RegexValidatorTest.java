package com.MeetUp.meetup.utils.validate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.regex.Pattern;

@RunWith(MockitoJUnitRunner.class)
public class RegexValidatorTest {

    private static final Pattern EMPTY_PATTERN = Pattern.compile("^$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(".{8,}");
    private static final String VALID_EMAIL = "a@a.a";
    private static final String VALID_PASSWORD = "12345678";
    private static final String EMPTY_STRING = "";
    RegexValidator regexValidator;

    @Test
    public void emptyValidatorShouldReturnTrueWhenParameterIsEmpty() {
        regexValidator = new RegexValidator(EMPTY_PATTERN);
        Boolean isValid = regexValidator.isValid(EMPTY_STRING);
        Assert.assertEquals(isValid, true);
    }

    @Test
    public void emptyValidatorShouldReturnFalseWhenParameterIsNotEmpty() {
        regexValidator = new RegexValidator(EMPTY_PATTERN);
        Boolean isValid = regexValidator.isValid(VALID_EMAIL);
        Assert.assertEquals(isValid, false);
    }

    @Test
    public void emailValidatorShouldReturnTrueWhenParameterIsEmpty() {
        regexValidator = new RegexValidator(EMAIL_PATTERN);
        Boolean isValid = regexValidator.isValid(EMPTY_STRING);
        Assert.assertEquals(isValid, false);
    }

    @Test
    public void emailValidatorShouldReturnFalseWhenParameterIsNotEmpty() {
        regexValidator = new RegexValidator(EMAIL_PATTERN);
        Boolean isValid = regexValidator.isValid(VALID_EMAIL);
        Assert.assertEquals(isValid, true);
    }


    @Test
    public void passwordValidatorShouldReturnTrueWhenParameterIsEmpty() {
        regexValidator = new RegexValidator(PASSWORD_PATTERN);
        Boolean isValid = regexValidator.isValid(EMPTY_STRING);
        Assert.assertEquals(isValid, false);
    }

    @Test
    public void passwordValidatorShouldReturnFalseWhenParameterIsNotEmpty() {
        regexValidator = new RegexValidator(PASSWORD_PATTERN);
        Boolean isValid = regexValidator.isValid(VALID_PASSWORD);
        Assert.assertEquals(isValid, true);
    }

}