package sbgl3.yunda.module.fwhjhx.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.fwhjhx.di.module.RepairTaskEquipModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.fwhjhx.mvp.ui.activity.RepairTaskEquipActivity;

@ActivityScope
@Component(modules = RepairTaskEquipModule.class, dependencies = AppComponent.class)
public interface RepairTaskEquipComponent {
    void inject(RepairTaskEquipActivity activity);
}