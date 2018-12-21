package sbgl3.yunda.module.gztp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.gztp.di.module.NewBuildTicketModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.gztp.mvp.ui.activity.NewBuildTicketActivity;

@ActivityScope
@Component(modules = NewBuildTicketModule.class, dependencies = AppComponent.class)
public interface NewBuildTicketComponent {
    void inject(NewBuildTicketActivity activity);
}