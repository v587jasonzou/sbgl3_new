package sbgl3.yunda.module.gzcl.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gzcl.di.module.GzclListModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gzcl.mvp.ui.activity.GzclListActivity;

@ActivityScope
@Component(modules = GzclListModule.class, dependencies = AppComponent.class)
public interface GzclListComponent {
    void inject(GzclListActivity activity);
}