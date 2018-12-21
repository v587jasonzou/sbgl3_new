package sbgl3.yunda.module.sbxj.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.sbxj.di.module.TakephotoModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.sbxj.mvp.ui.activity.TakephotoActivity;

@ActivityScope
@Component(modules = TakephotoModule.class, dependencies = AppComponent.class)
public interface TakephotoComponent {
    void inject(TakephotoActivity activity);
}