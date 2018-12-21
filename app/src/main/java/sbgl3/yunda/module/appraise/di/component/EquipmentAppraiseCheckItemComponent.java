package sbgl3.yunda.module.appraise.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.appraise.di.module.EquipmentAppraiseCheckItemModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseCheckItemActivity;

@ActivityScope
@Component(modules = EquipmentAppraiseCheckItemModule.class, dependencies = AppComponent.class)
public interface EquipmentAppraiseCheckItemComponent {
    void inject(EquipmentAppraiseCheckItemActivity activity);
}