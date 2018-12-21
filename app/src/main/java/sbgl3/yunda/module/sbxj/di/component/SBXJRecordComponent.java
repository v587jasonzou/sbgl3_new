package sbgl3.yunda.module.sbxj.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.sbxj.di.module.SBXJRecordModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJRecordActivity;

@ActivityScope
@Component(modules = SBXJRecordModule.class, dependencies = AppComponent.class)
public interface SBXJRecordComponent {
    void inject(SBXJRecordActivity activity);
}