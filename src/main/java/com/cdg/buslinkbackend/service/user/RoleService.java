package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.model.entity.Role;
import com.cdg.buslinkbackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll(){
        return (List<Role>) roleRepository.findAll();
    }

    public Role findById(String id){
        return roleRepository.findById(id).get();
    }
}
