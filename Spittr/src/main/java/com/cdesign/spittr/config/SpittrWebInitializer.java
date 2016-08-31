package com.cdesign.spittr.config;

import com.cdesign.spittr.data.entity.Spitter;
import com.cdesign.spittr.data.entity.UserRole;
import com.cdesign.spittr.data.repo.SpitterRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * Created by Ageev Evgeny on 27.08.2016.
 */
public class SpittrWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic reg) {
        reg.setMultipartConfig(new MultipartConfigElement("/tmp/spittr/uploads",
                2097152, 4194304, 0));
    }

    @Bean
    public InitializingBean insertDefaultUsers() {
        return new InitializingBean() {
            @Autowired
            private SpitterRepository spitterRepository;
            @Autowired
            private PasswordEncoder passwordEncoder;

            @Override
            public void afterPropertiesSet() {
                addUser("admin", "admin", UserRole.ROLE_ADMIN);
            }

            private void addUser(String username, String password, UserRole userRole) {
                if (spitterRepository.findByUsername(username) == null) {
                    Spitter spitter = new Spitter(
                            username,
                            password,
                            username,
                            username,
                            username + "@" + username + ".ru"
                    );
                    spitterRepository.save(spitter);
                }
            }
        };
    }
}
