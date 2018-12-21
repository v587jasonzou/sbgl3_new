package sbgl3.yunda.module.sbdj.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.sbdj.di.module.SbdjMainModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.sbdj.mvp.ui.activity.SbdjMainActivity;

@ActivityScope
@Component(modules = SbdjMainModule.class, dependencies = AppComponent.class)
public interface SbdjMainComponent {
    void inject(SbdjMainActivity activity);
}