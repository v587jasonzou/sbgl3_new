package sbgl3.yunda.module.evaluate.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateCheckItemContract;
import sbgl3.yunda.module.evaluate.mvp.model.EquipmentEvaluateCheckItemModel;


@Module
public class EquipmentEvaluateCheckItemModule {
    private EquipmentEvaluateCheckItemContract.View view;

    /**
     * 构建EquipmentEvaluateCheckItemModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipmentEvaluateCheckItemModule(EquipmentEvaluateCheckItemContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipmentEvaluateCheckItemContract.View provideEquipmentEvaluateCheckItemView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipmentEvaluateCheckItemContract.Model provideEquipmentEvaluateCheckItemModel(EquipmentEvaluateCheckItemModel model) {
        return model;
    }
}