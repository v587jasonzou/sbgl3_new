package sbgl3.yunda.module.sbdj.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.sbdj.di.module.ReadPhotoModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.sbdj.mvp.ui.activity.ReadPhotoActivity;

@ActivityScope
@Component(modules = ReadPhotoModule.class, dependencies = AppComponent.class)
public interface ReadPhotoComponent {
    void inject(ReadPhotoActivity activity);
}