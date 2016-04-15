package com.rushbox.android.rushboxapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;


import com.rushbox.android.rushboxapp.connections.ResponseOperation;
import com.rushbox.android.rushboxapp.connections.TheBoxAppClient;
import com.rushbox.android.rushboxapp.model.User;
import com.rushbox.android.rushboxapp.utils.Fonts;
import com.rushbox.android.rushboxapp.utils.Utility;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtLastName, edtPhoneNumber, edtEmail,
            edtConfirmEmail, edtUserName, edtPassword, edtConfirmPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextInputLayout txtNameLy = (TextInputLayout) findViewById(R.id.name_edt_ly);
        TextInputLayout txtLastNameLy = (TextInputLayout) findViewById(R.id.lastname_edt_ly);
        TextInputLayout txtPhoneNumberLy = (TextInputLayout) findViewById(R.id.phonenumber_edt_ly);
        TextInputLayout txtEmailLy = (TextInputLayout) findViewById(R.id.email_edt_ly);
        TextInputLayout txtConfirmEmailLy = (TextInputLayout) findViewById(R.id.confirmemail_edt_ly);
        TextInputLayout txtUserNameLy = (TextInputLayout) findViewById(R.id.username_edt_ly);
        TextInputLayout txtPasswordLy = (TextInputLayout) findViewById(R.id.password_edt_ly);
        TextInputLayout txtConfirmPasswordLy = (TextInputLayout) findViewById(R.id.confirmpassword_edt_ly);

        edtName = (EditText) findViewById(R.id.name_edt);
        edtLastName = (EditText) findViewById(R.id.lastname_edt);
        edtPhoneNumber = (EditText) findViewById(R.id.phonenumber_edt);
        edtEmail = (EditText) findViewById(R.id.email_edt);
        edtConfirmEmail = (EditText) findViewById(R.id.confirmemail_edt);
        edtUserName = (EditText) findViewById(R.id.username_edt);
        edtPassword = (EditText) findViewById(R.id.password_edt);
        edtConfirmPassword = (EditText) findViewById(R.id.confirmpassword_edt);

        TextView txtSignUpName = (TextView) findViewById(R.id.signup_txt);
        final Button btnSignUp = (Button) findViewById(R.id.sign_up_btn);

        txtNameLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        txtLastNameLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        txtPhoneNumberLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        txtEmailLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        txtConfirmEmailLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        txtUserNameLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        txtPasswordLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        txtConfirmPasswordLy.setTypeface(Fonts.getFuenteElectrolizeRegular(this));

        edtName.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        edtLastName.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        edtPhoneNumber.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        edtEmail.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        edtConfirmEmail.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        edtUserName.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        edtPassword.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        edtConfirmPassword.setTypeface(Fonts.getFuenteElectrolizeRegular(this));

        txtSignUpName.setTypeface(Fonts.getFuenteElectrolizeRegular(this));
        btnSignUp.setTypeface(Fonts.getFuenteMonserratRegular(this));

        btnSignUp.setOnClickListener(this);

        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                TheBoxAppClient.get(TheBoxAppClient.USERSERVICE_BASE_URL + getString(R.string.operation_user_find_user) + editable.toString().trim(), null, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        btnSignUp.setEnabled(false);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        if (responseString.equals("")) {
                            btnSignUp.setEnabled(true);
                        } else {
                            edtUserName.setError(getString(R.string.user_name_exists));
                            btnSignUp.setEnabled(false);
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_btn:
                if (validateFields()) {
                    registerUser();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Build.VERSION.SDK_INT > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
    }

    private void registerUser() {
        User user = new User(edtName.getText().toString().trim(), edtLastName.getText().toString().trim(),
                edtEmail.getText().toString().trim(), edtUserName.getText().toString().trim(),
                edtPassword.getText().toString().trim(), edtPhoneNumber.getText().toString());
        final String jsonUser = new Gson().toJson(user);
        progressDialog = ProgressDialog.show(SignUpActivity.this, "", getString(R.string.singning_up));
        try {
            StringEntity stringEntity = new StringEntity(jsonUser);
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, getString(R.string.json_content_type)));
            TheBoxAppClient.post(getApplicationContext(), TheBoxAppClient.USERSERVICE_BASE_URL + getString(R.string.operation_user_create), stringEntity,
                    stringEntity.getContentType().getValue(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            progressDialog.dismiss();
                            ResponseOperation responseOperation = Utility.JSONObjectToResponseOperation(response);

                            if (responseOperation != null && responseOperation.isResultOperation()) {
                                String jsonString = Utility.transfromInnerDate(responseOperation.getMessage());
                                User user = User.parseUser(jsonString);
                                SharedPreferences preferences =
                                        getSharedPreferences(getString(R.string.preference_app_name), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean(getString(R.string.preference_registered), true);
                                editor.putBoolean(getString(R.string.preference_logged), true);
                                editor.putString(getString(R.string.preference_principal_username), user.getTX_UserName());
                                editor.putString(getString(R.string.preference_principal_password), user.getTX_Password());
                                editor.apply();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.putExtra(getString(R.string.user_passed), user);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(edtEmail,
                                        responseOperation != null ? getString(R.string.error) + responseOperation.getMessage() : "", Snackbar.LENGTH_LONG)
                                        .setAction(getString(R.string.action), null).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            progressDialog.dismiss();
                            Snackbar.make(edtEmail, R.string.error_signin_up, Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.action), null).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        boolean result = false;
        if (edtName.getText().toString().length() > 0) {
            if (edtLastName.getText().toString().length() > 0) {
                if (edtPhoneNumber.getText().toString().length() > 0) {
                    if (Utility.isValid(Utility.PATTERN_PHONE, edtPhoneNumber.getText().toString())) {
                        if (edtEmail.getText().toString().length() > 0) {
                            if (Utility.isValid(Utility.PATTERN_EMAIL, edtEmail.getText().toString())) {
                                if (edtConfirmEmail.getText().toString().equals(edtEmail.getText().toString())) {
                                    if (edtUserName.getText().toString().length() > 0) {
                                        if (edtPassword.getText().toString().length() > 0) {
                                            if (Utility.isValid(Utility.PATTERN_PASSWORD, edtPassword.getText().toString())) {
                                                if (edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
                                                    result = true;
                                                } else {
                                                    edtConfirmPassword.setError(getResources().getString(R.string.error_password_confirmation_no_the_same));
                                                }
                                            } else {
                                                edtPassword.setError(getResources().getString(R.string.error_password_validation));
                                            }
                                        } else {
                                            edtPassword.setError(getResources().getString(R.string.error_field_required));
                                        }
                                    } else {
                                        edtUserName.setError(getResources().getString(R.string.error_field_required));
                                    }
                                } else {
                                    edtConfirmEmail.setError(getResources().getString(R.string.error_mail_confirmation_no_the_same));
                                }
                            } else {
                                edtEmail.setError(getResources().getString(R.string.error_invalid_email));
                            }
                        } else {
                            edtEmail.setError(getResources().getString(R.string.error_field_required));
                        }
                    } else {
                        edtPhoneNumber.setError(getResources().getString(R.string.error_invalid_phone));
                    }
                } else {
                    edtPhoneNumber.setError(getResources().getString(R.string.error_field_required));
                }
            } else {
                edtLastName.setError(getResources().getString(R.string.error_field_required));
            }
        } else {
            edtName.setError(getResources().getString(R.string.error_field_required));
        }
        return result;
    }

}