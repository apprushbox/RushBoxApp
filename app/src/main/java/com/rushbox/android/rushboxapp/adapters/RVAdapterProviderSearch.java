package com.rushbox.android.rushboxapp.adapters;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rushbox.android.rushboxapp.R;
import com.rushbox.android.rushboxapp.model.Product;
import com.rushbox.android.rushboxapp.model.Provider;
import com.rushbox.android.rushboxapp.utils.Fonts;
import com.rushbox.android.rushboxapp.utils.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronner on 16-12-2015.
 */
public class RVAdapterProviderSearch extends RecyclerView.Adapter<RVAdapterProviderSearch.StoreViewHolder> {
    List<Provider> storesFinded;
    List<Product> productsFinded;
    List<Product> storeProducts;
    Context context;
    Location mLastLocation;

    public RVAdapterProviderSearch(List<Provider> storesFinded, List<Product> productsFinded, Context context, Location mLastLocation) {
        this.storesFinded = storesFinded;
        this.productsFinded = productsFinded;
        this.context = context;
        this.mLastLocation = mLastLocation;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card_item, parent, false);
        StoreViewHolder pvh = new StoreViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int i) {
        holder.tvStoreName.setText(storesFinded.get(i).getTX_Name());
        holder.tvDistance.setText(" " + String.valueOf(storesFinded.get(i).getNU_Distance()) + "M");
        holder.tvAddress.setText(" " + String.valueOf(storesFinded.get(i).getTX_AddressLine1()));
        //holder.tvTime.setText(" " +String.valueOf(storesFinded.get(i).getTimeShortDistance()) + " min");
        //holder.tvShipping.setText(" $" + String.valueOf(storesFinded.get(i).getShipShortDistance()));
        holder.tvOpen.setText(storesFinded.get(i).isBO_Open() ? "Open" : "Closed");
        if (storesFinded.get(i).isBO_Open())
            holder.tvOpen.setBackgroundResource(R.drawable.custom_text_view_green);
        else
            holder.tvOpen.setBackgroundResource(R.drawable.custom_text_view_red);
        storeProducts = getProductsByStoreId(storesFinded.get(i).getID_Provider());
        if (storeProducts.size() > 0) {
            holder.rvListProducts.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(holder.rvListProducts.getContext());
            holder.rvListProducts.setLayoutManager(llm);
            RVAdapterProductsSearch storeProductsAdapter = new RVAdapterProductsSearch(storeProducts, storesFinded.get(i), mLastLocation);
            holder.rvListProducts.setAdapter(storeProductsAdapter);
            holder.rvListProducts.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return storesFinded.size();
    }

    private List<Product> getProductsByStoreId(long storeId) {
        List<Product> storeProducts = new ArrayList<>();
        for (Product product : productsFinded) {
            if (product.getID_Provider() == storeId)
                storeProducts.add(product);
        }
        return storeProducts;
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        CardView cvItem;
        TextView tvStoreName;
        TextView tvAddress;
        TextView tvDistance;
        TextView tvTime;
        TextView tvShipping;
        TextView tvOpen;
        RecyclerView rvListProducts;

        StoreViewHolder(View itemView) {
            super(itemView);
            cvItem = (CardView) itemView.findViewById(R.id.cvItem);
            tvStoreName = (TextView) itemView.findViewById(R.id.tvStoreName);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvShipping = (TextView) itemView.findViewById(R.id.tvShipping);
            tvOpen = (TextView) itemView.findViewById(R.id.tvOpen);
            rvListProducts = (RecyclerView) itemView.findViewById(R.id.rvListProducts);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);

            tvStoreName.setTypeface(Fonts.getFuenteMonserratBold(itemView.getContext()));
            tvDistance.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));
            tvTime.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));
            tvShipping.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));
            tvOpen.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));
            tvAddress.setTypeface(Fonts.getFuenteElectrolizeRegular(itemView.getContext()));
        }
    }

}
