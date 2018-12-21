package sbgl3.yunda.module.fwhjhx.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.fwhjhx.di.module.FwhRepaiScopsModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.fwhjhx.mvp.ui.activity.FwhRepaiScopsActivity;

@ActivityScope
@Component(modules = FwhRepaiScopsModule.class, dependencies = AppComponent.class)
public interface FwhRepaiScopsComponent {
    void inject(FwhRepaiScopsActivity activity);
}