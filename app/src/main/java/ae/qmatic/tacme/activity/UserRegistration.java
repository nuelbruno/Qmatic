package ae.qmatic.tacme.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.model.DialogItem;
import ae.qmatic.tacme.model.RegisterRequestModel;
import ae.qmatic.tacme.model.RegisterResponseModel;
import ae.qmatic.tacme.networkManager.ServiceManager;
import ae.qmatic.tacme.utils.ActivityConstant;
import ae.qmatic.tacme.utils.DialogUtils;
import ae.qmatic.tacme.utils.HttpConstants;
import ae.qmatic.tacme.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistration extends AppCompatActivity implements DialogUtils.DialogListner{

    EditText edtTextFirstName, edtTextLastName, edtTextEmail, edtTextMobileNo, edtTextPassword, edtTextConfirmPassword, edtTextCardNo;
    Button btnRegister;
    //TextView txtviewErrorfirstname, txtviewErrorLastName, txtviewErrorEmailAddress, txtviewErrorMobileNo, txtviewErrorPassword, txtviewErrorConfirmPassword, txtviewErrorCardNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("User Registration");
        ImageView imgRightIcon = (ImageView) toolbarTop.findViewById(R.id.imgRightIcon);
        imgRightIcon.setVisibility(View.GONE);

        initializeControls();
    }

    public void initializeControls(){
        edtTextFirstName = (EditText)findViewById(R.id.edtTextFirstName);
        edtTextLastName = (EditText)findViewById(R.id.edtTextLastName);
        edtTextEmail = (EditText)findViewById(R.id.edtTextEmail);
        edtTextMobileNo = (EditText)findViewById(R.id.edtTextMobileNo);
        edtTextPassword = (EditText)findViewById(R.id.edtTextPassword);
        edtTextConfirmPassword = (EditText)findViewById(R.id.edtTextConfirmPassword);
        edtTextCardNo = (EditText)findViewById(R.id.edtTextCardNo);

        /*txtviewErrorfirstname = (TextView)findViewById(R.id.txtviewErrorfirstname);
        txtviewErrorLastName = (TextView)findViewById(R.id.txtviewErrorLastName);
        txtviewErrorEmailAddress = (TextView)findViewById(R.id.txtviewErrorEmailAddress);
        txtviewErrorMobileNo = (TextView)findViewById(R.id.txtviewErrorMobileNo);
        txtviewErrorPassword = (TextView)findViewById(R.id.txtviewErrorPassword);
        txtviewErrorConfirmPassword = (TextView)findViewById(R.id.txtviewErrorConfirmPassword);
        txtviewErrorCardNo = (TextView)findViewById(R.id.txtviewErrorCardNo);*/

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(btnRegisterClick);

    }
    View.OnClickListener btnRegisterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if (isFieldsValidationSuccess()){
            if (NetworkUtils.getInstance(UserRegistration.this).isOnline())
                doUserRegistration();
            else
                Toast.makeText(UserRegistration.this, getResources().getString(R.string.err_internet), Toast.LENGTH_LONG).show();
        }
        }
    };

    public boolean isFieldsValidationSuccess(){
        //////FirstName validation
        if (edtTextFirstName.getText().toString().length() == 0) {
            //shakeObject(txtviewErrorfirstname, edtTextFirstName);
            DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_firstname_msg), UserRegistration.this);
            return false;
        } /*else
            txtviewErrorfirstname.setVisibility(View.GONE);*/

        //////LastName validation
        if (edtTextLastName.getText().toString().length() == 0) {
            //shakeObject(txtviewErrorLastName, edtTextLastName);
            DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_lasstname_msg), UserRegistration.this);
            return false;
        } /*else
            txtviewErrorLastName.setVisibility(View.GONE);*/

        //////Email Address validation : 1st Requiured Field then EMAIL FORMAT
        if (edtTextEmail.getText().toString().length() == 0) {
            //txtviewErrorEmailAddress.setText(getResources().getString(R.string.err_email_msg));
            DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_email_msg), UserRegistration.this);
            //shakeObject(txtviewErrorEmailAddress, edtTextEmail);
            return false;
        } else{
            if (!edtTextEmail.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_invalid_email), UserRegistration.this);
                /*txtviewErrorEmailAddress.setText("Please enter a valid email address");
                txtviewErrorEmailAddress.setVisibility(View.VISIBLE);*/
                return false;
            }
            /*else
                txtviewErrorEmailAddress.setVisibility(View.GONE);*/
        }


        //////Mobile Number validation
        if (edtTextMobileNo.getText().toString().length() == 0) {
            //shakeObject(txtviewErrorMobileNo, edtTextMobileNo);
            DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_mobileno_msg), UserRegistration.this);
            return false;
        } else{
            if (edtTextMobileNo.getText().toString().length() != 10){
                DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_invalid_mobileno), UserRegistration.this);
                return false;
            }
        }


        //////Password validation := Required Field Validation
        if (edtTextPassword.getText().toString().length() == 0) {
            //shakeObject(txtviewErrorPassword, edtTextPassword);
            DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_password_msg), UserRegistration.this);
            return false;
        } /*else
            txtviewErrorPassword.setVisibility(View.GONE);*/

        ////// Confirm Password validation /:::: ==  Password & Conf. Password SAME VALIDATION
        if (edtTextConfirmPassword.getText().toString().length() == 0) {
            //txtviewErrorConfirmPassword.setText(getResources().getString(R.string.err_confirm_msg));
            DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_confirm_msg), UserRegistration.this);
            //shakeObject(txtviewErrorConfirmPassword, edtTextConfirmPassword);
            return false;
        } else {
            if (!edtTextPassword.getText().toString().equalsIgnoreCase(edtTextConfirmPassword.getText().toString())) {
                //txtviewErrorConfirmPassword.setText(getResources().getString(R.string.err_pass_match_msg));
                DialogUtils.singleButtonDialog(UserRegistration.this, getResources().getString(R.string.err_pass_match_msg), UserRegistration.this);
                //shakeObject(txtviewErrorConfirmPassword, edtTextConfirmPassword);
                return false;
            }
            /*else
                txtviewErrorConfirmPassword.setVisibility(View.GONE);*/
        }

        return true;
    }
    public void doUserRegistration(){
        final ActivityConstant activityConstant = new ActivityConstant();
        activityConstant.showProgressDialog(UserRegistration.this, "Please wait...");
        RegisterRequestModel regRequestModel = new RegisterRequestModel();
        regRequestModel.setFirstName(edtTextFirstName.getText().toString());
        regRequestModel.setLastName(edtTextLastName.getText().toString());
        //regRequest.setId("");
        regRequestModel.setCardNumber(edtTextCardNo.getText().toString());

        RegisterRequestModel.PropertiesBean regProperties = new RegisterRequestModel.PropertiesBean();
        regProperties.setEmail(edtTextEmail.getText().toString());
        regProperties.setPassword(edtTextPassword.getText().toString());
        regProperties.setPhoneMobile(edtTextMobileNo.getText().toString());
        regRequestModel.setProperties(regProperties);


        ServiceManager ss = new ServiceManager();
        Call<RegisterResponseModel> call = ss.apiService.userRegistration(HttpConstants.auth2, regRequestModel);
        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                Log.e("Response", "Response" + response.code());
                if (response.body() != null && response.code() == 200) {
                    if (response.isSuccessful()) {
                        activityConstant.hideProgressDialog();

                        if (response.body().getStatus().equalsIgnoreCase(getResources().getString(R.string.service_success))){
                            Log.e("Reponse", "" + response.body().getMessage());
                            //// store User Data : UserId (CustomerId) and EmailId
                            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(UserRegistration.this).edit();
                            edit.putString("userId", response.body().getObject().getId());
                            edit.putString("emailId", response.body().getObject().getProperties().getEmail());
                            edit.apply();

                            /// to get User data
                            SharedPreferences shrPref = PreferenceManager.getDefaultSharedPreferences(UserRegistration.this);
                            String userId = shrPref.getString("userId", "user");
                            String userEmail = shrPref.getString("emailId", "email");

                            Log.d("STORED", "USER ID: " + userId);
                            Log.d("STORED", "EMAIL ID: " + userEmail);

                            startActivity(new Intent(UserRegistration.this, MainActivity.class));
                            finish();

                            Toast.makeText(UserRegistration.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            activityConstant.hideProgressDialog();
                            Log.e("Reponse", "" + response.body().getMessage());
                            DialogUtils.singleButtonDialog(UserRegistration.this, response.body().getMessage(), UserRegistration.this);
                        }
                    }
                }
                else{
                    activityConstant.hideProgressDialog();
                    Toast.makeText(UserRegistration.this, getResources().getString(R.string.webservice_error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                activityConstant.hideProgressDialog();
                Log.e("ERROR: ", "Registration"+t.toString());
            }
        });
    }
    /*public void shakeObject(TextView txtViewError, EditText edtText){
        txtViewError.setVisibility(View.VISIBLE);
        Animation shake = AnimationUtils.loadAnimation(UserRegistration.this, R.anim.shake);
        edtText.startAnimation(shake);
    }*/

    @Override
    public void mDialogSelectedType(String mMesage, int i) {}

    @Override
    public void mDialogSelectedItem(DialogItem mDialogItem, int i) {}

    @Override
    public void mDialogSelectedHashMap(HashMap mHashMap) {}
}
