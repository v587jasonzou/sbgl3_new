package sbgl3.yunda.module.appraise.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.appraise.di.module.EquipmentAppraiseDetailEditModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseDetailEditActivity;

@ActivityScope
@Component(modules = EquipmentAppraiseDetailEditModule.class, dependencies = AppComponent.class)
public interface EquipmentAppraiseDetailEditComponent {
    void inject(EquipmentAppraiseDetailEditActivity activity);
}