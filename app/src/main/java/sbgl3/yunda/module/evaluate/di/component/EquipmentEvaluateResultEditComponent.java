package sbgl3.yunda.module.evaluate.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;


import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.evaluate.di.module.EquipmentEvaluateResultEditModule;
import sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateResultEditActivity;

@ActivityScope
@Component(modules = EquipmentEvaluateResultEditModule.class, dependencies = AppComponent.class)
public interface EquipmentEvaluateResultEditComponent {
    void inject(EquipmentEvaluateResultEditActivity activity);
}