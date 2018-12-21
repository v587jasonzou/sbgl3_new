package sbgl3.yunda.module.gzqr.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gzqr.di.module.GzqrEquipmentListModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gzqr.mvp.ui.activity.GzqrEquipmentListActivity;

@ActivityScope
@Component(modules = GzqrEquipmentListModule.class, dependencies = AppComponent.class)
public interface GzqrEquipmentListComponent {
    void inject(GzqrEquipmentListActivity activity);
}