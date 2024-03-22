package com.scanner.hardware.expandable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.scanner.hardware.MyApplication;
import com.scanner.hardware.db.ScannerData;

import kotlinx.coroutines.CoroutineScope;

public class LoadMoreViewModel extends ViewModel {
    PagingConfig pagingConfig = new PagingConfig(10,// 每页显示的数据的大小。
            10,  // 预刷新的距离，距离最后一个 item 多远时加载数据，默认为 pageSize
            true, //是否启用占位符
            30 //初始化加载数量，默认为 pageSize * 3
    );

    public LiveData<PagingData<ScannerData>> getPaging() {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, ScannerData> pager = new Pager<>(pagingConfig, () -> MyApplication.dao.selectAllData());
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }
}
