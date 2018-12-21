package sbgl3.yunda.module.evaluate.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateResultEditContract;
import sbgl3.yunda.module.evaluate.mvp.model.EquipmentEvaluateResultEditModel;


@Module
public class EquipmentEvaluateResultEditModule {
    private EquipmentEvaluateResultEditContract.View view;

    /**
     * 构建EquipmentEvaluateResultEditActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipmentEvaluateResultEditModule(EquipmentEvaluateResultEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipmentEvaluateResultEditContract.View provideEquipmentEvaluateResultEditActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipmentEvaluateResultEditContract.Model provideEquipmentEvaluateResultEditActivityModel(EquipmentEvaluateResultEditModel model) {
        return model;
    }
}