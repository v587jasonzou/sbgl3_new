package sbgl3.yunda.module.login.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.login.di.module.LoginActivityModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.login.mvp.ui.activity.LoginActivityActivity;

@ActivityScope
@Component(modules = LoginActivityModule.class, dependencies = AppComponent.class)
public interface LoginActivityComponent {
    void inject(LoginActivityActivity activity);
}