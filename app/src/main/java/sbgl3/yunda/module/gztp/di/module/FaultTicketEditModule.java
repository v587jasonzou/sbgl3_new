package sbgl3.yunda.module.gztp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gztp.mvp.contract.FaultTicketEditContract;
import sbgl3.yunda.module.gztp.mvp.model.FaultTicketEditModel;


@Module
public class FaultTicketEditModule {
    private FaultTicketEditContract.View view;

    /**
     * 构建FaultTicketEditModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FaultTicketEditModule(FaultTicketEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FaultTicketEditContract.View provideFaultTicketEditView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FaultTicketEditContract.Model provideFaultTicketEditModel(FaultTicketEditModel model) {
        return model;
    }
}