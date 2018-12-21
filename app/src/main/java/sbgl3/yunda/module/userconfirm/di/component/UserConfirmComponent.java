package sbgl3.yunda.module.userconfirm.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.userconfirm.di.module.UserConfirmModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.userconfirm.mvp.ui.activity.UserConfirmActivity;

@ActivityScope
@Component(modules = UserConfirmModule.class, dependencies = AppComponent.class)
public interface UserConfirmComponent {
    void inject(UserConfirmActivity activity);
}