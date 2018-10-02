package tabian.com.instagramclone2.Share.shareAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import tabian.com.instagramclone2.R;
import tabian.com.instagramclone2.models.CategoryItem;


/**
 * Created by Admin on 6/8/2018.
 */

public class KategoriAdapter extends RecyclerView.Adapter<VHKat> {

    List<CategoryItem> list = Collections.emptyList();
    Context context;

    public KategoriAdapter(List<CategoryItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public VHKat onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        VHKat holder = new VHKat(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(VHKat holder, int position) {
        holder.title.setText(list.get(position).title);
        holder.img_icon.setImageResource(list.get(position).imageId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Insert a new item to the RecyclerView
    public void insert(int position, CategoryItem menuss) {
        list.add(position, menuss);
        notifyItemInserted(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
