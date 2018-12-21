package sbgl3.yunda.module.gzcl.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gzcl.di.module.GzclBackDispatcherModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gzcl.mvp.ui.activity.GzclBackDispatcherActivity;

@ActivityScope
@Component(modules = GzclBackDispatcherModule.class, dependencies = AppComponent.class)
public interface GzclBackDispatcherComponent {
    void inject(GzclBackDispatcherActivity activity);
}