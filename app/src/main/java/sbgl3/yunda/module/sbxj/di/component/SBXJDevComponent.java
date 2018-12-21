package sbgl3.yunda.module.sbxj.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.sbxj.di.module.SBXJDevModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJDevActivity;

@ActivityScope
@Component(modules = SBXJDevModule.class, dependencies = AppComponent.class)
public interface SBXJDevComponent {
    void inject(SBXJDevActivity activity);
}