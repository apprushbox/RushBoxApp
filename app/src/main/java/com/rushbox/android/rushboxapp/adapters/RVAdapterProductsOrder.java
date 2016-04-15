package com.rushbox.android.rushboxapp.adapters;

/**
 * Created by Ronner on 17-12-2015.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.rushbox.android.rushboxapp.R;
import com.rushbox.android.rushboxapp.model.Product;
import com.rushbox.android.rushboxapp.utils.Fonts;

import java.util.List;

public class RVAdapterProductsOrder extends RecyclerView.Adapter<RVAdapterProductsOrder.ProductViewHolder> {

    List<Product> orderProducts;


    public RVAdapterProductsOrder(List<Product> orderProducts) {
        this.orderProducts = orderProducts;

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_order_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int i) {
        holder.tvAmount.setText(orderProducts.get(i).getOrderAmount().toString());
        holder.tvAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvAmount.setVisibility(View.INVISIBLE);
                holder.edtAmount.setText(orderProducts.get(i).getOrderAmount().toString());
                holder.edtAmount.setSelection(holder.edtAmount.getText().length());
                holder.edtAmount.setVisibility(View.VISIBLE);
                holder.edtAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            orderProducts.get(i).setOrderAmount(Integer.parseInt(holder.edtAmount.getText().toString()));
                            holder.tvAmount.setVisibility(View.VISIBLE);
                            holder.tvAmount.setText(orderProducts.get(i).getOrderAmount().toString());
                            holder.edtAmount.setVisibility(View.INVISIBLE);
                            InputMethodManager in = (InputMethodManager) holder.tvAmount.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(holder.tvAmount.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            notifyDataSetChanged();
                            if (mOnDataChangeListener != null) {
                                mOnDataChangeListener.onDataChanged();
                            }
                            return true;
                        }
                        return false;
                    }
                });
                //Action
            }
        });
        holder.tvProductName.setText(orderProducts.get(i).getTX_Name());
        holder.tvProductName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(orderProducts.get(i));
                notifyDataSetChanged();
                return true;
            }
        });
        holder.tvUnitPrice.setText("$" + String.valueOf(orderProducts.get(i).getNU_Price()));
        holder.tvTotalPrice.setText(String.valueOf("$" + String.format("%.2f", orderProducts.get(i).getNU_Price() * orderProducts.get(i).getOrderAmount() )));
        if(orderProducts.get(i).getOrderAmount()>1)
            holder.tvUnitPrice.setVisibility(View.VISIBLE);
        else
            holder.tvUnitPrice.setVisibility(View.INVISIBLE);

        if(orderProducts.get(i).isBO_Service()){
            if(mOnServiceDetectedListener != null){
                mOnServiceDetectedListener.onServiceDetected();
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        EditText edtAmount;
        TextView tvAmount;
        TextView tvProductName;
        TextView tvUnitPrice;
        TextView tvTotalPrice;


        ProductViewHolder(View itemView) {
            super(itemView);

            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvUnitPrice = (TextView) itemView.findViewById(R.id.tvUnitPrice);
            tvTotalPrice = (TextView) itemView.findViewById(R.id.tvTotalPrice);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            edtAmount = (EditText) itemView.findViewById(R.id.edtAmount);

            Typeface font = Fonts.getFuenteMonserratRegular(itemView.getContext());

            tvProductName.setTypeface(font);
            tvUnitPrice.setTypeface(font);
            tvTotalPrice.setTypeface(font);
            tvAmount.setTypeface(font);
            edtAmount.setTypeface(font);
        }
    }
    public interface OnDataChangeListener{
        public void onDataChanged();
    }
    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }
    public interface OnProductRemovedListener{
        public void onProductRemoved(Product product);
    }
    OnProductRemovedListener mOnProductRemovedListener;
    public void setOnProductRemovedListener(OnProductRemovedListener onProductRemovedListener){
        mOnProductRemovedListener = onProductRemovedListener;
    }
    public interface OnServiceDetectedListener{
        public void onServiceDetected();
    }
    OnServiceDetectedListener mOnServiceDetectedListener;
    public void setOnServiceDetectedListener(OnServiceDetectedListener onServiceDetectedListener){
        mOnServiceDetectedListener = onServiceDetectedListener;
    }
    public void add(Product item) {
        orderProducts.add(item);
        notifyDataSetChanged();
        //notifyItemInserted(position);
        if (mOnDataChangeListener != null) {
            mOnDataChangeListener.onDataChanged();
        }
    }

    public void remove(Product item) {
        int position = orderProducts.indexOf(item);
        if (mOnProductRemovedListener != null) {
            mOnProductRemovedListener.onProductRemoved(orderProducts.get(position));
        }
        orderProducts.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        if (mOnDataChangeListener != null) {
            mOnDataChangeListener.onDataChanged();
        }
    }
}
