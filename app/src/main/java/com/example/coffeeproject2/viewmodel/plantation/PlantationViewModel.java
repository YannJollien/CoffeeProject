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
import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.database.rep.PlantationRepository;
import com.example.coffeeproject2.database.rep.StorageRepository;
import com.example.coffeeproject2.util.OnAsyncEventListener;

public class PlantationViewModel extends AndroidViewModel {

    private PlantationRepository repository;
    private MediatorLiveData<Plantation> observablePlantation;

    public PlantationViewModel(@NonNull Application application,
                            final String idPlantation, PlantationRepository repository) {
        super(application);

        this.repository = repository;

        observablePlantation = new MediatorLiveData<>();
        observablePlantation.setValue(null);

        if (idPlantation != null){
            LiveData<Plantation> account = repository.getPlantation(idPlantation);
            observablePlantation.addSource(account, observablePlantation::setValue);

        }

    }


    /* public StorageViewModel(@NonNull Application application, String idEpisode, String showName, PlantationRepository repository) {
         super(application);
     }
 */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String idPlantation;
        private final PlantationRepository repository;

        public Factory(@NonNull Application application, String idPlantation) {
            this.application = application;
            this.idPlantation = idPlantation;
            repository = ((BaseApp) application).getPlantationRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PlantationViewModel(application, idPlantation, repository);
        }
    }

    public LiveData<Plantation> getPlantation() {
        return observablePlantation;
    }

    public void createPlantation(Plantation plantation, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getPlantationRepository()
                .insert(plantation, callback);
    }

}
