package com.girlsskinsminecraft.boyskinsminecraft.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.girlsskinsminecraft.boyskinsminecraft.R;


public class SavedToGalleryDialog extends DialogFragment {
    private OnFinishListener listener;

    
    public interface OnFinishListener {
        void onFinish();
    }

    public static SavedToGalleryDialog createDialog(OnFinishListener onFinishListener) {
        SavedToGalleryDialog savedToGalleryDialog = new SavedToGalleryDialog();
        savedToGalleryDialog.listener = onFinishListener;
        return savedToGalleryDialog;
    }

    @Override 
    public Dialog onCreateDialog(Bundle bundle) {
        Context context = getContext();
        if (context == null) {
            return super.onCreateDialog(bundle);
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_skin_save, (ViewGroup) null, false);
        inflate.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                SavedToGalleryDialog.this.dismiss(view);
            }
        });
        return new AlertDialog.Builder(context).setView(inflate).create();
    }

    
     void dismiss(View view) {
        dismiss();
    }

    @Override 
    public void onDismiss(DialogInterface dialogInterface) {
        OnFinishListener onFinishListener = this.listener;
        if (onFinishListener != null) {
            onFinishListener.onFinish();
        }
    }
}
