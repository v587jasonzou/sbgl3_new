package sbgl3.yunda.module.userconfirm.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.userconfirm.mvp.contract.FaultOrderConfirmEditContract;
import sbgl3.yunda.module.userconfirm.mvp.model.FaultOrderConfirmEditModel;


@Module
public class FaultOrderConfirmEditModule {
    private FaultOrderConfirmEditContract.View view;

    /**
     * 构建FaultOrderConfirmEditModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FaultOrderConfirmEditModule(FaultOrderConfirmEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FaultOrderConfirmEditContract.View provideFaultOrderConfirmEditView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FaultOrderConfirmEditContract.Model provideFaultOrderConfirmEditModel(FaultOrderConfirmEditModel model) {
        return model;
    }
}