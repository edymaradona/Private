package tabian.com.instagramclone2.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import tabian.com.instagramclone2.Home.HomeActivity;
import tabian.com.instagramclone2.MainPage;
import tabian.com.instagramclone2.R;

/**
 * Created by User on 6/19/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final Boolean CHECK_IF_VERIFIED = false;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    //private TextView mPleaseWait;

    public static final int SNACKBAR_DURATION = 2000;

    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        //mPleaseWait = (TextView) findViewById(R.id.pleaseWait);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mContext = LoginActivity.this;
        Log.d(TAG, "onCreate: started.");

        //mPleaseWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        setupFirebaseAuth();
        init();

    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

    private void snackbarCustom() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "You must fill out all the fields \n\n",SNACKBAR_DURATION);

        View snackView = snackbar.getView();
        snackView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        TextView snackViewText = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/ProductSansRegular.ttf");
        snackViewText.setTypeface(font);
        snackViewText.setTextColor(ContextCompat.getColor(this, R.color.black));
        Button snackViewButton = (Button) snackView.findViewById(android.support.design.R.id.snackbar_action);
        snackViewButton.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        // You can replace the above lines by using the below single line :)
        //snackbar=SnackBarUtil.getColorfulSnackBar(snackbar,this,R.color.snackBarBg,R.color.colorWhite,R.color.colorAccent);

        snackbar.show();
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

     private void init(){

         //initialize the button for logging in
         LinearLayout btnLogin = (LinearLayout) findViewById(R.id.login_btn);
         btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.d(TAG, "onClick: attempting to log in.");

                 String email = mEmail.getText().toString();
                 String password = mPassword.getText().toString();

                 if(isStringNull(email) && isStringNull(password)){
                     //Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                     snackbarCustom();
                 }else{
                     mProgressBar.setVisibility(View.VISIBLE);
                     //mPleaseWait.setVisibility(View.VISIBLE);

                     mAuth.signInWithEmailAndPassword(email, password)
                             .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                     FirebaseUser user = mAuth.getCurrentUser();

                                     // If sign in fails, display a message to the user. If sign in succeeds
                                     // the auth state listener will be notified and logic to handle the
                                     // signed in user can be handled in the listener.
                                     if (!task.isSuccessful()) {
                                         Log.w(TAG, "signInWithEmail:failed", task.getException());

                                         //Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                         //        Toast.LENGTH_SHORT).show();
                                         mProgressBar.setVisibility(View.GONE);

                                         Snackbar snackbar = Snackbar
                                                 .make(coordinatorLayout, "Failed to Authenticate \n\n",SNACKBAR_DURATION);

                                         View snackView = snackbar.getView();
                                         snackView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colordarkRed));
                                         TextView snackViewText = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
                                         Typeface font = Typeface.createFromAsset(LoginActivity.this.getAssets(), "fonts/ProductSansRegular.ttf");
                                         snackViewText.setTypeface(font);
                                         snackViewText.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                                         Button snackViewButton = (Button) snackView.findViewById(android.support.design.R.id.snackbar_action);
                                         snackViewButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));

                                         // You can replace the above lines by using the below single line :)
                                         //snackbar=SnackBarUtil.getColorfulSnackBar(snackbar,this,R.color.snackBarBg,R.color.colorWhite,R.color.colorAccent);

                                         snackbar.show();
                                         //mPleaseWait.setVisibility(View.GONE);
                                     }
                                     else{
                                         try{
                                             if(CHECK_IF_VERIFIED){
                                                 if(user.isEmailVerified()){
                                                     Log.d(TAG, "onComplete: success. email is verified.");
                                                     Intent intent = new Intent(LoginActivity.this, MainPage.class);
                                                     startActivity(intent);
                                                 }else{
                                                     Toast.makeText(mContext, "Email is not verified \n check your email inbox.", Toast.LENGTH_SHORT).show();
                                                     mProgressBar.setVisibility(View.GONE);
                                                     //mPleaseWait.setVisibility(View.GONE);
                                                     mAuth.signOut();
                                                 }
                                             }
                                             else{
                                                 Log.d(TAG, "onComplete: success. email is verified.");
                                                 Intent intent = new Intent(LoginActivity.this, MainPage.class);
                                                 startActivity(intent);
                                             }

                                         }catch (NullPointerException e){
                                             Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage() );
                                         }
                                     }

                                     // ...
                                 }
                             });
                 }

             }
         });

         CardView linkSignUp = (CardView) findViewById(R.id.btn_sign_up);
         linkSignUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.d(TAG, "onClick: navigating to register screen");
                 Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                 startActivity(intent);
             }
         });

         /*
         If the user is logged in then navigate to HomeActivity and call 'finish()'
          */
         if(mAuth.getCurrentUser() != null){
             Intent intent = new Intent(LoginActivity.this, MainPage.class);
             startActivity(intent);
             finish();
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
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
























