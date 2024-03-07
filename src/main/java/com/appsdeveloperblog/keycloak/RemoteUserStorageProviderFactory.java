package com.appsdeveloperblog.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;
import org.keycloak.utils.StringUtil;

import java.util.List;

public class RemoteUserStorageProviderFactory implements UserStorageProviderFactory<RemoteUserStorageProvider> {

    public static final String PROVIDER_NAME = "my-remote-user-storage-provider";

    @Override
    public RemoteUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new RemoteUserStorageProvider(session, model, new UsersApiLegacyService(session, model));
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    @Override
    public String getHelpText() {
        return "My Remote User Storage provider Sample";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create()
                .property(Constants.SERVICE_URL, "Service URL", "Authentication için consume edilecek harici REST Service URL bilgisi giriniz.", ProviderConfigProperty.STRING_TYPE, "", null)
				.property(Constants.AUTH_TOKEN, "Auth Token", "Harici REST serviste kullanilacak gecerli token", ProviderConfigProperty.STRING_TYPE, "", null)
//				.property(Constants.AUTH_PASSWORD, "BasicAuth Password", "Password for BasicAuth at the API", ProviderConfigProperty.PASSWORD, "", null)
                .build();
    }

    @Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config) throws ComponentValidationException {
        if (StringUtil.isBlank(config.get(Constants.SERVICE_URL))
				|| StringUtil.isBlank(config.get(Constants.AUTH_TOKEN))
//				|| StringUtil.isBlank(config.get(Constants.AUTH_PASSWORD))
        ) {
            throw new ComponentValidationException("Yapılandırma doğru şekilde ayarlanmamış, lütfen doğrulayın.");
        }
    }
}
