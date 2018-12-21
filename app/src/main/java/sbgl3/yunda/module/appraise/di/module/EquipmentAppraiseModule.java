package sbgl3.yunda.module.appraise.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseContract;
import sbgl3.yunda.module.appraise.mvp.model.EquipmentAppraiseModel;


@Module
public class EquipmentAppraiseModule {
    private EquipmentAppraiseContract.View view;

    /**
     * 构建EquipmentAppraiseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipmentAppraiseModule(EquipmentAppraiseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipmentAppraiseContract.View provideEquipmentAppraiseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipmentAppraiseContract.Model provideEquipmentAppraiseModel(EquipmentAppraiseModel model) {
        return model;
    }
}