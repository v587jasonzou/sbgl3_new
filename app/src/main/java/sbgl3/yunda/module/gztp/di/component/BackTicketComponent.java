package sbgl3.yunda.module.gztp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gztp.di.module.BackTicketModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gztp.mvp.ui.activity.BackTicketActivity;

@ActivityScope
@Component(modules = BackTicketModule.class, dependencies = AppComponent.class)
public interface BackTicketComponent {
    void inject(BackTicketActivity activity);
}