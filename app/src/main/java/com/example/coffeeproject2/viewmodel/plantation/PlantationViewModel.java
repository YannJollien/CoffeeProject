package com.example.coffeeproject2.viewmodel.plantation;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.coffeeproject2.BaseApp;
import com.example.coffeeproject2.database.entity.Plantation;
import com.example.coffeeproject2.database.rep.PlantationRepository;
import com.example.coffeeproject2.util.OnAsyncEventListener;

public class PlantationViewModel extends AndroidViewModel {

    private PlantationRepository repository;
    private final MediatorLiveData<Plantation> observableEpisode;

    public PlantationViewModel(@NonNull Application application,
                            final String idEpisode, final String showName, PlantationRepository repository) {
        super(application);

        this.repository = repository;

        observableEpisode = new MediatorLiveData<>();
        observableEpisode.setValue(null);

        if (idEpisode != null){
            LiveData<Plantation> account = repository.getEpisode(idEpisode, showName);
            observableEpisode.addSource(account, observableEpisode::setValue);
        }
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String idEpisode;
        private final String showName;
        private final PlantationRepository repository;

        public Factory(@NonNull Application application, String idEpisode, String showName) {
            this.application = application;
            this.idEpisode = idEpisode;
            this.showName = showName;
            repository = ((BaseApp) application).getEpisodeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PlantationViewModel(application, idEpisode, showName, repository);
        }
    }

    public LiveData<Plantation> getEpisode() {
        return observableEpisode;
    }

    public void createEpisode(Plantation episode, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .insert(episode, callback);
    }

    public void updateEpisode(Plantation episode, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .update(episode, callback);
    }

    public void deleteEpisode(Plantation episode, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getEpisodeRepository()
                .delete(episode, callback);
    }

}
