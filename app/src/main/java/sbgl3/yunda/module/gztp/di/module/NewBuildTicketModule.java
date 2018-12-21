package sbgl3.yunda.module.gztp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gztp.mvp.contract.NewBuildTicketContract;
import sbgl3.yunda.module.gztp.mvp.model.NewBuildTicketModel;


@Module
public class NewBuildTicketModule {
    private NewBuildTicketContract.View view;

    /**
     * 构建NewBuildTicketModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewBuildTicketModule(NewBuildTicketContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewBuildTicketContract.View provideNewBuildTicketView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewBuildTicketContract.Model provideNewBuildTicketModel(NewBuildTicketModel model) {
        return model;
    }
}