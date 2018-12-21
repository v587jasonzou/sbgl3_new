package sbgl3.yunda.module.userconfirm.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.userconfirm.mvp.contract.PlanRepairOrderConfirmContract;
import sbgl3.yunda.module.userconfirm.mvp.model.PlanRepairOrderConfirmModel;


@Module
public class PlanRepairOrderConfirmModule {
    private PlanRepairOrderConfirmContract.View view;

    /**
     * 构建PlanRepairOrderConfirmModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PlanRepairOrderConfirmModule(PlanRepairOrderConfirmContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PlanRepairOrderConfirmContract.View providePlanRepairOrderConfirmView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PlanRepairOrderConfirmContract.Model providePlanRepairOrderConfirmModel(PlanRepairOrderConfirmModel model) {
        return model;
    }
}