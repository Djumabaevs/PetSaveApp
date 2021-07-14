package com.bignerdranch.android.petsaveapp.core.data.di


@InstallIn(ApplicationComponent::class)
@Module
class ApiModule {

  @Provides
  @Singleton
  fun provideApi(okHttpClient: OkHttpClient): PetFinderApi {
    return Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(PetFinderApi::class.java)
  }

  @Provides
  fun provideOkHttpClient(
      httpLoggingInterceptor: HttpLoggingInterceptor,
      authenticationInterceptor: AuthenticationInterceptor
  ): OkHttpClient {

    //TODO: Add pinning for versions lower than M
    val hostname = "**.petfinder.com" //Two asterisks matches any number of subdomains
    val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/U8zLlKBQLcRpbcte+Y0kpfoe0pMz+ABQqhAdPlPtf7M=")
        .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        .build()

    //TODO: Add certificate transparency here
    val ctInterceptor = certificateTransparencyInterceptor {
      // Enable for the provided hosts
      +"*.petfinder.com" //1 For subdomains
      +"petfinder.com" //2 asterisk does not cover base domain
      //+"*.*" - this will add all hosts
      //-"legacy.petfinder.com" //3 Exclude specific hosts
    }

    return OkHttpClient.Builder()
        .certificatePinner(certificatePinner)
        .addNetworkInterceptor(ctInterceptor)
        .addInterceptor(authenticationInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .cache(null)
        .build()
  }

  @Provides
  fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(loggingInterceptor)

    interceptor.level = HttpLoggingInterceptor.Level.BODY

    return interceptor
  }
}