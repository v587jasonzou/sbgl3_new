package sbgl3.yunda.module.equipinfo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.equipinfo.mvp.contract.EquipInfoContract;
import sbgl3.yunda.module.equipinfo.mvp.model.EquipInfoModel;


@Module
public class EquipInfoModule {
    private EquipInfoContract.View view;

    /**
     * 构建EquipInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipInfoModule(EquipInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipInfoContract.View provideEquipInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipInfoContract.Model provideEquipInfoModel(EquipInfoModel model) {
        return model;
    }
}