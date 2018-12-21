package sbgl3.yunda.module.ysrqr.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.ysrqr.di.module.YsrWorkOrderConfirmModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.ysrqr.mvp.ui.activity.YsrWorkOrderConfirmActivity;

@ActivityScope
@Component(modules = YsrWorkOrderConfirmModule.class, dependencies = AppComponent.class)
public interface YsrWorkOrderConfirmComponent {
    void inject(YsrWorkOrderConfirmActivity activity);
}