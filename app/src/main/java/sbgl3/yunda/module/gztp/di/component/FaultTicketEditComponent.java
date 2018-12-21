package sbgl3.yunda.module.gztp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gztp.di.module.FaultTicketEditModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gztp.mvp.ui.activity.FaultTicketEditActivity;

@ActivityScope
@Component(modules = FaultTicketEditModule.class, dependencies = AppComponent.class)
public interface FaultTicketEditComponent {
    void inject(FaultTicketEditActivity activity);
}