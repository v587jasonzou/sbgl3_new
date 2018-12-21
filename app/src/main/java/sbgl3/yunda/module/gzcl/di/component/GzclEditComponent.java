package sbgl3.yunda.module.gzcl.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gzcl.di.module.GzclEditModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gzcl.mvp.ui.activity.GzclEditActivity;

@ActivityScope
@Component(modules = GzclEditModule.class, dependencies = AppComponent.class)
public interface GzclEditComponent {
    void inject(GzclEditActivity activity);
}