package sbgl3.yunda.module.appraise.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseDetailEditContract;
import sbgl3.yunda.module.appraise.mvp.model.EquipmentAppraiseDetailEditModel;


@Module
public class EquipmentAppraiseDetailEditModule {
    private EquipmentAppraiseDetailEditContract.View view;

    /**
     * 构建EquipmentAppraiseDetailEditModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipmentAppraiseDetailEditModule(EquipmentAppraiseDetailEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipmentAppraiseDetailEditContract.View provideEquipmentAppraiseDetailEditView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipmentAppraiseDetailEditContract.Model provideEquipmentAppraiseDetailEditModel(EquipmentAppraiseDetailEditModel model) {
        return model;
    }
}