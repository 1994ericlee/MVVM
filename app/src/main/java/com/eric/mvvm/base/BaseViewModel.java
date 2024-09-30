package com.eric.mvvm.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    public MutableLiveData<ErrorBean> failed = new MutableLiveData<>();
}
