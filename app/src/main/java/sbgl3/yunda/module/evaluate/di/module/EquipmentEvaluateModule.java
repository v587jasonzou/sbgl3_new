package sbgl3.yunda.module.evaluate.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateContract;
import sbgl3.yunda.module.evaluate.mvp.model.EquipmentEvaluateModel;


@Module
public class EquipmentEvaluateModule {
    private EquipmentEvaluateContract.View view;

    /**
     * 构建EquipmentEvaluateModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipmentEvaluateModule(EquipmentEvaluateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipmentEvaluateContract.View provideEquipmentEvaluateView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipmentEvaluateContract.Model provideEquipmentEvaluateModel(EquipmentEvaluateModel model) {
        return model;
    }
}