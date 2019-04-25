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
    private MediatorLiveData<Storage> observableEpisode;

    public StorageViewModel(@NonNull Application application,
                            final String idEpisode, StorageRepository repository) {
        super(application);

        this.repository = repository;

        observableEpisode = new MediatorLiveData<>();
        observableEpisode.setValue(null);

        if (idEpisode != null){
            LiveData<Storage> account = repository.getStorage(idEpisode);
            observableEpisode.addSource(account, observableEpisode::setValue);

        }

    }


   /* public StorageViewModel(@NonNull Application application, String idEpisode, String showName, PlantationRepository repository) {
        super(application);
    }
*/
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String idEpisode;
        private final StorageRepository repository;

        public Factory(@NonNull Application application, String idEpisode) {
            this.application = application;
            this.idEpisode = idEpisode;
            repository = ((BaseApp) application).getEpisodeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new StorageViewModel(application, idEpisode, repository);
        }
    }

    public LiveData<Storage> getEpisode() {
        return observableEpisode;
    }

    public void createEpisode(Storage episode, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .insert(episode, callback);
    }

}
