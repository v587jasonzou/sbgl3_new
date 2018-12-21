package sbgl3.yunda.module.ysrqr.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.ysrqr.mvp.contract.YsrWorkOrderConfirmContract;
import sbgl3.yunda.module.ysrqr.mvp.model.YsrWorkOrderConfirmModel;


@Module
public class YsrWorkOrderConfirmModule {
    private YsrWorkOrderConfirmContract.View view;

    /**
     * 构建YsrWorkOrderConfirmModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public YsrWorkOrderConfirmModule(YsrWorkOrderConfirmContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    YsrWorkOrderConfirmContract.View provideYsrWorkOrderConfirmView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    YsrWorkOrderConfirmContract.Model provideYsrWorkOrderConfirmModel(YsrWorkOrderConfirmModel model) {
        return model;
    }
}