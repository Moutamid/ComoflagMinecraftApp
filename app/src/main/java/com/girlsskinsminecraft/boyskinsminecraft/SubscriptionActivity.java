package com.girlsskinsminecraft.boyskinsminecraft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;
import com.girlsskinsminecraft.boyskinsminecraft.utils.ConstantFunctions;


public class SubscriptionActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    BillingProcessor bp;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_subscribe);
        bp = BillingProcessor.newBillingProcessor(this, ConstantFunctions.LICENSE_KEY, this);
        bp.initialize();

    }


    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("VIP", true);
        myEdit.commit();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    public void subscribe(View view) {
        bp.subscribe(SubscriptionActivity.this, ConstantFunctions.VIP_MONTH);

    }

    public void close(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
