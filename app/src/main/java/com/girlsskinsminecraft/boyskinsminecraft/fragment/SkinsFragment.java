package com.girlsskinsminecraft.boyskinsminecraft.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import com.girlsskinsminecraft.boyskinsminecraft.R;
import com.girlsskinsminecraft.boyskinsminecraft.SubscriptionActivity;
import com.girlsskinsminecraft.boyskinsminecraft.adapter.SkinsAdapter;
import com.girlsskinsminecraft.boyskinsminecraft.model.Skin;
import com.girlsskinsminecraft.boyskinsminecraft.utils.BaseURL;
import com.girlsskinsminecraft.boyskinsminecraft.utils.FavoritesManager;
import com.girlsskinsminecraft.boyskinsminecraft.utils.Helpers;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


public class SkinsFragment extends BaseFragment {
    private static final String KEY_MODE = "key_mode";
    public static final int MODE_DOWNLOADS = 2;
    public static final int MODE_FAVORITE = 1;
    public static final int MODE_LATEST = 0;
    public static final int MODE_VIEWS = 3;
    private SkinsAdapter adapter;
    private DrawerLayout drawerLayout;
    private TextView tvtotal;
    private ProgressDialog mProgressDialog;
    private View mView;
    private RecyclerView recyclerView;
    private String searchStr;
    private final ArrayList<Skin> allSkinsList = new ArrayList<>();
    private final ArrayList<Skin> filteredSkinsList = new ArrayList<>();
    private boolean unsort = true;
    private ImageView vipImg;
    int total_items;
    private SkinsAdapter.OnItemSelected mOnItemSelected = new SkinsAdapter.OnItemSelected() { 
        @Override 
        public final void onItemSelected(Skin skin) {
            SkinsFragment.this.loadskins(skin);
        }
    };
    private final NavigationView.OnNavigationItemSelectedListener itemListener = new NavigationView.OnNavigationItemSelectedListener() { 
        @Override 
        public final boolean onNavigationItemSelected(MenuItem menuItem) {
            return SkinsFragment.this.m53x9fca35b(menuItem);
        }
    };

    public static SkinsFragment createInstance(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_MODE, i);
        SkinsFragment skinsFragment = new SkinsFragment();
        skinsFragment.setArguments(bundle);
        return skinsFragment;
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mView = layoutInflater.inflate(R.layout.fragment_main, viewGroup, false);
        if (getContext() == null) {
            return this.mView;
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
         total_items= Integer.parseInt(sharedPreferences.getString("total","43130"));
        tvtotal=mView.findViewById(R.id.total_name);
        vipImg=mView.findViewById(R.id.img_vip);
        tvtotal.setText(String.valueOf(total_items)+ " Skins");
        NavigationView navigationView = (NavigationView) this.mView.findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this.itemListener);
        this.drawerLayout = (DrawerLayout) this.mView.findViewById(R.id.drawer_layout);
        this.mView.findViewById(R.id.toolbar_menu).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                SkinsFragment.this.m54xd56fdc4a(view);
            }
        });
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        this.mProgressDialog = progressDialog;
        progressDialog.setMessage(getString(R.string.loading));
        this.mProgressDialog.setCancelable(false);
        this.adapter = new SkinsAdapter(this.filteredSkinsList, this.mOnItemSelected, getContext());
        RecyclerView recyclerView = (RecyclerView) this.mView.findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.recyclerView.setAdapter(this.adapter);
        this.mView.findViewById(R.id.toolbar_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SkinsFragment.this.setupSearchfun(view);
            }
        });
        vipImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SubscriptionActivity.class));
            }
        });
        initMode();
        initData();
        setupSearch(false);
        return this.mView;
    }

    
     void m54xd56fdc4a(View view) {
        if (!this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.openDrawer(GravityCompat.START);
        } else {
            this.drawerLayout.closeDrawers();
        }
    }

    
     void setupSearchfun(View view) {
        setupSearch(true);
    }

    @Override 
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.rate_app) {
            Helpers.goToStore(getContext());
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setupSearch(boolean z) {
        View findViewById = this.mView.findViewById(R.id.toolbar_search);
        View findViewById2 = this.mView.findViewById(R.id.toolbar_name);
        SearchView searchView = (SearchView) this.mView.findViewById(R.id.search);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() { 
            @Override 
            public final boolean onClose() {
                return SkinsFragment.this.m56x411aeefd();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { 
            @Override 
            public boolean onQueryTextSubmit(String str) {
                return false;
            }

            @Override 
            public boolean onQueryTextChange(String str) {
                SkinsFragment.this.searchStr = str;
                SkinsFragment.this.filterData();
                return false;
            }
        });
        findViewById.setVisibility(z ? View.GONE : View.VISIBLE);
        findViewById2.setVisibility(z ? View.GONE : View.VISIBLE);
        searchView.setVisibility(z ? View.VISIBLE : View.GONE);
        if (z) {
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
        }
    }

    
     boolean m56x411aeefd() {
        setupSearch(false);
        return false;
    }

    private void descendingSorting(ArrayList<Skin> arrayList) {
        Collections.sort(arrayList, new Comparator() { 
            @Override 
            public final int compare(Object obj, Object obj2) {
                return SkinsFragment.this.m47xc5e44515((Skin) obj, (Skin) obj2);
            }
        });
    }

    
     int m47xc5e44515(Skin skin, Skin skin2) {
        boolean z = this.unsort;
        Skin skin3 = z ? skin : skin2;
        if (z) {
            skin = skin2;
        }
        if (skin3.isNumber() && skin.isNumber()) {
            return Integer.compare(skin.getIntName(), skin3.getIntName());
        }
        if (skin3.isNumber() || skin.isNumber()) {
            return (skin3.isNumber() || !skin.isNumber()) ? 1 : -1;
        }
        return skin.getName().compareToIgnoreCase(skin3.getName());
    }

    private void initMode() {
        TextView textView = (TextView) this.mView.findViewById(R.id.button_latest);
        textView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                SkinsFragment.this.showlatestskins(view);
            }
        });
        int mode = getMode();
        setCheckedState(textView, mode == 0);
    }

    
     void showlatestskins(View view) {
        this.mainManager.setScreen(createInstance(0));
    }

    private void initData() {
        this.allSkinsList.clear();
        Context context = getContext();

        int mode = getMode();
        for (int i = 0; i <= total_items-1; i++) {
            Skin skin = new Skin(i);
            if (mode != 0) {
                if (mode != 1) {
                    if (mode != 2 && mode != 3) {
                    }
                } else if (FavoritesManager.getInstance().isFavorite(context, skin.getNumber())) {
                    this.allSkinsList.add(skin);
                }
            }
            this.allSkinsList.add(skin);
        }
        filterData();
    }

    
    public void filterData() {
        this.filteredSkinsList.clear();
        String string = getString(R.string.skin_name_formatted);
        Iterator<Skin> it = this.allSkinsList.iterator();
        while (it.hasNext()) {
            Skin next = it.next();
            if (containsIgnoreCase(String.format(string, next.getName()), this.searchStr)) {
                this.filteredSkinsList.add(next);
            }
        }
        descendingSorting(this.filteredSkinsList);
        this.adapter.notifyDataSetChanged();
    }

    private int getMode() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return 0;
        }
        return arguments.getInt(KEY_MODE, 0);
    }

    private void setCheckedState(TextView textView, boolean z) {
        if (getContext() == null) {
            return;
        }
        textView.setTextColor(ContextCompat.getColor(getContext(), z ? R.color.black : R.color.white));
        textView.setBackgroundResource(z ? R.drawable.background_tab_button : 0);
    }

    private static boolean containsIgnoreCase(String str, String str2) {
        int length;
        if (str == null) {
            return false;
        }
        if (str2 == null || (length = str2.length()) == 0) {
            return true;
        }
        for (int length2 = str.length() - length; length2 >= 0; length2--) {
            if (str.regionMatches(true, length2, str2, 0, length)) {
                return true;
            }
        }
        return false;
    }

    
    @SuppressLint("ResourceType")
    public void showAlert(int i) {
        if (getContext() == null) {
            return;
        }
        new AlertDialog.Builder(getContext()).setMessage(i).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).show();
    }

    
     void loadskins(Skin skin) {
        if (!Helpers.isNetworkAvailable(getContext())) {
            showAlert(R.string.no_internet_connection);
        } else {
            new LoadImageTask(skin, getContext().getCacheDir()).execute(new Void[0]);
        }
    }

    
    private class LoadImageTask extends AsyncTask<Void, Void, Boolean> {
        private File localPath;
        private Skin skin;

        LoadImageTask(Skin skin, File file) {
            this.skin = skin;
            this.localPath = file;
        }

        @Override 
        protected void onPreExecute() {
            SkinsFragment.this.mProgressDialog.show();
        }

        
        @Override 
        public Boolean doInBackground(Void... voidArr) {
            File file = new File(this.localPath, String.format("%s.png", this.skin.getNumber()));
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(BaseURL.createSkinPath(this.skin.getNumber())).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = bufferedInputStream.read(bArr, 0, 1024);
                        if (read != -1) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            fileOutputStream.close();
                            bufferedInputStream.close();
                            return true;
                        }
                    }
                } catch (Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException unused) {
                return false;
            }
        }

        
        @Override 
        public void onPostExecute(Boolean bool) {
            if (SkinsFragment.this.mProgressDialog.isShowing()) {
                SkinsFragment.this.mProgressDialog.dismiss();
            }
            if (!bool.booleanValue()) {
                SkinsFragment.this.showAlert(R.string.skin_loading_error);
            } else {
                SkinsFragment.this.mainManager.addScreen(SkinDetailsFragment.createInstance(this.skin));
            }
        }
    }

    boolean m53x9fca35b(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_favorite :
                this.mainManager.setScreen(createInstance(1));
                break;
            case R.id.action_policy :
                this.mainManager.addScreen(new PolicyFragment());
                break;
            case R.id.action_rate :
                Helpers.goToStore(getContext());
                break;
            case R.id.share_app :
                Helpers.shareApp(getContext());
                break;
            case R.id.moreskins :
                Helpers.goToDevPage(getContext());
                break;
            case R.id.removeads:
                startActivity(new Intent(getContext(), SubscriptionActivity.class));
                break;
        }
        this.drawerLayout.closeDrawers();
        return true;
    }
}
