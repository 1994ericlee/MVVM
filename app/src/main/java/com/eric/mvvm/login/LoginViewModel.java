package com.eric.mvvm.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eric.mvvm.base.BaseViewModel;
import com.eric.mvvm.base.ErrorBean;

public class LoginViewModel extends BaseViewModel {

    private final LoginRepository loginRepository = new LoginRepository();

    private final MutableLiveData<LoginResult> loginResultMutableLiveData = new MutableLiveData<>();

    public LiveData<LoginResult> getLoginResultLiveData() {return loginResultMutableLiveData;}
    public LiveData<ErrorBean> getErrorBeanResultLiveData() {return failed;}

    public void login(String workerId, String macAddress){
        loginRepository.setLoginResultMutableLiveData(workerId, macAddress, loginResultMutableLiveData, failed);

    }
}
