package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.Role;
import com.scp.patientmanagement.model.User;
import com.scp.patientmanagement.repository.UserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashSet;

@ComponentScan
@EnableTransactionManagement
@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    private final RoleServiceInterface roleService;

    private EntityManager entityManager;


    public UserService(EntityManager entityManager, UserRepository userRepository, RoleServiceInterface roleService) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    /**
     * @return UserRepository
     */
    public UserRepository getRepository() {
        return this.userRepository;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Create a user
     * @param User user
     * @return User
     * @throws RuntimeException
     */
    @Override
    public User save (User user) throws RuntimeException {
        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));

            HashSet<Role> listRoles = this.foundRoles(user);
            if (!user.getRoles().isEmpty() && !listRoles.isEmpty()) {
                user.setRoles(listRoles);
            }

            this.entityManager.persist(user);
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return user;
    }

    /**
     * Edit a user
     * @param User userEdit
     * @param User user
     * @return User
     * @throws RuntimeException
     */
    @Override
    public User edit(User userEdit, User user) throws RuntimeException {
        try {
            if (user.getUserLogin() != null && user.getUserLogin() != "") {
                userEdit.setUserLogin(user.getUserLogin());
            }

            if (user.getUserPassword() != null && user.getUserPassword() != "") {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                userEdit.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
            }

            HashSet<Role> listRoles = this.foundRoles(user);
            if (!user.getRoles().isEmpty() && !listRoles.isEmpty()) {
                userEdit.setRoles(listRoles);
            }

            Date now = new Date();
            userEdit.setUpdatedAt(now);

            this.entityManager.merge(userEdit);
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return userEdit;
    }

    /**
     * Remove a user
     * @param User user
     * @return boolean
     * @throws RuntimeException
     */
    @Override
    public boolean remove(User user) throws RuntimeException {
        try {
            this.entityManager.remove(this.entityManager.merge(user));
            this.entityManager.flush();
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return true;
    }

    /**
     * Finding each role for user
     * @param User user
     * @return HashSet<Role>
     */
    private HashSet<Role> foundRoles(User user) {
        HashSet<Role> listRoles = new HashSet<>();

        user.getRoles().forEach((role) -> {
            Role roleFound = roleService.getRepository().findById(role.getRoleId()).orElse(null);
            if (roleFound != null) {
                listRoles.add(roleFound);
            }
        });

        return listRoles;
    }
}
