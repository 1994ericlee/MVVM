package com.eric.mvvm.login;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.eric.mvvm.GlobalData;
import com.eric.mvvm.R;
import com.eric.mvvm.base.BaseActivity;
import com.eric.mvvm.base.ErrorBean;
import com.eric.mvvm.base.ErrorType;
import com.eric.mvvm.databinding.ActivityLoginBinding;
import com.eric.mvvm.utils.DialogUtil;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginTitle.setText(R.string.login_title);
        binding.loginWorkIDTitle.setText(R.string.login_worker_id_title);
        binding.loginButton.setText(R.string.login_button);

        binding.loginButton.setOnClickListener(view -> login());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getLoginResultLiveData().observe(this, this::loginResult);
        loginViewModel.getErrorBeanResultLiveData().observe(this, this::showError);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.loginWorkIDInput.setText("P56");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loginResult(LoginResult loginResult) {
        if (loginResult.isLogin()){
            DialogUtil.showDialog(this,"Login", "Login Success!");
        }else {
            Log.e(TAG,"login error: ");
        }
    }

    private void login(){
        loginViewModel.login(GlobalData.workerID, GlobalData.macAddress);
    }
    private void showError(ErrorBean errorBean) {
        if (errorBean.getErrorType().equals(ErrorType.LOGIN)){
            DialogUtil.showDialog(this,"Login Error", errorBean.getErrorMsg());
        }
    }
}
