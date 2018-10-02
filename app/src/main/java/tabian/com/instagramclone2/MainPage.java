package tabian.com.instagramclone2;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import tabian.com.instagramclone2.Login.LoginActivity;
import tabian.com.instagramclone2.Share.ShareActivity;
import tabian.com.instagramclone2.Utils.UniversalImageLoader;
import tabian.com.instagramclone2.Utils.ViewCommentsFragment;
import tabian.com.instagramclone2.databinding.ActivityCenterFabBinding;
import tabian.com.instagramclone2.fragment.NewHomeFragment;
import tabian.com.instagramclone2.fragment.NewProfileFragment;
import tabian.com.instagramclone2.models.Photo;
import tabian.com.instagramclone2.opengl.AddToStoryDialog;
import tabian.com.instagramclone2.opengl.NewStoryActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Admin on 6/5/2018.
 */

public class MainPage extends AppCompatActivity {

    private static final int RESULT_ADD_NEW_STORY = 7891;
    private final static int CAMERA_RQ = 6969;
    private static final int REQUEST_ADD_NEW_STORY = 8719;

    private static final String TAG = MainPage.class.getSimpleName();
    private ActivityCenterFabBinding bind;
    private VpAdapter adapter;

    private Context mContext = MainPage.this;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;

    // collections
    private List<Fragment> fragments;// used for ViewPager adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_with_view_pager);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_center_fab);
        mFrameLayout = (FrameLayout) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayoutParent);

        Log.d(TAG, "onCreate: starting.");
        FirebaseCrash.report(new Exception("iCodersLab.com : My first Android non-fatal error"));

        setupFirebaseAuth();

        initData();
        initView();
        initEvent();
        initImageLoader();

    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    public void onCommentThreadSelected(Photo photo, String callingActivity){
        Log.d(TAG, "onCommentThreadSelected: selected a coemment thread");

        ViewCommentsFragment fragment  = new ViewCommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo), photo);
        args.putString(getString(R.string.home_activity), getString(R.string.home_activity));
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.view_comments_fragment));
        transaction.commit();

    }

    public void openNewStoryActivity(){
        Intent intent = new Intent(this, NewStoryActivity.class);
        startActivityForResult(intent, REQUEST_ADD_NEW_STORY);
    }

    public void showAddToStoryDialog(){
        Log.d(TAG, "showAddToStoryDialog: showing add to story dialog.");
        AddToStoryDialog dialog = new AddToStoryDialog();
        dialog.show(getFragmentManager(), getString(R.string.dialog_add_to_story));
    }


    public void hideLayout(){
        Log.d(TAG, "hideLayout: hiding layout");
        mRelativeLayout.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }

    public void showLayout(){
        Log.d(TAG, "hideLayout: showing layout");
        mRelativeLayout.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFrameLayout.getVisibility() == View.VISIBLE){
            showLayout();
        }
    }




     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * checks to see if the @param 'user' is logged in
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    /**
     * create fragments
     */
    private void initData() {
        fragments = new ArrayList<>(2);

        // create music fragment and add it
        NewHomeFragment musicFragment = new NewHomeFragment();
        Bundle bundle = new Bundle();
        musicFragment.setArguments(bundle);

        // create backup fragment and add it

        // create friends fragment and add it
        NewProfileFragment profileFragment = new NewProfileFragment();
        bundle = new Bundle();
        profileFragment.setArguments(bundle);

        // create friends fragment and add it

        // add to fragments for adapter
        fragments.add(musicFragment);
        fragments.add(profileFragment);
    }


    /**
     * change BottomNavigationViewEx style
     */
    private void initView() {
        bind.bnve.enableItemShiftingMode(false);
        bind.bnve.enableShiftingMode(false);
        bind.bnve.enableAnimation(false);
        bind.bnve.setTextVisibility(false);

        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        bind.vp.setAdapter(adapter);
    }

    /**
     * set listeners
     */
    private void initEvent() {
        // set listener to change the current item of view pager when click bottom nav item
        bind.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.i_music:
                        position = 0;
                        break;
                    case R.id.i_favor:
                        position = 1;
                        break;
                    case R.id.i_empty: {
                        return false;
                    }
                }
                if(previousPosition != position) {
                    bind.vp.setCurrentItem(position, false);
                    previousPosition = position;
                    Log.i(TAG, "-----bnve-------- previous item:" + bind.bnve.getCurrentItem() + " current item:" + position + " ------------------");
                }

                return true;
            }
        });

        // set listener to change the current checked item of bottom nav when scroll view pager
        bind.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "-----ViewPager-------- previous item:" + bind.bnve.getCurrentItem() + " current item:" + position + " ------------------");
                if (position >= 2)// 2 is center
                    position++;// if page is 2, need set bottom item to 3, and the same to 3 -> 4
                bind.bnve.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // center item click listener
        bind.fab.setOnClickListener((View.OnClickListener) view -> {
            Intent i = new Intent(MainPage.this,ShareActivity.class);
            startActivity(i);
        });
    }

    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }
}
