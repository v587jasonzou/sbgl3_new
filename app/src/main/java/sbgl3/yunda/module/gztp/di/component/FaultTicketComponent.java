package sbgl3.yunda.module.gztp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gztp.di.module.FaultTicketModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gztp.mvp.ui.activity.FaultTicketActivity;

@ActivityScope
@Component(modules = FaultTicketModule.class, dependencies = AppComponent.class)
public interface FaultTicketComponent {
    void inject(FaultTicketActivity activity);
}