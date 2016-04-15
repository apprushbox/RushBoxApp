package com.rushbox.android.rushboxapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rushbox.android.rushboxapp.model.Provider;
import com.rushbox.android.rushboxapp.utils.Fonts;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderPlacedActivity extends AppCompatActivity {

    private Data data;
    private TextView tvStoreName;
    private TextView tvAddress;
    private TextView tvOpen;
    private TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setLogo(R.mipmap.ic_box_small);
        data = Data.getInstance();
        Integer deliveryTime = getIntent().getIntExtra("delivery_time", 0);
        Provider store = (Provider) getIntent().getSerializableExtra("provider");
        TextView tvThank = (TextView) findViewById(R.id.tvThank);
        TextView tvMessage = (TextView) findViewById(R.id.tvMessage);
        TextView tvDeliveryTimeTitle = (TextView) findViewById(R.id.tvDeliveryTimeTitle);
        TextView tvDeliveryTime = (TextView) findViewById(R.id.tvDeliveryTime);
        TextView tvBtnOK = (TextView) findViewById(R.id.tvBtnOk);

        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        tvStoreName.setText(store.getTX_Name());
        tvAddress.setText(store.getTX_AddressLine1() + " " + store.getTX_AddressLine2());
        tvOpen = (TextView) findViewById(R.id.tvOpen);
        tvOpen.setText(store.isBO_Open() ? "Open" : "Closed");
        if (store.isBO_Open())
            tvOpen.setBackgroundResource(R.drawable.custom_text_view_green);
        else
            tvOpen.setBackgroundResource(R.drawable.custom_text_view_red);
        tvPhone.setText(store.getTX_PhoneNumber());
        Typeface font = Fonts.getFuenteMonserratRegular(getApplicationContext());
        tvThank.setTypeface(Fonts.getFuenteMonserratBold(getApplicationContext()));
        tvMessage.setTypeface(Fonts.getFuenteMonserratRegular(getApplicationContext()));
        tvDeliveryTimeTitle.setTypeface(Fonts.getFuenteMonserratRegular(getApplicationContext()));
        tvDeliveryTime.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        tvBtnOK.setTypeface(font);

        tvStoreName.setTypeface(Fonts.getFuenteMonserratBold(getApplicationContext()));
        tvAddress.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        tvOpen.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));
        tvPhone.setTypeface(Fonts.getFuenteElectrolizeRegular(getApplicationContext()));

        tvBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setOrder(null);
                data.countOrder += 1;
                if (data.countOrder == 3) {
                    //Notify();
                    data.countOrder = 0;
                }
                startActivity(new Intent(OrderPlacedActivity.this, MainActivity.class));
                finish();
            }
        });

        if (data.getOrder().getListProducts().get(0).isBO_Service()) {
            if (null != null) {
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(data.getOrder().getListProducts().get(0).getStartTime());
//                cal.add(Calendar.MINUTE, (-1 * deliveryTime));
//                tvDeliveryTime.setText(df.format(cal.getTime()));
            } else {
                SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, deliveryTime);
                tvDeliveryTime.setText(df.format(cal.getTime()));
            }
        } else {
            SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, deliveryTime);
            tvDeliveryTime.setText(df.format(cal.getTime()));
        }


    }

//    private void Notify() {
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
//        mBuilder.setSmallIcon(R.drawable.ic_box_notif);
//        mBuilder.setContentTitle("Order Received");
//        mBuilder.setContentText("You have received a new order");
//        mBuilder.setAutoCancel(true);
//        Intent resultIntent = new Intent(getApplicationContext(), OrderReceivedActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
//        stackBuilder.addParentStack(MainActivity.class);
//
//// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//// notificationID allows you to update the notification later on.
//        mNotificationManager.notify(1, mBuilder.build());
//
//    }

}
