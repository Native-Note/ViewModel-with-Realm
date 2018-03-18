package com.nativenote.realmwithviewmodel.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.nativenote.realmwithviewmodel.model.StoreInfo;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by imtiaz on 10/12/17.
 */

public class ListViewModel extends ViewModel {

    private final MediatorLiveData<List<StoreInfo>> dataListModel = new MediatorLiveData<>();

    Realm realm;
    RealmResults<StoreInfo> results;
    RealmChangeListener<RealmResults<StoreInfo>> realmChangeListener = results -> {
        if (results.isLoaded() && results.isValid()) {
            dataListModel.setValue(results);
        }
    };

    public ListViewModel() {
        realm = Realm.getDefaultInstance();

        dataListModel.setValue(null);
        results = realm.where(StoreInfo.class).findAllAsync();

        results.addChangeListener(realmChangeListener);
    }

    public LiveData<List<StoreInfo>> getStores() {
        return dataListModel;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        results.removeChangeListener(realmChangeListener);
        realm.close();
        realm = null;
    }
}
