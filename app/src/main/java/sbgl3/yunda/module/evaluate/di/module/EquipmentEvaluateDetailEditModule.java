package sbgl3.yunda.module.evaluate.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateDetailEditContract;
import sbgl3.yunda.module.evaluate.mvp.model.EquipmentEvaluateDetailEditModel;


@Module
public class EquipmentEvaluateDetailEditModule {
    private EquipmentEvaluateDetailEditContract.View view;

    /**
     * 构建EquipmentEvaluateDetailEditModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipmentEvaluateDetailEditModule(EquipmentEvaluateDetailEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipmentEvaluateDetailEditContract.View provideEquipmentEvaluateDetailEditView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipmentEvaluateDetailEditContract.Model provideEquipmentEvaluateDetailEditModel(EquipmentEvaluateDetailEditModel model) {
        return model;
    }
}