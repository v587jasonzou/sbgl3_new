package sbgl3.yunda.module.tpgzpg.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.tpgzpg.di.module.TpgzpgEditModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.tpgzpg.mvp.ui.activity.TpgzpgEditActivity;

@ActivityScope
@Component(modules = TpgzpgEditModule.class, dependencies = AppComponent.class)
public interface TpgzpgEditComponent {
    void inject(TpgzpgEditActivity activity);
}