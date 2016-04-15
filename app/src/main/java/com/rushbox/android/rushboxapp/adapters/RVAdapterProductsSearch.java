package com.rushbox.android.rushboxapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rushbox.android.rushboxapp.OrderActivity;
import com.rushbox.android.rushboxapp.R;
import com.rushbox.android.rushboxapp.model.Product;
import com.rushbox.android.rushboxapp.model.Provider;
import com.rushbox.android.rushboxapp.utils.Fonts;

import java.util.List;

/**
 * Created by Ronner on 16-12-2015.
 */

public class RVAdapterProductsSearch extends RecyclerView.Adapter<RVAdapterProductsSearch.ProductViewHolder> {


    List<Product> storeProducts;
    Provider store;
    Location mLastLocation;

    public RVAdapterProductsSearch(List<Product> storeProducts, Provider store, Location mLastLocation) {
        this.storeProducts = storeProducts;
        this.store = store;
        this.mLastLocation = mLastLocation;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_product_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int i) {
        holder.tvProductName.setText(storeProducts.get(i).getTX_Name());
        holder.tvPrice.setText("$" + String.valueOf(storeProducts.get(i).getNU_Price()));
        holder.tvShipping.setText(" $" + String.valueOf(storeProducts.get(i).getNU_ShippingCost()));
        final Context context = holder.tvBtnBuy.getContext();

        if (store.isBO_Open()) {

            holder.tvBtnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderActivity.class);
                    intent.putExtra("product_id", storeProducts.get(i).getID_Product());
                    intent.putExtra("provider", store);
                    intent.putExtra("product", storeProducts.get(i));
                    intent.putExtra("location", mLastLocation);
                    context.startActivity(intent);
                    ((Activity) context).setResult(2);
                    ((Activity) context).finish();
                }
            });
        } else {
            holder.tvBtnBuy.setVisibility(View.INVISIBLE);
        }

        if (storeProducts.get(i).isBO_Service()) {
            holder.tvBtnBuy.setText("hire");
            holder.tvBtnBuy.setBackgroundResource(R.drawable.selector_button_blue_light);
            holder.tvDescription.setText(storeProducts.get(i).getTX_Description());
            holder.tvDescription.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.GONE);
            holder.imgPlus.setVisibility(View.GONE);
            holder.tvShipping.setVisibility(View.GONE);
            holder.imgEquals.setVisibility(View.GONE);
            holder.tvPriceTotal.setText(String.valueOf("$" + storeProducts.get(i).getNU_Price()));
        } else {
            holder.tvBtnBuy.setText("buy");
            holder.tvBtnBuy.setBackgroundResource(R.drawable.selector_button_blue);
            holder.tvDescription.setVisibility(View.GONE);
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.imgPlus.setVisibility(View.VISIBLE);
            holder.tvShipping.setVisibility(View.VISIBLE);
            holder.imgEquals.setVisibility(View.VISIBLE);
            holder.tvPriceTotal.setText(String.valueOf("$" + (storeProducts.get(i).getNU_Price() + storeProducts.get(i).getNU_ShippingCost())));
        }
        if (storeProducts.get(i).isBO_SpecialOffer()) {
            holder.lyProduct.setBackgroundColor(context.getResources().getColor((R.color.blue_sky)));
        } else {
            holder.lyProduct.setBackgroundColor(context.getResources().getColor((R.color.white)));
        }


    }

    @Override
    public int getItemCount() {

        return storeProducts.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductName;
        TextView tvDescription;
        TextView tvPrice;
        TextView tvPriceTotal;
        TextView tvBtnBuy;
        ImageView imgPlus;
        TextView tvShipping;
        ImageView imgEquals;
        LinearLayout lyProduct;

        ProductViewHolder(View itemView) {
            super(itemView);

            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvPriceTotal = (TextView) itemView.findViewById(R.id.tvPriceTotal);
            tvBtnBuy = (TextView) itemView.findViewById(R.id.tvBtnBuy);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

            imgPlus = (ImageView) itemView.findViewById(R.id.imgPlus);
            tvShipping = (TextView) itemView.findViewById(R.id.tvShipping);
            imgEquals = (ImageView) itemView.findViewById(R.id.imgEquals);

            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            lyProduct = (LinearLayout) itemView.findViewById(R.id.lyProduct);

            tvProductName.setTypeface(Fonts.getFuenteMonserratRegular(itemView.getContext()));
            tvDescription.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));
            tvPrice.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));
            tvPriceTotal.setTypeface(Fonts.getFuenteMonserratRegular(itemView.getContext()));
            tvBtnBuy.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));
            tvShipping.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));

        }
    }

}