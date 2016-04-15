package com.rushbox.android.rushboxapp;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rushbox.android.rushboxapp.adapters.RVAdapterProviderSearch;
import com.rushbox.android.rushboxapp.comparators.StoreAvailabilityAndDistanceComparator;
import com.rushbox.android.rushboxapp.connections.TheBoxAppClient;
import com.rushbox.android.rushboxapp.locations.Locations;
import com.rushbox.android.rushboxapp.model.MyObject;
import com.rushbox.android.rushboxapp.model.Product;
import com.rushbox.android.rushboxapp.model.Provider;
import com.rushbox.android.rushboxapp.utils.DividerItemDecoration;
import com.rushbox.android.rushboxapp.utils.Fonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class SearchActivity extends AppCompatActivity {

    private MultiAutoCompleteTextView mActvSearch;
    private RecyclerView rvStoreList;
    private ArrayList<Provider> providers;
    private ImageButton imgBtnSearch;
    private MyObject myObject;
    private ArrayList<Product> products;
    private String textSearched;

    private String desde;
    private TextView tvWarning;
    private TextView tvStoreName;
    private TextView tvAddress;
    private TextView tvDeliveryTime;
    private CardView cvStore;
    private TextView tvOpen;
    private Location mLastLocation;
    private Provider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        mActvSearch = (MultiAutoCompleteTextView) findViewById(R.id.actvSearch);


        cvStore = (CardView) findViewById(R.id.cvItem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        imgBtnSearch = (ImageButton) findViewById(R.id.imgBtnSearch);
        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });

        tvWarning = (TextView) findViewById(R.id.tvWarning);
        tvWarning.setVisibility(View.GONE);


        rvStoreList = (RecyclerView) findViewById(R.id.rvList);
        rvStoreList.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvStoreList.addItemDecoration(itemDecoration);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvStoreList.setLayoutManager(llm);

        desde = getIntent().getStringExtra("from");
        mLastLocation = (Location) getIntent().getParcelableExtra("location");
        try {
            provider = (Provider) getIntent().getSerializableExtra("provider");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (desde.equals("main")) {
            textSearched = getIntent().getStringExtra("text_searched");
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            cvStore.setVisibility(View.GONE);
        } else {
            textSearched = "";
            provider = (Provider) getIntent().getSerializableExtra("provider");
            tvStoreName = (TextView) findViewById(R.id.tvStoreName);
            tvAddress = (TextView) findViewById(R.id.tvAddress);
            tvDeliveryTime = (TextView) findViewById(R.id.tvDeliveryTime);
            tvStoreName.setText(provider.getTX_Name());
            tvAddress.setText(provider.getTX_AddressLine1() + "\n" + provider.getTX_AddressLine2());
            tvDeliveryTime.setText(String.valueOf(Locations.calculateDeliveryTime(provider.getGE_Location())));
            tvOpen = (TextView) findViewById(R.id.tvOpen);
            tvOpen.setText(provider.isBO_Open() ? "Open" : "Closed");
            if (provider.isBO_Open())
                tvOpen.setBackgroundResource(R.drawable.custom_text_view_green);
            else
                tvOpen.setBackgroundResource(R.drawable.custom_text_view_red);
            cvStore.setVisibility(View.VISIBLE);
        }

        mActvSearch.setText(textSearched);
        //mActvSearch.clearFocus();
        performSearch();

        //providers = data.getStores();
        //myObject = data.getProductsBySearch(textSearched.toLowerCase());
//        providers = myObject.providers;
//        products = myObject.products;
//        Collections.sort(providers, new StoreAvailabilityAndDistanceComparator());
//        RVAdapterProviderSearch rvAdapter = new RVAdapterProviderSearch(providers, products, getApplicationContext());
//        rvStoreList.setAdapter(rvAdapter);
        //rvStoreList.setVisibility(View.VISIBLE);
        mActvSearch.setSelection(mActvSearch.getText().length());
        mActvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mActvSearch.getText().toString().charAt(mActvSearch.length() - 2) == ',')
                    mActvSearch.setText(mActvSearch.getText().toString().trim().substring(0, mActvSearch.getText().length() - 2));
                mActvSearch.setSelection(mActvSearch.getText().length());
                textSearched = mActvSearch.getText().toString();
            }
        });
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
                textSearched = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mActvSearch.setOnEditorActionListener(new MultiAutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mActvSearch.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });

        /*ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data.getStringProducts());
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fuente));

        mActvSearch.setAdapter(adapter);
        mActvSearch.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        mActvSearch.setTypeface(font);*/

        tvWarning.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        if (cvStore.getVisibility() == View.VISIBLE) {
            tvStoreName.setTypeface(Fonts.getFuenteMonserratBold(getApplicationContext()));
            tvAddress.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
            tvDeliveryTime.setTypeface(Fonts.getFuenteMonserratRegular(getApplicationContext()));
            tvOpen.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        }

        mActvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                performSearch();
            }
        });
        mActvSearch.setTypeface(Fonts.getFuenteMonserratRegular(getApplicationContext()));
    }

    private void search(String text, long providerId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Text", text);
            jsonObject.put("Latitud", mLastLocation.getLatitude());
            jsonObject.put("Longitud", mLastLocation.getLongitude());
            jsonObject.put("ID_Provider", providerId);
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, getString(R.string.json_content_type)));
            TheBoxAppClient.post(getApplicationContext(), TheBoxAppClient.PRODUCSERVICE_BASE_URL + getString(R.string.operation_product_findproductbylocation), stringEntity,
                    stringEntity.getContentType().getValue(),
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);


                            String prod = null;
                            String prov = null;
                            try {
                                prov = response.getJSONArray("providers").toString();
                                prod = response.getJSONArray("products").toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Type collectionTypeProducts = new TypeToken<ArrayList<Product>>() {
                            }.getType();
                            Type collectionTypeProviders = new TypeToken<ArrayList<Provider>>() {
                            }.getType();
                            products = new Gson().fromJson(prod, collectionTypeProducts);
                            providers = new Gson().fromJson(prov, collectionTypeProviders);
                            //result = Arrays.copyOf(strings.toArray(), strings.size(), String[].class);
                            /*if (result != null && result.length > 0) {
                                adapterAutocomplete = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, result);
                                adapterAutocomplete.notifyDataSetChanged();
                                mActvSearch.setAdapter(adapterAutocomplete);
                                mActvSearch.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            }*/
                            //providers = myObject.providers;
                            //products = myObject.products;
                            if (products.size() > 0) {
                                tvWarning.setVisibility(View.GONE);
                                cvStore.setVisibility(View.GONE);
                            } else {
                                tvWarning.setVisibility(View.VISIBLE);
                                if (desde.equals("order"))
                                    cvStore.setVisibility(View.VISIBLE);
                            }

                            Collections.sort(providers, new StoreAvailabilityAndDistanceComparator());
                            RVAdapterProviderSearch rvAdapter = new RVAdapterProviderSearch(providers, products, getApplicationContext(), mLastLocation);
                            rvStoreList.setAdapter(rvAdapter);
                            rvStoreList.setVisibility(View.VISIBLE);

                            mActvSearch.setSelection(mActvSearch.getText().length());
                            mActvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (mActvSearch.getText().toString().charAt(mActvSearch.length() - 2) == ',')
                                        mActvSearch.setText(mActvSearch.getText().toString().trim().substring(0, mActvSearch.getText().length() - 2));
                                    mActvSearch.setSelection(mActvSearch.getText().length());
                                    textSearched = mActvSearch.getText().toString();
                                }
                            });
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
                                    textSearched = charSequence.toString();
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });
                            mActvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    performSearch();
                                }
                            });
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

    private void performSearch() {
        mActvSearch.dismissDropDown();

        if (mActvSearch.getText().toString().length() > 0) {
            if (mActvSearch.length() > 2 && mActvSearch.getText().toString().charAt(mActvSearch.length() - 2) == ',') {
                mActvSearch.setText(mActvSearch.getText().toString().trim().substring(0, mActvSearch.getText().length() - 2));
            }
            mActvSearch.setSelection(mActvSearch.getText().length());
            if (desde.equals("main"))
                search(textSearched.toLowerCase(), 0);
                //myObject = data.getProductsBySearch(textSearched.toLowerCase());
            else
                search(textSearched.toLowerCase(), provider.getID_Provider());
            //myObject = data.getProductsBySearch(textSearched.toLowerCase(), storeId);


        } else {
            Snackbar.make(mActvSearch, "Please, write something", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
