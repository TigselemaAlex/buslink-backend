package com.cdg.buslinkbackend;

import com.cdg.buslinkbackend.model.entity.Role;
import com.cdg.buslinkbackend.model.enums.RoleType;
import com.cdg.buslinkbackend.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BusLinkBackendApplication {

    /**
     * If the database is empty, add the roles to the database
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication
                .run(BusLinkBackendApplication.class, args);
        RoleRepository roleRepository = configurableApplicationContext.getBean(RoleRepository.class);
        if (((List<Role>) roleRepository.findAll()).isEmpty()) {
            List<Role> roleList = new ArrayList<>(List.of(
                    new Role(RoleType.ANT_ADMIN),
                    new Role(RoleType.USER),
                    new Role(RoleType.ANT),
                    new Role(RoleType.OFFICE_WORKER),
                    new Role(RoleType.BUS_CONTROLLER),
                    new Role(RoleType.BUS_ADMIN)));
            roleRepository.saveAll(roleList);
        }

    }

}
