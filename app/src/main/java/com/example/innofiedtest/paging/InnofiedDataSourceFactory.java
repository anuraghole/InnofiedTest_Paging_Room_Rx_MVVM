package com.example.innofiedtest.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.innofiedtest.model.User;
import com.example.innofiedtest.repository.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class InnofiedDataSourceFactory extends DataSource.Factory<Integer, User> {

    private MutableLiveData<InnofiedDataSource> liveData;
    private Repository repository;
    private CompositeDisposable compositeDisposable;

    public InnofiedDataSourceFactory(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<InnofiedDataSource> getMutableLiveData() {
        return liveData;
    }
    @Override
    public DataSource<Integer, User> create() {
        InnofiedDataSource dataSourceClass = new InnofiedDataSource(repository, compositeDisposable);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}
