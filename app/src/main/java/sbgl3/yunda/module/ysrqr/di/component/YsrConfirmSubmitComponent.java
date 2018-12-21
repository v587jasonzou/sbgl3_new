package sbgl3.yunda.module.ysrqr.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.ysrqr.di.module.YsrConfirmSubmitModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.ysrqr.mvp.ui.activity.YsrConfirmSubmitActivity;

@ActivityScope
@Component(modules = YsrConfirmSubmitModule.class, dependencies = AppComponent.class)
public interface YsrConfirmSubmitComponent {
    void inject(YsrConfirmSubmitActivity activity);
}