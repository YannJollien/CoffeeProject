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
import com.example.coffeeproject2.database.rep.PlantationRepository;
import com.example.coffeeproject2.database.rep.StorageRepository;
import com.example.coffeeproject2.util.OnAsyncEventListener;

public class StorageViewModel extends AndroidViewModel {

    private StorageRepository repository;
    private MediatorLiveData<Storage> observableStorage;

    public StorageViewModel(@NonNull Application application,
                            final String idStorage, StorageRepository repository) {
        super(application);

        this.repository = repository;

        observableStorage = new MediatorLiveData<>();
        observableStorage.setValue(null);

        if (idStorage != null){
            LiveData<Storage> storage = repository.getStorage(idStorage);
            observableStorage.addSource(storage, observableStorage::setValue);

        }

    }


   /* public StorageViewModel(@NonNull Application application, String idEpisode, String showName, PlantationRepository repository) {
        super(application);
    }
*/
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String idStorage;
        private final StorageRepository repository;

        public Factory(@NonNull Application application, String idStorage) {
            this.application = application;
            this.idStorage = idStorage;
            repository = ((BaseApp) application).getEpisodeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new StorageViewModel(application, idStorage, repository);
        }
    }

    public LiveData<Storage> getStorage() {
        return observableStorage;
    }

    public void createStorage(Storage storage, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .insert(storage, callback);
    }

}
