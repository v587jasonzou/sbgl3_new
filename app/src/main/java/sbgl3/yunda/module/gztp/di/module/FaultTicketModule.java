package sbgl3.yunda.module.gztp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gztp.mvp.contract.FaultTicketContract;
import sbgl3.yunda.module.gztp.mvp.model.FaultTicketModel;


@Module
public class FaultTicketModule {
    private FaultTicketContract.View view;

    /**
     * 构建FaultTicketModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FaultTicketModule(FaultTicketContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FaultTicketContract.View provideFaultTicketView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FaultTicketContract.Model provideFaultTicketModel(FaultTicketModel model) {
        return model;
    }
}