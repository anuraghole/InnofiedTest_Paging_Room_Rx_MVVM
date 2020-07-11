package com.example.innofiedtest.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.innofiedtest.paging.InnofiedDataSource;
import com.example.innofiedtest.paging.InnofiedDataSourceFactory;
import com.example.innofiedtest.model.User;
import com.example.innofiedtest.repository.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends AndroidViewModel {
    private InnofiedDataSourceFactory innofiedDataSourceFactory;
    private LiveData<PagedList<User>> pageListLiveData;
    private Repository repository;
    private LiveData<String> progressLoadStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public MainActivityViewModel(Application application) {
        super(application);
        this.repository = new Repository();
        innofiedDataSourceFactory = new InnofiedDataSourceFactory(repository, compositeDisposable);
        initializePaging();
    }

    public void initializePaging() {

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(5)
                        .setPageSize(5).build();

        pageListLiveData = new LivePagedListBuilder<>(innofiedDataSourceFactory, pagedListConfig)
                .build();

        progressLoadStatus = Transformations.switchMap(innofiedDataSourceFactory.getMutableLiveData(), InnofiedDataSource::getProgressLiveStatus);

    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<User>> getPageListLiveData() {
        return pageListLiveData;
    }

    public void onPageRefresh() {
        if (innofiedDataSourceFactory.getMutableLiveData().getValue() != null)
            innofiedDataSourceFactory.getMutableLiveData().getValue().invalidate();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

}
