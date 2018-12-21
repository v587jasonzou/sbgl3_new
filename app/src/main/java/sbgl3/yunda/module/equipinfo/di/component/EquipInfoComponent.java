package sbgl3.yunda.module.equipinfo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.equipinfo.di.module.EquipInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.equipinfo.mvp.ui.activity.EquipInfoActivity;

@ActivityScope
@Component(modules = EquipInfoModule.class, dependencies = AppComponent.class)
public interface EquipInfoComponent {
    void inject(EquipInfoActivity activity);
}