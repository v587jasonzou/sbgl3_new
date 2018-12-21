package sbgl3.yunda.module.evaluate.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.evaluate.di.module.EquipmentEvaluateModule;
import sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateActivity;

import com.jess.arms.di.scope.ActivityScope;

@ActivityScope
@Component(modules = EquipmentEvaluateModule.class, dependencies = AppComponent.class)
public interface EquipmentEvaluateComponent {
    void inject(EquipmentEvaluateActivity activity);
}