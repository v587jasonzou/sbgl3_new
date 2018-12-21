package sbgl3.yunda.widget;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import sbgl3.yunda.module.login.di.module.LoginActivityModule;
import sbgl3.yunda.module.login.mvp.ui.activity.LoginActivityActivity;

@ActivityScope
@Component(modules = PhotoReadActivityModule.class, dependencies = AppComponent.class)
public interface PhotoReadActivityComponent {
    void inject(PhotoReadActivity activity);
}