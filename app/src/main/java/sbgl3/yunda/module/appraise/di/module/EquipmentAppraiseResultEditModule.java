package sbgl3.yunda.module.appraise.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseResultEditContract;
import sbgl3.yunda.module.appraise.mvp.model.EquipmentAppraiseResultEditModel;


@Module
public class EquipmentAppraiseResultEditModule {
    private EquipmentAppraiseResultEditContract.View view;

    /**
     * 构建EquipmentAppraiseResultEditModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipmentAppraiseResultEditModule(EquipmentAppraiseResultEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipmentAppraiseResultEditContract.View provideEquipmentAppraiseResultEditView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipmentAppraiseResultEditContract.Model provideEquipmentAppraiseResultEditModel(EquipmentAppraiseResultEditModel model) {
        return model;
    }
}