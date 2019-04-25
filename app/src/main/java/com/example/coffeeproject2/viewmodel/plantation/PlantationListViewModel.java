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

import java.util.List;

public class PlantationListViewModel extends AndroidViewModel {

    private PlantationRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Plantation>> observableEpisodes;

    public PlantationListViewModel(@NonNull Application application,
                                final String showName, PlantationRepository repository) {
        super(application);

        this.repository = repository;

        observableEpisodes = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableEpisodes.setValue(null);

        LiveData<List<Plantation>> episodes = repository.getAllPlantation(showName);

        // observe the changes of the entities from the database and forward them
        observableEpisodes.addSource(episodes, observableEpisodes::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final PlantationRepository repository;
        private final String showName;

        public Factory(@NonNull Application application, String showName) {
            this.application = application;
            this.showName = showName;
            repository = ((BaseApp) application).getPlantationRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PlantationListViewModel(application, showName, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Plantation>> getEpisodes() {
        return observableEpisodes;
    }

    public void deleteEpisode(Plantation episode, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getPlantationRepository()
                .delete(episode, callback);
    }


}