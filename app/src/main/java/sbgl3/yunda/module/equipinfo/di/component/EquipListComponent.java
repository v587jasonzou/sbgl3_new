package sbgl3.yunda.module.equipinfo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.equipinfo.di.module.EquipListModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.equipinfo.mvp.ui.activity.EquipListActivity;

@ActivityScope
@Component(modules = EquipListModule.class, dependencies = AppComponent.class)
public interface EquipListComponent {
    void inject(EquipListActivity activity);
}