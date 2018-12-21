package sbgl3.yunda.module.ysrqr.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.ysrqr.mvp.contract.YsrConfirmSubmitContract;
import sbgl3.yunda.module.ysrqr.mvp.model.YsrConfirmSubmitModel;


@Module
public class YsrConfirmSubmitModule {
    private YsrConfirmSubmitContract.View view;

    /**
     * 构建YsrConfirmSubmitModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public YsrConfirmSubmitModule(YsrConfirmSubmitContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    YsrConfirmSubmitContract.View provideYsrConfirmSubmitView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    YsrConfirmSubmitContract.Model provideYsrConfirmSubmitModel(YsrConfirmSubmitModel model) {
        return model;
    }
}