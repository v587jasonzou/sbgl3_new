package sbgl3.yunda.module.appraise.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseCheckItemContract;
import sbgl3.yunda.module.appraise.mvp.model.EquipmentAppraiseCheckItemModel;


@Module
public class EquipmentAppraiseCheckItemModule {
    private EquipmentAppraiseCheckItemContract.View view;

    /**
     * 构建EquipmentAppraiseCheckItemModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipmentAppraiseCheckItemModule(EquipmentAppraiseCheckItemContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipmentAppraiseCheckItemContract.View provideEquipmentAppraiseCheckItemView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipmentAppraiseCheckItemContract.Model provideEquipmentAppraiseCheckItemModel(EquipmentAppraiseCheckItemModel model) {
        return model;
    }
}