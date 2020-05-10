package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        if (authentication.getPrincipal() instanceof AuthUser) {

            var authUser = (AuthUser) authentication.getPrincipal();
            var info = new HashMap<String, Object>();
            var oauth2AcessToken = (DefaultOAuth2AccessToken) accessToken;

            info.put("full_name", authUser.getFullName());
            info.put("user_id", authUser.getId());

            oauth2AcessToken.setAdditionalInformation(info);
        }

        return accessToken;
    }
}