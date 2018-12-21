package sbgl3.yunda.module.fwhjhx.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.fwhjhx.di.module.ManageWorkorderModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.fwhjhx.mvp.ui.activity.ManageWorkorderActivity;

@ActivityScope
@Component(modules = ManageWorkorderModule.class, dependencies = AppComponent.class)
public interface ManageWorkorderComponent {
    void inject(ManageWorkorderActivity activity);
}