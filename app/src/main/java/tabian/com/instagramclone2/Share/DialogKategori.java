package tabian.com.instagramclone2.Share;

import android.animation.ObjectAnimator;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tabian.com.instagramclone2.R;
import tabian.com.instagramclone2.Share.shareAdapter.KategoriAdapter;
import tabian.com.instagramclone2.Utils.FabTransform;
import tabian.com.instagramclone2.Utils.MorphTransform;

/**
 * Created by Admin on 6/8/2018.
 */

public class DialogKategori extends AppCompatActivity {
    boolean isDismissing = false;
    boolean isLoginFailed = false;

    ViewGroup container;

    LinearLayout ll_kategori;

    LinearLayout btnBangunan;
    TextView tvBangun;

    LinearLayout llFasilitas;
    TextView tvFasilitas;

    LinearLayout llJalan;
    TextView tvJalan;


    private KategoriAdapter kateadapter;

    /*
    private List<CategoryItem> data_menu() {

        List<CategoryItem> menu = new ArrayList<>();
        //menu.add(new CategoryItem("Fasilitas",R.drawable.cat_fasilitas));
        menu.add(new CategoryItem("Bangunan",R.drawable.cat_bangunan));
        //menu.add(new CategoryItem("Jalanan",R.drawable.cat_jalan));
        //menu.add(new CategoryItem("Lahan Parkir",R.drawable.cat_parkir));


        return menu;
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_kategori);
        //ButterKnife.bind(this);
        llFasilitas = (LinearLayout)findViewById(R.id.ll_fasilitas);
        tvFasilitas = (TextView)findViewById(R.id.tv_fasilitas);
        btnBangunan = (LinearLayout)findViewById(R.id.btn_bangunan);
        tvBangun = (TextView)findViewById(R.id.tv_bangun); 
        ll_kategori = (LinearLayout)findViewById(R.id.ll_kategories);
        llJalan = (LinearLayout)findViewById(R.id.ll_jalan);
        tvJalan = (TextView)findViewById(R.id.tv_jalan);

        ViewGroup container = (ViewGroup)findViewById(R.id.container);

        if (!FabTransform.setup(DialogKategori.this, container)) {
            MorphTransform.setup(DialogKategori.this, container,
                    ContextCompat.getColor(DialogKategori.this, R.color.trans),
                    getResources().getDimensionPixelSize(R.dimen.dialog_corners_c));
        }

        /*
        final List<CategoryItem> menus = data_menu();
        rv_icon.setHasFixedSize(true);
        KategoriAdapter kateadapter = new KategoriAdapter(menus, this);
        rv_icon.setAdapter(kateadapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_icon.setLayoutManager(layoutManager);
        */


        btnBangunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringToPassBack = tvBangun.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("keyname",stringToPassBack);
                setResult(RESULT_OK, returnIntent);
                dismiss(null);
            }
        });

        llFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringToPassBack = tvFasilitas.getText().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("keyname",stringToPassBack);
                setResult(RESULT_OK, returnIntent);
                dismiss(null);
            }
        });

        llJalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringToPassBack = tvJalan.getText().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("keyname",stringToPassBack);
                setResult(RESULT_OK, returnIntent);
                dismiss(null);
            }
        });
    }

    public void dismiss(View view) {
        isDismissing = true;
        ll_kategori.setVisibility(View.GONE);
        finishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast));

        TextView text = (TextView) layout.findViewById(R.id.tv_toast);
        text.setText("Pilih salah satu kategori");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.TOP, 0, 32);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void forceSharedElementLayout() {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(container.getWidth(),
                View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(container.getHeight(),
                View.MeasureSpec.EXACTLY);
        container.measure(widthSpec, heightSpec);
        container.layout(container.getLeft(), container.getTop(), container.getRight(), container
                .getBottom());
    }

    private SharedElementCallback sharedElementEnterCallback = new SharedElementCallback() {
        @Override
        public View onCreateSnapshotView(Context context, Parcelable snapshot) {
            // grab the saved fab snapshot and pass it to the below via a View
            View view = new View(context);
            final Bitmap snapshotBitmap = getSnapshot(snapshot);
            if (snapshotBitmap != null) {
                view.setBackground(new BitmapDrawable(context.getResources(), snapshotBitmap));
            }
            return view;
        }

        @Override
        public void onSharedElementStart(List<String> sharedElementNames,
                                         List<View> sharedElements,
                                         List<View> sharedElementSnapshots) {
            // grab the fab snapshot and fade it out/in (depending on if we are entering or exiting)
            for (int i = 0; i < sharedElements.size(); i++) {
                if (sharedElements.get(i) == container) {
                    View snapshot = sharedElementSnapshots.get(i);
                    BitmapDrawable fabSnapshot = (BitmapDrawable) snapshot.getBackground();
                    fabSnapshot.setBounds(0, 0, snapshot.getWidth(), snapshot.getHeight());
                    container.getOverlay().clear();
                    container.getOverlay().add(fabSnapshot);
                    if (!isDismissing) {
                        // fab -> login: fade out the fab snapshot
                        ObjectAnimator.ofInt(fabSnapshot, "alpha", 0).setDuration(100).start();
                    } else {
                        // login -> fab: fade in the fab snapshot toward the end of the transition
                        fabSnapshot.setAlpha(0);
                        ObjectAnimator fadeIn = ObjectAnimator.ofInt(fabSnapshot, "alpha", 255)
                                .setDuration(150);
                        fadeIn.setStartDelay(150);
                        fadeIn.start();
                    }
                    forceSharedElementLayout();
                    break;
                }
            }
        }

        private Bitmap getSnapshot(Parcelable parcel) {
            if (parcel instanceof Bitmap) {
                return (Bitmap) parcel;
            } else if (parcel instanceof Bundle) {
                Bundle bundle = (Bundle) parcel;
                // see SharedElementCallback#onCaptureSharedElementSnapshot
                return (Bitmap) bundle.getParcelable("sharedElement:snapshot:bitmap");
            }
            return null;
        }
    };
}

