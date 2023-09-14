package com.girlsskinsminecraft.boyskinsminecraft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.girlsskinsminecraft.boyskinsminecraft.R;
import com.girlsskinsminecraft.boyskinsminecraft.model.Skin;
import com.girlsskinsminecraft.boyskinsminecraft.utils.BaseURL;
import com.girlsskinsminecraft.boyskinsminecraft.utils.ConstantFunctions;
import java.util.ArrayList;


public class SkinsAdapter extends RecyclerView.Adapter<SkinsAdapter.ViewHolder> {
    private Context context;
    private OnItemSelected listener;
    private ArrayList<Skin> originalList;




    public interface OnItemSelected {
        void onItemSelected(Skin skin);
    }

    public SkinsAdapter(ArrayList<Skin> arrayList, OnItemSelected onItemSelected, Context context) {
        this.originalList = arrayList;
        this.listener = onItemSelected;
        this.context = context;
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_skinlist_item, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Skin skin = this.originalList.get(i);
        String createThumbnailPath = BaseURL.createThumbnailPath(skin.getNumber());
        viewHolder.skinName.setText(String.format(this.context.getString(R.string.skin_name_formatted), skin.getName()));
        viewHolder.viewsCount.setText("123456");
        viewHolder.downloadsCount.setText("12345");
        ConstantFunctions.loadImage(createThumbnailPath, viewHolder.skinImage);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                SkinsAdapter.this.onItemSelectedfun(skin, view);
            }
        });
        viewHolder.bottomBackground.setVisibility(View.GONE);
    }

    
     void onItemSelectedfun(Skin skin, View view) {
        OnItemSelected onItemSelected = this.listener;
        if (onItemSelected != null) {
            onItemSelected.onItemSelected(skin);
        }
    }

    @Override 
    public int getItemCount() {
        ArrayList<Skin> arrayList = this.originalList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    
    class ViewHolder extends RecyclerView.ViewHolder {
        View bottomBackground;
        TextView downloadsCount;
        ImageView skinImage;
        TextView skinName;
        TextView viewsCount;

        ViewHolder(View view) {
            super(view);
            this.skinName = (TextView) view.findViewById(R.id.skin_name);
            this.skinImage = (ImageView) view.findViewById(R.id.skinImage);
            this.viewsCount = (TextView) view.findViewById(R.id.views_count);
            this.downloadsCount = (TextView) view.findViewById(R.id.downloads_count);
            this.bottomBackground = view.findViewById(R.id.bottom_background);
        }
    }
}
