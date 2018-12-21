package sbgl3.yunda.module.userconfirm.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.userconfirm.di.module.FaultOrderConfirmEditModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.userconfirm.mvp.ui.activity.FaultOrderConfirmEditActivity;

@ActivityScope
@Component(modules = FaultOrderConfirmEditModule.class, dependencies = AppComponent.class)
public interface FaultOrderConfirmEditComponent {
    void inject(FaultOrderConfirmEditActivity activity);
}