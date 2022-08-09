package payments.oline.application.di.modules

import com.example.learnup.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import exo.player.exoplayerpetprogect.network.ApiModuleProvider
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GetVideoInfoApiModule : ApiModuleProvider<ApiService> {
    @Singleton
    @Provides
    override fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
