package sbgl3.yunda.module.tpgzpg.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.tpgzpg.di.module.TpgzpgListModule;
import sbgl3.yunda.module.tpgzpg.mvp.ui.activity.TpgzpgListActivity;

import com.jess.arms.di.scope.ActivityScope;

@ActivityScope
@Component(modules = TpgzpgListModule.class, dependencies = AppComponent.class)
public interface TpgzpgListComponent {
    void inject(TpgzpgListActivity activity);
}