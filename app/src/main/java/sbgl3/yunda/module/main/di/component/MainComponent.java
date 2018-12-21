package sbgl3.yunda.module.main.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.main.di.module.MainModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.main.mvp.ui.activity.MainActivity;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}