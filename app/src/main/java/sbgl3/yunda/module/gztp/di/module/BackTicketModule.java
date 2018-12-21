package sbgl3.yunda.module.gztp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gztp.mvp.contract.BackTicketContract;
import sbgl3.yunda.module.gztp.mvp.model.BackTicketModel;


@Module
public class BackTicketModule {
    private BackTicketContract.View view;

    /**
     * 构建BackTicketModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BackTicketModule(BackTicketContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BackTicketContract.View provideBackTicketView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BackTicketContract.Model provideBackTicketModel(BackTicketModel model) {
        return model;
    }
}