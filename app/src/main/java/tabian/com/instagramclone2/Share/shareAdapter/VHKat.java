package tabian.com.instagramclone2.Share.shareAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tabian.com.instagramclone2.R;
import tabian.com.instagramclone2.models.CategoryItem;

public class VHKat extends RecyclerView.ViewHolder {

    public String mBoundString;

    TextView title, harga,mins;
    ImageView img_icon;

    public CategoryItem menuss;

    public VHKat(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.tv_title);
        img_icon = (ImageView) itemView.findViewById(R.id.iv_icon);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), DetailMenu.class);
                //v.getContext().startActivity(intent);
                //Bundle extras = new Bundle();
                //extras.putInt("position",getAdapterPosit ion());
                //intent.putExtras(extras);
            }
        });


        itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(v.getContext(), "Kamu Long Click " + getPosition(), Toast.LENGTH_SHORT).show();

                return true;

            }
        });
    }
}


