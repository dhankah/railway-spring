
package com.mospan.railwayspring.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
    public SecurityWebInitializer() {
        super(WebSecurityConfig.class);
    }
}


