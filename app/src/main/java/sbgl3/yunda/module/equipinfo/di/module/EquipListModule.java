package sbgl3.yunda.module.equipinfo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.equipinfo.mvp.contract.EquipListContract;
import sbgl3.yunda.module.equipinfo.mvp.model.EquipListModel;


@Module
public class EquipListModule {
    private EquipListContract.View view;

    /**
     * 构建EquipListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EquipListModule(EquipListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EquipListContract.View provideEquipListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EquipListContract.Model provideEquipListModel(EquipListModel model) {
        return model;
    }
}