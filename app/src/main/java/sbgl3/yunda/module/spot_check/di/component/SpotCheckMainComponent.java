package sbgl3.yunda.module.spot_check.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.spot_check.di.module.SpotCheckMainModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.spot_check.mvp.ui.activity.SpotCheckMainActivity;

@ActivityScope
@Component(modules = SpotCheckMainModule.class, dependencies = AppComponent.class)
public interface SpotCheckMainComponent {
    void inject(SpotCheckMainActivity activity);
}