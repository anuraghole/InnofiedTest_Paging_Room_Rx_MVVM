package com.example.innofiedtest.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.innofiedtest.MyApplication;
import com.example.innofiedtest.model.ResponseModel;
import com.example.innofiedtest.model.User;
import com.example.innofiedtest.repository.Repository;
import com.example.innofiedtest.util.Constant;
import com.example.innofiedtest.util.NetworkUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class InnofiedDataSource extends PageKeyedDataSource<Integer, User> {

    private Repository repository;
    private int per_page = 5;
    private int sourceIndex;
    private MutableLiveData<String> progressLiveStatus;
    private CompositeDisposable compositeDisposable;

    public InnofiedDataSource(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        progressLiveStatus = new MutableLiveData<>();
    }

    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, User> callback) {
        if (NetworkUtil.isConnected()){
            loadInitialApi(callback);
        }else {
            loadInitialLocalDb(callback);
        }

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, final @NonNull LoadCallback<Integer, User> callback) {
        if (NetworkUtil.isConnected()){
            loadAfterApi(params.key,callback);
        }else {
            loadAfterLocalDb(params.key,callback);
        }

    }


    private void loadInitialLocalDb(LoadInitialCallback<Integer, User> callback) {
        repository.getResponseFromDB(sourceIndex, per_page)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (List<User> result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);
                            sourceIndex++;
                            if (result != null) {
                                List<User> arrayList = result;
                                callback.onResult(arrayList, null, sourceIndex);
                            }
                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)

                );
    }

    private void loadInitialApi(LoadInitialCallback<Integer, User> callback) {
        repository.getResponse(sourceIndex, per_page)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (ResponseModel result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);
                            sourceIndex++;
                            if (result != null && result.getUsers() != null) {
                                List<User> arrayList = result.getUsers();
                                repository.insertUsersInDB(arrayList);
                                callback.onResult(arrayList, null, sourceIndex);
                            }
                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)

                );
    }

    private void loadAfterLocalDb(Integer key, LoadCallback<Integer, User> callback) {
        repository.getResponseFromDB(key, per_page)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (List<User> result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            if (result != null) {
                                List<User> arrayList = result;
                                callback.onResult(arrayList, key + 1);
                            }

                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)
                );
    }

    private void loadAfterApi(Integer key, LoadCallback<Integer, User> callback) {
        repository.getResponse(key, per_page)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (ResponseModel result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            if (result != null && result.getUsers() != null) {
                                List<User> arrayList = result.getUsers();
                                repository.insertUsersInDB(arrayList);
                                callback.onResult(arrayList, key + 1);
                            }

                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)
                );
    }
}
