package com.rushbox.android.rushboxapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rushbox.android.rushboxapp.LoginActivity;
import com.rushbox.android.rushboxapp.R;
import com.rushbox.android.rushboxapp.SignUpActivity;
import com.rushbox.android.rushboxapp.utils.Fonts;


/**
 * A placeholder fragment containing a simple view.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {

    public WelcomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button btnSignIn = (Button) view.findViewById(R.id.signin_btn);
        Button btnSignUp = (Button) view.findViewById(R.id.signup_btn);
        TextView txtWelcome = (TextView) view.findViewById(R.id.welcome_txt);
        btnSignIn.setTypeface(Fonts.getFuenteMonserratRegular(this.getContext()));
        btnSignUp.setTypeface(Fonts.getFuenteMonserratBold(this.getContext()));
        txtWelcome.setTypeface(Fonts.getFuenteElectrolizeRegular(this.getContext()));

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.signin_btn:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.signup_btn:
                intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
