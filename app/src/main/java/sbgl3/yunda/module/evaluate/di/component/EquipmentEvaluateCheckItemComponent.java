package sbgl3.yunda.module.evaluate.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.evaluate.di.module.EquipmentEvaluateCheckItemModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateCheckItemActivity;

@ActivityScope
@Component(modules = EquipmentEvaluateCheckItemModule.class, dependencies = AppComponent.class)
public interface EquipmentEvaluateCheckItemComponent {
    void inject(EquipmentEvaluateCheckItemActivity activity);
}