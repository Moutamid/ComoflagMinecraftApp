package com.girlsskinsminecraft.boyskinsminecraft.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.girlsskinsminecraft.boyskinsminecraft.R;


public class ConfirmDialog extends DialogFragment {
    private OnFinishListener listener;

    public interface OnFinishListener {
        void onFinish();
    }

    public static ConfirmDialog createDialog(OnFinishListener onFinishListener) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.listener = onFinishListener;
        return confirmDialog;
    }

    @Override 
    public Dialog onCreateDialog(Bundle bundle) {
        Context context = getContext();
        if (context == null) {
            return super.onCreateDialog(bundle);
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, (ViewGroup) null, false);
        inflate.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {

                ConfirmDialog.this.onFinishListenerFun(view);
            }
        });
        inflate.findViewById(R.id.no_button).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                ConfirmDialog.this.dimisfun(view);
            }
        });
        return new AlertDialog.Builder(context).setView(inflate).create();
    }

     void onFinishListenerFun(View view) {
        OnFinishListener onFinishListener = this.listener;
        if (onFinishListener != null) {
            onFinishListener.onFinish();
        }
        dismiss();
    }

     void dimisfun(View view) {
        dismiss();
    }
}
