package sbgl3.yunda.module.appraise.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.appraise.di.module.EquipmentAppraiseModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseActivity;

@ActivityScope
@Component(modules = EquipmentAppraiseModule.class, dependencies = AppComponent.class)
public interface EquipmentAppraiseComponent {
    void inject(EquipmentAppraiseActivity activity);
}