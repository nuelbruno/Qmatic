package ae.qmatic.tacme.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.model.DialogItem;
import ae.qmatic.tacme.model.ForgotPasswordModel;
import ae.qmatic.tacme.model.UserLoginModel;
import ae.qmatic.tacme.networkManager.ServiceManager;
import ae.qmatic.tacme.utils.ActivityConstant;
import ae.qmatic.tacme.utils.DialogUtils;
import ae.qmatic.tacme.utils.HttpConstants;
import ae.qmatic.tacme.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mdev3 on 8/10/16.
 */
public class LoginActivity extends AppCompatActivity implements DialogUtils.DialogListner{
    private EditText mUserNameText, mPassword;
    private Button mSigninButton;
    private TextView mForgotPasswordView, mRegisterView;
    Dialog dialogForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        initializeUI();
        setListeners();
    }

    private void initializeUI() {
        mUserNameText = (EditText) findViewById(R.id.editText_username);
        mPassword = (EditText) findViewById(R.id.editText_Password);
        mSigninButton = (Button) findViewById(R.id.SigninButton);
        mForgotPasswordView = (TextView) findViewById(R.id.ForgotPasswordView);
        mRegisterView = (TextView) findViewById(R.id.RegisterView);
    }

    private void setListeners() {
        mSigninButton.setOnClickListener(onClickListner);
        mForgotPasswordView.setOnClickListener(onClickListner);
        mRegisterView.setOnClickListener(onClickListner);
        mForgotPasswordView.setPaintFlags(mForgotPasswordView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mRegisterView.setPaintFlags(mRegisterView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private boolean isPrimaryValidationSuccess() {

        if (mUserNameText.getText().toString().length() == 0) {
            //txtviewErrorEmailAddress.setVisibility(View.VISIBLE);
            //txtviewErrorEmailAddress.setText("Please enter your Email address.");
            //Toast.makeText(LoginActivity.this, "Please enter Username", Toast.LENGTH_SHORT).show();
            DialogUtils.singleButtonDialog(LoginActivity.this, "Please enter Username", LoginActivity.this);
            return false;
        }
        else {
            if (!mUserNameText.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                DialogUtils.singleButtonDialog(LoginActivity.this, "Please enter a valid email address", LoginActivity.this);
                return false;
            }
        }

        if (mPassword.getText().toString().length() == 0) {
            //txtviewErrorEmailAddress.setVisibility(View.VISIBLE);
            //txtviewErrorEmailAddress.setText("Please enter your Email address.");
            //Toast.makeText(LoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
            DialogUtils.singleButtonDialog(LoginActivity.this, "Please enter Password", LoginActivity.this);
            return false;
        }

        return true;
    }

    View.OnClickListener onClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.SigninButton: {


                    if (isPrimaryValidationSuccess())
                        if (NetworkUtils.getInstance(LoginActivity.this).isOnline())
                            checkUserLogin();
                        else
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.err_internet), Toast.LENGTH_LONG).show();


                }
                break;
                case R.id.ForgotPasswordView: {
                    dialogForgotPassword();
                }
                break;
                case R.id.RegisterView: {
                    startActivity(new Intent(LoginActivity.this, UserRegistration.class));
                }
                break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 1) {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
    }

    public void checkUserLogin(){
        final ActivityConstant activityConstant = new ActivityConstant();
        //activityConstant.showProgressDialog(LoginActivity.this, "Logging...");
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                activityConstant.hideProgressDialog();
                Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityForResult(mIntent, 1);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        }, 100);
        //return;
//        ServiceManager ss = new ServiceManager();
//        Log.d("User: " + mUserNameText.getText().toString(), "Password: " + mPassword.getText().toString());
//        Call<UserLoginModel> call = ss.apiService.userLogin(HttpConstants.auth2, mUserNameText.getText().toString(), mPassword.getText().toString());
//        call.enqueue(new Callback<UserLoginModel>() {
//            @Override
//            public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
//                Log.e("Response", "Response" + response.code());
//                if (response.body() != null && response.code() == 200) {
//                    if (response.isSuccessful()) {
//                        if (response.body().getStatus().equalsIgnoreCase(getResources().getString(R.string.service_success))){
//                            Log.e("Reponse", "" + response.body().getMessage());
//
//                            //// store User Data : UserId (CustomerId) and EmailId
//                            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
//                            edit.putString("userId", response.body().getObject().getProperties().getCustomerId());
//                            edit.putString("emailId", response.body().getObject().getProperties().getEmail());
//                            edit.apply();
//
//                            /// to get User data
//                            SharedPreferences shrPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
//                            String userId = shrPref.getString("userId", "user");
//                            String userEmail = shrPref.getString("emailId", "email");
//
//                            Log.d("STORED", "USER ID: " + userId);
//                            Log.d("STORED", "EMAIL ID: " + userEmail);
//
//                            Handler h = new Handler();
//                            h.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    activityConstant.hideProgressDialog();
//                                    Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
//                                    startActivityForResult(mIntent, 1);
//                                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
//                                }
//                            }, 1000);
//                        }
//                        else {
//                            activityConstant.hideProgressDialog();
//                            Log.e("Reponse", "" + response.body().getMessage());
//                            DialogUtils.singleButtonDialog(LoginActivity.this, response.body().getMessage(), LoginActivity.this);
//                        }
//                    }
//                }
//                else
//                {
//                    activityConstant.hideProgressDialog();
//                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.webservice_error), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserLoginModel> call, Throwable t) {
//                activityConstant.hideProgressDialog();
//                Log.e("ERROR: ", "USER LOGIN"+t.toString());
//            }
//        });
    }
    @Override
    public void mDialogSelectedType(String mMesage, int i) {}


    public void dialogForgotPassword(){
        DisplayMetrics metrics = new DisplayMetrics();
        LoginActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = (int) (metrics.heightPixels * 0.9);
        dialogForgotPassword = new Dialog(LoginActivity.this);
        dialogForgotPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForgotPassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogForgotPassword.setContentView(R.layout.forgot_password);
        dialogForgotPassword.setCanceledOnTouchOutside(true);
        dialogForgotPassword.setCancelable(true);
        dialogForgotPassword.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialogForgotPassword.getWindow().setGravity(Gravity.CENTER);

        ////// Access required UI controls.
        final EditText editText_userEmail = (EditText)dialogForgotPassword.findViewById(R.id.editText_userEmail);
        Button btnForgotPassword = (Button)dialogForgotPassword.findViewById(R.id.btnForgotPassword);
        //final TextView txtviewErrorEmailAddress = (TextView)dialogForgotPassword.findViewById(R.id.txtviewErrorEmailAddress);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_userEmail.getText().toString().length() == 0) {
                    DialogUtils.singleButtonDialog(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.err_email_msg), LoginActivity.this);
                    return;
                }

                if (!editText_userEmail.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    DialogUtils.singleButtonDialog(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.err_invalid_email), LoginActivity.this);
                }
                else {
                    if (NetworkUtils.getInstance(LoginActivity.this).isOnline())
                        forgotPassword(editText_userEmail.getText().toString());
                    else
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.err_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogForgotPassword.show();
    }
    public void forgotPassword(String userEmailAddress){
        final ActivityConstant activityConstant = new ActivityConstant();
        activityConstant.showProgressDialog(LoginActivity.this, "Please wait...");
        Log.d("FORGOT PASSWORD", "EMAIL: "+userEmailAddress);
        ServiceManager ss = new ServiceManager();
        Call<ForgotPasswordModel> call = ss.apiService.forgotUserPassword(HttpConstants.auth2, userEmailAddress);
        call.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                Log.e("Response", "Response" + response.code());
                if (response.body() != null && response.code() == 200) {
                    if (response.isSuccessful()) {
                        activityConstant.hideProgressDialog();
                        if (response.body().getStatus().equalsIgnoreCase(getResources().getString(R.string.service_success))){
                            DialogUtils.singleButtonDialog(LoginActivity.this, response.body().getMessage(), LoginActivity.this);
                            dialogForgotPassword.hide();
                        }
                        else {
                            Log.e("Reponse", "" + response.body().getMessage());
                            DialogUtils.singleButtonDialog(LoginActivity.this, response.body().getMessage(), LoginActivity.this);
                        }
                    }
                }
                else{
                    activityConstant.hideProgressDialog();
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.webservice_error), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                activityConstant.hideProgressDialog();
                Log.e("ERROR: ", "FORGOT PASSWORD" + t.toString());
            }
        });
    }
    @Override
    public void mDialogSelectedItem(DialogItem mDialogItem, int i) {}

    @Override
    public void mDialogSelectedHashMap(HashMap mHashMap) {}
}
