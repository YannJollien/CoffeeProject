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

import java.util.List;

public class StorageListViewModel extends AndroidViewModel {

    private StorageRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Storage>> observableStorage;

    public StorageListViewModel(@NonNull Application application,
                                   final String showName, StorageRepository repository) {
        super(application);

        this.repository = repository;

        observableStorage = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableStorage.setValue(null);

        LiveData<List<Storage>> storages = repository.getAllStorages(showName);

        // observe the changes of the entities from the database and forward them
        //observableEpisodes.addSource(episodes, observableEpisodes::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final StorageRepository repository;
        private final String showName;

        public Factory(@NonNull Application application, String showName) {
            this.application = application;
            this.showName = showName;
            repository = ((BaseApp) application).getEpisodeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new StorageListViewModel(application, showName, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Storage>> getStorages() {
        return observableStorage;
    }

    public void deleteStorage(Storage storage, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .delete(storage, callback);
    }

    public void updateStorage(Storage storage, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .update(storage, callback);
    }


}
