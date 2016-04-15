package com.rushbox.android.rushboxapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rushbox.android.rushboxapp.LoginActivity;
import com.rushbox.android.rushboxapp.R;
import com.rushbox.android.rushboxapp.SearchActivity;
import com.rushbox.android.rushboxapp.connections.TheBoxAppClient;
import com.rushbox.android.rushboxapp.model.User;
import com.rushbox.android.rushboxapp.utils.Fonts;
import com.rushbox.android.rushboxapp.utils.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private OnFragmentInteractionListener mListener;
    private String[] result = null;
    private User user;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private MultiAutoCompleteTextView mActvSearch;
    private LatLng mLatLng;
    private TextView txtAddress;
    private boolean mAddressLista;
    private ImageButton imgBtnSearch;
    private ArrayAdapter adapterAutocomplete;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        /*TextView txt = (TextView) view.findViewById(R.id.hellopf);
        try {
            if (getArguments().getSerializable(getString(R.string.user_passed)) != null) {
                User user = (User) getArguments().getSerializable(getString(R.string.user_passed));
                txt.setText(user.getTX_Name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        SharedPreferences preferences =
                getContext().getSharedPreferences(getString(R.string.preference_app_name), Context.MODE_PRIVATE);

        String userName = preferences.getString(getString(R.string.preference_principal_username), null);
        String password = preferences.getString(getString(R.string.preference_principal_password), null);
        if (userName != null && password != null) {
            logIn(userName, password);
        } else {
            getActivity().finish();
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (mGoogleApiClient == null) {
                    mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                            .addConnectionCallbacks(MapFragment.this)
                            .addOnConnectionFailedListener(MapFragment.this)
                            .addApi(LocationServices.API)
                            .build();
                    mGoogleApiClient.connect();

                }
            }
        }).start();
        imgBtnSearch = (ImageButton) view.findViewById(R.id.imgBtnSearch);
        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mActvSearch.dismissDropDown();
                performSearch();
            }
        });
        imgBtnSearch.setVisibility(View.GONE);

        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        mActvSearch = (MultiAutoCompleteTextView) view.findViewById(R.id.actvSearch);
        mActvSearch.requestFocus();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mActvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mActvSearch.getText().toString().length() > 0)
                    imgBtnSearch.setVisibility(View.VISIBLE);
                else
                    imgBtnSearch.setVisibility(View.GONE);
                search(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mActvSearch.setTypeface(Fonts.getFuenteMonserratRegular(getContext()));
        txtAddress.setTypeface(Fonts.getFuenteElectrolizeRegular(getContext()));
        mActvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mActvSearch.getText().toString().charAt(mActvSearch.length() - 2) == ',')
                    mActvSearch.setText(mActvSearch.getText().toString().trim().substring(0, mActvSearch.getText().length() - 2));
                mActvSearch.setSelection(mActvSearch.getText().length());
            }
        });
        mActvSearch.setOnEditorActionListener(new MultiAutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    //performSearch();
                    mActvSearch.dismissDropDown();
                    InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mActvSearch.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        mActvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mActvSearch.dismissDropDown();
                performSearch();
            }
        });

        return view;
    }

    private void search(String text) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Text", text);
            jsonObject.put("Latitud", mLastLocation.getLatitude());
            jsonObject.put("Longitud", mLastLocation.getLongitude());
            jsonObject.put("ID_Provider", 0);

            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, getString(R.string.json_content_type)));
            TheBoxAppClient.post(getActivity().getApplicationContext(), TheBoxAppClient.PRODUCSERVICE_BASE_URL + getString(R.string.operation_product_findproductnames), stringEntity,
                    stringEntity.getContentType().getValue(),
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                            String json = response.toString();
                            Type collectionType = new TypeToken<Collection<String>>() {
                            }.getType();
                            Collection<String> strings = new Gson().fromJson(json, collectionType);
                            result = Arrays.copyOf(strings.toArray(), strings.size(), String[].class);
                            if (result != null && result.length > 0) {
                                adapterAutocomplete = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, result);
                                adapterAutocomplete.notifyDataSetChanged();
                                mActvSearch.setAdapter(adapterAutocomplete);
                                mActvSearch.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            super.onSuccess(statusCode, headers, responseString);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void logIn(String username, String password) {
        user = new User(username, password);
        final String jsonUser = new Gson().toJson(user);
        //progressDialog = ProgressDialog.show(LoginActivity.this, "", getString(R.string.dialog_logging_in));
        try {
            SharedPreferences preferences = getContext().
                    getSharedPreferences(getString(R.string.preference_app_name), Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferences.edit();
            StringEntity stringEntity = new StringEntity(jsonUser);
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, getString(R.string.json_content_type)));
            TheBoxAppClient.post(getContext(), TheBoxAppClient.USERSERVICE_BASE_URL + getString(R.string.operation_user_log_in), stringEntity,
                    stringEntity.getContentType().getValue(),
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            if (response != null) {
                                String jsonString = Utility.transfromInnerDate(response.toString());
                                user = User.parseUser(jsonString);
                                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                                TextView firstText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_first_text);
                                firstText.setText(user.getTX_Name());
                                editor.putBoolean(getString(R.string.preference_logged), true);
                                editor.putBoolean(getString(R.string.preference_registered), true);
                                editor.putString(getString(R.string.preference_principal_username), user.getTX_UserName());
                                editor.putString(getString(R.string.preference_principal_password), user.getTX_Password());
                                editor.apply();
                            } else {
                                Snackbar.make(mActvSearch,
                                        getString(R.string.error_log_in), Snackbar.LENGTH_LONG)
                                        .setAction(getString(R.string.action), null).show();
                                editor.putBoolean(getString(R.string.preference_logged), false);
                                editor.putBoolean(getString(R.string.preference_registered), true);
                                editor.putString(getString(R.string.preference_principal_username), null);
                                editor.putString(getString(R.string.preference_principal_password), null);
                                editor.apply();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Snackbar.make(mActvSearch, getString(R.string.error_log_in), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.action), null).show();

                            editor.putBoolean(getString(R.string.preference_logged), false);
                            editor.putBoolean(getString(R.string.preference_registered), true);
                            editor.putString(getString(R.string.preference_principal_username), null);
                            editor.putString(getString(R.string.preference_principal_password), null);
                            editor.apply();
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            editor.putBoolean(getString(R.string.preference_logged), false);
                            editor.putBoolean(getString(R.string.preference_registered), true);
                            editor.putString(getString(R.string.preference_principal_username), null);
                            editor.putString(getString(R.string.preference_principal_password), null);
                            editor.apply();
                            getActivity().finish();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(mActvSearch, getString(R.string.error_log_in), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action), null).show();
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }

    }

    private void performSearch() {
        if (mActvSearch.getText().toString().length() > 0) {
            if (mActvSearch.length() > 2 && mActvSearch.getText().toString().charAt(mActvSearch.length() - 2) == ',')
                mActvSearch.setText(mActvSearch.getText().toString().trim().substring(0, mActvSearch.getText().length() - 2));
            mActvSearch.setSelection(mActvSearch.getText().length());

            Intent intent = new Intent(getContext(), SearchActivity.class);
            intent.putExtra("text_searched", mActvSearch.getText().toString());
            intent.putExtra("store_id", "");
            intent.putExtra("from", "main");
            intent.putExtra("location", mLastLocation);
            startActivity(intent);
            getActivity().finish();
        } else {
            Snackbar.make(mActvSearch, "Please, write something", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            mLastLocation.setLatitude(25.824035);
            mLastLocation.setLongitude(-80.316227);
        //} catch (Exception e) {
           // e.printStackTrace();

            //mLastLocation.setLatitude(25.824035);
            //mLastLocation.setLongitude(-80.316227);
        //}
        mLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        // Add a marker in YourLocation and move the camera
        if (mLastLocation != null) {
            mMap.addMarker(new MarkerOptions().position(mLatLng).title("You are here"));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
            getAddress();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /*protected void onStart() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mGoogleApiClient == null) {
                    mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                            .addConnectionCallbacks(MapFragment.this)
                            .addOnConnectionFailedListener(MapFragment.this)
                            .addApi(LocationServices.API)
                            .build();
                } else {
                    mGoogleApiClient.connect();
                }
            }
        };
        new Thread(runnable).start();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }*/

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void getAddress() {
        mAddressLista = false;
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                Geocoder geo = new Geocoder(getContext(), Locale.getDefault());
                if (Geocoder.isPresent()) {
                    while (!mAddressLista) {
                        try {
                            List<Address> addresses = geo.getFromLocation(mLatLng.latitude, mLatLng.longitude, 1);
                            if (addresses != null && addresses.size() > 0) {
                                Address address = addresses.get(0);
                                final String addressText = String.format("%s, %s, %s",
                                        // If there's a street address, add it
                                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                                        // Locality is usually a city
                                        address.getLocality(),
                                        // The country of the address
                                        address.getCountryName());

                                handler.post(new Runnable() {
                                    public void run() {
                                        txtAddress.setText(addressText);
                                        txtAddress.setVisibility(View.VISIBLE);
                                        mAddressLista = true;
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();

                            handler.post(new Runnable() {
                                public void run() {
                                    txtAddress.setVisibility(View.GONE);
                                }
                            });
                        }
                    }


                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
