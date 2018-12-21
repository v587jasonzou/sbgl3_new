package sbgl3.yunda.module.fwhjhx.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.fwhjhx.mvp.contract.ManageWorkorderContract;
import sbgl3.yunda.module.fwhjhx.mvp.model.ManageWorkorderModel;


@Module
public class ManageWorkorderModule {
    private ManageWorkorderContract.View view;

    /**
     * 构建ManageWorkorderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ManageWorkorderModule(ManageWorkorderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ManageWorkorderContract.View provideManageWorkorderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ManageWorkorderContract.Model provideManageWorkorderModel(ManageWorkorderModel model) {
        return model;
    }
}