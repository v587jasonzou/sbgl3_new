package sbgl3.yunda.module.sbdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.sbdj.mvp.contract.ReadPhotoContract;
import sbgl3.yunda.module.sbdj.mvp.model.ReadPhotoModel;


@Module
public class ReadPhotoModule {
    private ReadPhotoContract.View view;

    /**
     * 构建ReadPhotoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ReadPhotoModule(ReadPhotoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReadPhotoContract.View provideReadPhotoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ReadPhotoContract.Model provideReadPhotoModel(ReadPhotoModel model) {
        return model;
    }
}