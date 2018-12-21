package sbgl3.yunda.module.userconfirm.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.userconfirm.di.module.PlanRepairOrderConfirmModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.userconfirm.mvp.ui.activity.PlanRepairOrderConfirmActivity;

@ActivityScope
@Component(modules = PlanRepairOrderConfirmModule.class, dependencies = AppComponent.class)
public interface PlanRepairOrderConfirmComponent {
    void inject(PlanRepairOrderConfirmActivity activity);
}