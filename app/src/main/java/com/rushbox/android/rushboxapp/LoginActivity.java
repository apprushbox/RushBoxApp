package com.rushbox.android.rushboxapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rushbox.android.rushboxapp.connections.TheBoxAppClient;
import com.rushbox.android.rushboxapp.model.User;
import com.rushbox.android.rushboxapp.utils.Fonts;
import com.rushbox.android.rushboxapp.utils.Utility;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class LoginActivity extends AppCompatActivity {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressDialog progressDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_signup:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
                return true;
            case R.id.action_about:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.mipmap.ic_action_box);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.ursername_edt);
        mPasswordView = (EditText) findViewById(R.id.password_edt);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        TextView txtLogIn = (TextView) findViewById(R.id.login_txt);
        TextInputLayout txtLogInLy = (TextInputLayout) findViewById(R.id.username_edt_ly);
        TextInputLayout txtPasswordLy = (TextInputLayout) findViewById(R.id.password_edt_ly);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button mForgotPasswordButton = (Button) findViewById(R.id.forgot_password_btn);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        txtLogIn.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        mEmailView.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        mPasswordView.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        mEmailSignInButton.setTypeface(Fonts.getFuenteMonserratRegular(this));
        mForgotPasswordButton.setTypeface(Fonts.getFuenteMonserratRegular(this));
        txtLogInLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        txtPasswordLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Provider values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
            logIn(email, password);


        }
    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8 && Utility.isValid(Utility.PATTERN_PASSWORD, password);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private void logIn(String username, String password) {
        User user = new User(username, password);
        final String jsonUser = new Gson().toJson(user);
        progressDialog = ProgressDialog.show(LoginActivity.this, "", getString(R.string.dialog_logging_in));
        try {
            StringEntity stringEntity = new StringEntity(jsonUser);
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, getString(R.string.json_content_type)));
            TheBoxAppClient.post(getApplicationContext(), TheBoxAppClient.USERSERVICE_BASE_URL + getString(R.string.operation_user_log_in), stringEntity,
                    stringEntity.getContentType().getValue(),
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            progressDialog.dismiss();
                            if (response != null) {
                                String jsonString = Utility.transfromInnerDate(response.toString());
                                User user = User.parseUser(jsonString);
                                SharedPreferences preferences =
                                        getSharedPreferences(getString(R.string.preference_app_name), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean(getString(R.string.preference_logged), true);
                                editor.putBoolean(getString(R.string.preference_registered), true);
                                editor.putString(getString(R.string.preference_principal_username), user.getTX_UserName());
                                editor.putString(getString(R.string.preference_principal_password), user.getTX_Password());
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(getString(R.string.user_passed), user);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(mEmailView,
                                        getString(R.string.error_log_in), Snackbar.LENGTH_LONG)
                                        .setAction(getString(R.string.action), null).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            progressDialog.dismiss();
                            Snackbar.make(mEmailView, getString(R.string.error_log_in), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.action), null).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            progressDialog.dismiss();
                            Snackbar.make(mEmailView, getString(R.string.error_log_in), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.action), null).show();
                        }
                    });
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            Snackbar.make(mEmailView, getString(R.string.error_log_in), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action), null).show();
        }

    }


}

