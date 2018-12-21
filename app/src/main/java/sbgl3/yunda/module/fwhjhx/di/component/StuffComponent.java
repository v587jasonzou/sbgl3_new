package sbgl3.yunda.module.fwhjhx.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.fwhjhx.di.module.StuffModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.fwhjhx.mvp.ui.activity.StuffActivity;

@ActivityScope
@Component(modules = StuffModule.class, dependencies = AppComponent.class)
public interface StuffComponent {
    void inject(StuffActivity activity);
}