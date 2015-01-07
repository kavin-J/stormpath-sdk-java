/*
 * Copyright 2014 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.sdk.servlet.filter.account;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.lang.Assert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;

public class DefaultJwtAccountResolver implements JwtAccountResolver {

    private final JwtSigningKeyResolver jwtSigningKeyResolver;

    public DefaultJwtAccountResolver(JwtSigningKeyResolver jwtSigningKeyResolver) {
        Assert.notNull(jwtSigningKeyResolver, "JwtSigningKeyResolver cannot be null.");
        this.jwtSigningKeyResolver = jwtSigningKeyResolver;
    }

    protected JwtSigningKeyResolver getJwtSigningKeyResolver() {
        return this.jwtSigningKeyResolver;
    }

    @Override
    public Account getAccountByJwt(final HttpServletRequest request, final HttpServletResponse response, String jwt) {

        final JwtSigningKeyResolver resolver = getJwtSigningKeyResolver();

        SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {

            @Override
            public Key resolveSigningKey(JwsHeader header, Claims claims) {
                return resolver.getSigningKey(request, response, header, claims);
            }
        };

        Claims claims = Jwts.parser().setSigningKeyResolver(signingKeyResolver).parseClaimsJws(jwt).getBody();

        String accountHref = claims.getSubject();

        //will hit the cache:
        Client client = getClient(request);
        return client.getResource(accountHref, Account.class);
    }

    protected Client getClient(HttpServletRequest request) {
        return (Client)request.getAttribute(Client.class.getName());
    }
}