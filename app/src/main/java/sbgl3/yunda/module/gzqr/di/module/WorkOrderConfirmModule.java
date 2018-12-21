package sbgl3.yunda.module.gzqr.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gzqr.mvp.contract.WorkOrderConfirmContract;
import sbgl3.yunda.module.gzqr.mvp.model.WorkOrderConfirmModel;


@Module
public class WorkOrderConfirmModule {
    private WorkOrderConfirmContract.View view;

    /**
     * 构建WorkOrderConfirmModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WorkOrderConfirmModule(WorkOrderConfirmContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WorkOrderConfirmContract.View provideWorkOrderConfirmView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WorkOrderConfirmContract.Model provideWorkOrderConfirmModel(WorkOrderConfirmModel model) {
        return model;
    }
}