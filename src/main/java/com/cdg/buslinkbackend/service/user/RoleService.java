package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.exception.RoleNotFoundException;
import com.cdg.buslinkbackend.model.entity.Role;
import com.cdg.buslinkbackend.model.enums.RoleType;
import com.cdg.buslinkbackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * It returns a collection of roles that are either ant_admin, ant, or bus_admin
 */
@Service
@Transactional(readOnly = true)
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * It returns a collection of roles that are either ant_admin, ant, or bus_admin
     * 
     * @return A collection of roles.
     */
    public Collection<Role> findAllAdmins() {
        Collection<RoleType> roleList = new ArrayList<>(List.of(
                RoleType.ANT_ADMIN,
                RoleType.ANT,
                RoleType.BUS_ADMIN));
        return roleRepository.findByNameIn(roleList);
    }

    /**
     * It returns a collection of roles that are either an office worker, bus
     * controller, or bus admin
     * 
     * @return A collection of roles.
     */
    public Collection<Role> findAllBusUsers() {
        Collection<RoleType> roleList = new ArrayList<>(List.of(
                RoleType.OFFICE_WORKER,
                RoleType.BUS_CONTROLLER,
                RoleType.BUS_ADMIN));
        return roleRepository.findByNameIn(roleList);
    }

    /**
     * If the role is found, return it. If not, throw an exception
     * 
     * @param id The id of the role you want to find.
     * @return A Role object
     */
    public Role findById(String id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.orElseThrow(() -> new RoleNotFoundException(id));
    }
}
