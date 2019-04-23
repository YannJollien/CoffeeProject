package com.example.coffeeproject2.viewmodel.storage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.coffeeproject2.BaseApp;
import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.database.rep.StorageRepository;
import com.example.coffeeproject2.util.OnAsyncEventListener;

public class StorageViewModel extends AndroidViewModel {

    private StorageRepository repository;
    private final MediatorLiveData<Storage> observableEpisode;

    public PlantationViewModel(@NonNull Application application,
                               final String idEpisode, final String showName, StorageRepository repository) {
        super(application);

        this.repository = repository;

        observableEpisode = new MediatorLiveData<>();
        observableEpisode.setValue(null);

        if (idEpisode != null){
            LiveData<Storage> account = repository.getEpisode(idEpisode, showName);
            observableEpisode.addSource(account, observableEpisode::setValue);
        }
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String idEpisode;
        private final String showName;
        private final StorageRepository repository;

        public Factory(@NonNull Application application, String idEpisode, String showName) {
            this.application = application;
            this.idEpisode = idEpisode;
            this.showName = showName;
            repository = ((BaseApp) application).getEpisodeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new StorageViewModel(application, idEpisode, showName, repository);
        }
    }

    public LiveData<Storage> getEpisode() {
        return observableEpisode;
    }

    public void createEpisode(Storage episode, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .insert(episode, callback);
    }

    public void updateEpisode(Storage episode, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .update(episode, callback);
    }

    public void deleteEpisode(Storage episode, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .delete(episode, callback);
    }


}
