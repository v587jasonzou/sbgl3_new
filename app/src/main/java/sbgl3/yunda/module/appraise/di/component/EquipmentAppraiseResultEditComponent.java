package sbgl3.yunda.module.appraise.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.appraise.di.module.EquipmentAppraiseResultEditModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseResultEditActivity;

@ActivityScope
@Component(modules = EquipmentAppraiseResultEditModule.class, dependencies = AppComponent.class)
public interface EquipmentAppraiseResultEditComponent {
    void inject(EquipmentAppraiseResultEditActivity activity);
}