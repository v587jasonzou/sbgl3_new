package sbgl3.yunda.module.gzqr.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gzqr.di.module.WorkOrderConfirmModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gzqr.mvp.ui.activity.WorkOrderConfirmActivity;

@ActivityScope
@Component(modules = WorkOrderConfirmModule.class, dependencies = AppComponent.class)
public interface WorkOrderConfirmComponent {
    void inject(WorkOrderConfirmActivity activity);
}