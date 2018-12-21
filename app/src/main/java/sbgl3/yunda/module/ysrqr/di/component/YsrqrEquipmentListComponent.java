package sbgl3.yunda.module.ysrqr.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.ysrqr.di.module.YsrqrEquipmentListModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.ysrqr.mvp.ui.activity.YsrqrEquipmentListActivity;

@ActivityScope
@Component(modules = YsrqrEquipmentListModule.class, dependencies = AppComponent.class)
public interface YsrqrEquipmentListComponent {
    void inject(YsrqrEquipmentListActivity activity);
}