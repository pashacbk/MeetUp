package com.MeetUp.meetup.screens.auth.modules.signin.dagger;

import android.content.Context;
import android.content.res.AssetManager;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.data.assets.AssetsHelper;
import com.MeetUp.meetup.data.assets.UserParser;
import com.MeetUp.meetup.screens.auth.modules.signin.SignInModel;
import com.MeetUp.meetup.screens.auth.modules.signin.SignInPresenter;
import com.MeetUp.meetup.screens.auth.modules.signin.server.SignInMockServer;
import com.MeetUp.meetup.utils.validate.RegexValidator;
import com.MeetUp.meetup.utils.validate.Validator;

import java.util.Map;
import java.util.regex.Pattern;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class SignInModule {

    private static final String CREDENTIALS_PATH = "credentials/users";
    public static final String EMAIL_VALIDATOR = "email_validator";
    public static final String EMPTY_VALIDATOR = "empty_validator";
    public static final String PASSWORD_VALIDATOR = "password_validator";

    @Provides
    @SignInScope
    SignInPresenter providesPresenter(Map<String, Validator<CharSequence>> validators,
                                      SignInModel model) {
        return new SignInPresenter(validators, model);
    }

    @Provides
    @IntoMap
    @StringKey(EMAIL_VALIDATOR)
    Validator<CharSequence> providesEmailValidator(Context context) {
        String regex = context.getString(R.string.email_regex);
        return new RegexValidator(Pattern.compile(regex));
    }

    @Provides
    @IntoMap
    @StringKey(PASSWORD_VALIDATOR)
    Validator<CharSequence> providesPasswordValidator(Context context) {
        String regex = context.getString(R.string.password_regex);
        return new RegexValidator(Pattern.compile(regex));
    }

    @Provides
    @IntoMap
    @StringKey(EMPTY_VALIDATOR)
    Validator<CharSequence> providesEmptyValidator(Context context) {
        String regex = context.getString(R.string.empty_regex);
        return new RegexValidator(Pattern.compile(regex));
    }

    @Provides
    @SignInScope
    SignInModel providesModel(SignInMockServer server) {
        return new SignInModel(server);
    }

    @Provides
    @SignInScope
    SignInMockServer providesServer(AssetsHelper helper) {
        return SignInMockServer.getInstance(helper, CREDENTIALS_PATH);
    }

    @Provides
    @SignInScope
    AssetsHelper providesAssetsHelper(AssetManager assetManager, UserParser userParser) {
        return new AssetsHelper(assetManager, userParser);
    }

    @Provides
    @SignInScope
    UserParser providesUserParser() {
        return new UserParser();
    }
}
