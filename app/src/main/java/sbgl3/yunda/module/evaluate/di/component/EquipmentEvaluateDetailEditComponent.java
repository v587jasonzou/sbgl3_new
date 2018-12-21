package sbgl3.yunda.module.evaluate.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.evaluate.di.module.EquipmentEvaluateDetailEditModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateDetailEditActivity;

@ActivityScope
@Component(modules = EquipmentEvaluateDetailEditModule.class, dependencies = AppComponent.class)
public interface EquipmentEvaluateDetailEditComponent {
    void inject(EquipmentEvaluateDetailEditActivity activity);
}