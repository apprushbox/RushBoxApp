package com.rushbox.android.rushboxapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rushbox.android.rushboxapp.adapters.RVAdapterProductsOrder;
import com.rushbox.android.rushboxapp.model.Order;
import com.rushbox.android.rushboxapp.model.Product;
import com.rushbox.android.rushboxapp.model.Provider;
import com.rushbox.android.rushboxapp.utils.Fonts;
import com.rushbox.android.rushboxapp.utils.LinearLayoutManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private Data data;
    private Order order;
    private Provider provider;
    private RecyclerView rvListOrderProducts;
    private RVAdapterProductsOrder adapter;
    private TextView tvSubTotal;
    private TextView tvTax;
    private TextView tvShipping;
    private TextView tvTotalOrder;
    private TextView tvDeliveryTime;
    private TextView tvTaxTitle;
    private TextView tvAddProduct;
    private TextView tvShippingTitle;
    private CoordinatorLayout coordinatorLayout;
    private int productId;
    private Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        //toolbar.setLogo(R.mipmap.ic_box_small);
        data = Data.getInstance();

        order = data.getOrder();
        // productId = getIntent().getIntExtra("product_id", -1);
        Product product = (Product) getIntent().getSerializableExtra("product");
        mLastLocation = (Location) getIntent().getParcelableExtra("location");
        ArrayList<Product> productsMy = data.getProducts();
        provider = (Provider) getIntent().getSerializableExtra("provider");
        //provider = data.getStoreById(data.getProductById(productId).getStoreId());
        order.setStoreId(provider.getID_Provider());
        TextView tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvSubTotal = (TextView) findViewById(R.id.tvSubTotal);
        tvTax = (TextView) findViewById(R.id.tvTax);
        tvShipping = (TextView) findViewById(R.id.tvShipping);
        TextView tvSubTotalTitle = (TextView) findViewById(R.id.tvSubTotalTitle);
        tvTaxTitle = (TextView) findViewById(R.id.tvTaxTitle);
        tvShippingTitle = (TextView) findViewById(R.id.tvShippingTitle);
        TextView tvTotalOrderTitle = (TextView) findViewById(R.id.tvTotalOrderTitle);
        tvDeliveryTime = (TextView) findViewById(R.id.tvDeliveryTime);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTotalOrder = (TextView) findViewById(R.id.tvTotalOrder);
        TextView tvBtnOK = (TextView) findViewById(R.id.tvBtnOk);
        TextView tvBtnCancel = (TextView) findViewById(R.id.tvBtnCancel);
        rvListOrderProducts = (RecyclerView) findViewById(R.id.rvListOrderProducts);
        rvListOrderProducts.setItemAnimator(new DefaultItemAnimator());
        tvAddProduct = (TextView) findViewById(R.id.tvAddProduct);
        tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, SearchActivity.class);
                intent.putExtra("text_searched", "");
                intent.putExtra("store_id", provider.getID_Provider());
                intent.putExtra("provider", provider);
                intent.putExtra("from", "order");
                intent.putExtra("location", mLastLocation);
                startActivityForResult(intent, 2);
                //finish();
            }
        });

        tvStoreName.setTypeface(Fonts.getFuenteMonserratBold(getApplicationContext()));
        tvAddress.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        tvSubTotal.setTypeface(Fonts.getFuenteMonserratRegular(getApplicationContext()));
        tvTax.setTypeface(Fonts.getFuenteMonserratRegular(getApplicationContext()));
        tvShipping.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        tvTotalOrder.setTypeface(Fonts.getFuenteMonserratBold(getApplicationContext()));
        tvDeliveryTime.setTypeface(Fonts.getFuenteMonserratBold(getApplicationContext()));
        tvSubTotalTitle.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        tvShippingTitle.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        tvTaxTitle.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        tvTotalOrderTitle.setTypeface(Fonts.getFuenteMonserratBold(getApplicationContext()));
        tvBtnOK.setTypeface(Fonts.getFuenteRobotoMedium(getApplicationContext()));
        tvBtnCancel.setTypeface(Fonts.getFuenteRobotoMedium(getApplicationContext()));
        tvTitle.setTypeface(Fonts.getFuenteMonserratBold(getApplicationContext()));
        tvAddProduct.setTypeface(Fonts.getFuenteMonserratRegular(getApplicationContext()));
        tvStoreName.setText(provider.getTX_Name());
        tvAddress.setText(provider.getTX_AddressLine1() + " " + provider.getTX_AddressLine2());
        tvDeliveryTime.setText("???" + " min");
        tvBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setOrder(null);
                startActivity(new Intent(OrderActivity.this, MainActivity.class));
                finish();
            }
        });
        tvBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.setOrder(order);
                startActivity(new Intent(OrderActivity.this,
                        OrderPlacedActivity.class).
                        putExtra("delivery_time", "???")
                        .putExtra("provider", provider));
                finish();
            }
        });
        addProducToOrder(product);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addProducToOrder(Product product) {
        //Product product = data.getProductById(productId);
        product.setOrderAmount(product.getOrderAmount() + 1);
        Product product2 = getOrderProductById(product.getID_Product());
        if (product2 == null)
            order.getListProducts().add(new Product(product.getID_Product(), product.getTX_Name(),
                    product.getTX_Description(), product.getID_Provider(),
                    product.getIM_Image(), product.getNU_Price(), product.getNU_ShippingCost(), product.isBO_Service()));
        else
            product2.setOrderAmount(product2.getOrderAmount() + 1);
        refreshList();
    }

    private Product getOrderProductById(long productId) {
        for (Product product : order.getListProducts())
            if (product.getID_Product() == productId)
                return product;
        return null;
    }

    public void refreshList() {
        adapter = new RVAdapterProductsOrder(order.getListProducts());
        rvListOrderProducts.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvListOrderProducts.setLayoutManager(llm);
        rvListOrderProducts.setAdapter(adapter);
        rvListOrderProducts.setVisibility(View.VISIBLE);
        adapter.setOnDataChangeListener(new RVAdapterProductsOrder.OnDataChangeListener() {
            public void onDataChanged() {
                refreshList();
                calculateTotals();
            }
        });
        /*adapter.setOnServiceDetectedListener(new RVAdapterProductsOrder.OnServiceDetectedListener() {
            @Override
            public void onServiceDetected() {
                tvAddProduct.setVisibility(View.GONE);
                tvShipping.setVisibility(View.GONE);
                tvShippingTitle.setVisibility(View.GONE);
            }
        });*/
        adapter.setOnProductRemovedListener(new RVAdapterProductsOrder.OnProductRemovedListener() {
            @Override
            public void onProductRemoved(final Product product) {
                refreshList();
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Product removed", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                adapter.add(product);
                                adapter.notifyDataSetChanged();
                                Snackbar snackBarAux = Snackbar.make(coordinatorLayout, "Product restored", Snackbar.LENGTH_SHORT);
                                snackBarAux.show();
                            }
                        });
                snackbar.show();
            }
        });
        calculateTotals();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            try {
                productId = data.getIntExtra("product_id", -1);
                Product product = (Product) data.getSerializableExtra("product");
                mLastLocation = (Location) getIntent().getParcelableExtra("location");
                addProducToOrder(product);
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private void calculateTotals() {
        double totalProduct = 0;
        double subTotal = 0;
        double tax = 0;
        double taxRate = 7;
        double total = 0;
        double shipping;
        for (Product product : order.getListProducts()) {
            totalProduct = (product.getNU_Price() * product.getOrderAmount());
            subTotal += totalProduct;
        }
        tax = (subTotal * taxRate) / 100;
        shipping = order.getListProducts().get(0).getNU_ShippingCost();
        total = subTotal + tax + shipping;
        DecimalFormat df = new DecimalFormat("#.##");
        tvSubTotal.setText("$" + String.format("%.2f", subTotal));
        tvTaxTitle.setText("tax(" + String.format("%.2f", taxRate) + " %)");
        tvTax.setText("$" + String.format("%.2f", tax));
        tvShipping.setText("$" + String.format("%.2f", shipping));
        tvTotalOrder.setText("$" + String.format("%.2f", total));
        order.setSubTotal(subTotal);

    }
}
