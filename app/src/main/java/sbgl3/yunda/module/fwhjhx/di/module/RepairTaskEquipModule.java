package sbgl3.yunda.module.fwhjhx.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.fwhjhx.mvp.contract.RepairTaskEquipContract;
import sbgl3.yunda.module.fwhjhx.mvp.model.RepairTaskEquipModel;


@Module
public class RepairTaskEquipModule {
    private RepairTaskEquipContract.View view;

    /**
     * 构建RepairTaskEquipModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RepairTaskEquipModule(RepairTaskEquipContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RepairTaskEquipContract.View provideRepairTaskEquipView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RepairTaskEquipContract.Model provideRepairTaskEquipModel(RepairTaskEquipModel model) {
        return model;
    }
}