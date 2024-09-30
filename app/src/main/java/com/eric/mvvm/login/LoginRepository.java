package com.eric.mvvm.login;

import androidx.lifecycle.MutableLiveData;

import com.eric.mvvm.ApiService;
import com.eric.mvvm.NetworkApi;
import com.eric.mvvm.base.BaseObserver;
import com.eric.mvvm.base.ErrorBean;
import com.eric.mvvm.base.ErrorType;

import org.json.JSONObject;

import okhttp3.ResponseBody;

public class LoginRepository {

    public void setLoginResultMutableLiveData(String workerId, String macAddress, MutableLiveData<LoginResult> loginResultMutableLiveData,
                                              MutableLiveData<ErrorBean> failedLiveData){
        ErrorBean errorBean = new ErrorBean();
        errorBean.setErrorType(ErrorType.LOGIN);

        NetworkApi.createService(ApiService.class)
                .login(workerId, macAddress)
                .compose(NetworkApi.applySchedulers())
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            String response = responseBody.string();
                            response = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                            JSONObject jsonObject = new JSONObject(response);

                            boolean isLogin = jsonObject.getBoolean("login_result");
                            if (isLogin) {

                                LoginResult result = new LoginResult();
                                result.setLogin(true);

                                loginResultMutableLiveData.setValue(result);
                            } else {
                                String msg = jsonObject.getString("msg");
                                errorBean.setErrorMsg(msg);
                                failedLiveData.setValue(errorBean);

                            }
                        }catch (Exception e) {
                            errorBean.setErrorMsg(e.toString());
                            failedLiveData.setValue(errorBean);
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(Throwable e) {
                        errorBean.setErrorMsg(e.toString());
                        failedLiveData.setValue(errorBean);
                    }
                });
    }
}
