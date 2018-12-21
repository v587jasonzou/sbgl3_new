package sbgl3.yunda.module.tpgzpg.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.tpgzpg.di.module.BackDispatcherModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.tpgzpg.mvp.ui.activity.BackDispatcherActivity;

@ActivityScope
@Component(modules = BackDispatcherModule.class, dependencies = AppComponent.class)
public interface BackDispatcherComponent {
    void inject(BackDispatcherActivity activity);
}