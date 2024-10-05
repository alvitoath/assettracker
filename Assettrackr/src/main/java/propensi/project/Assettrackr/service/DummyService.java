package propensi.project.Assettrackr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Admin;
import propensi.project.Assettrackr.model.Developer;
import propensi.project.Assettrackr.model.Operator;
import propensi.project.Assettrackr.model.SecurityOfficer;
import propensi.project.Assettrackr.model.Role;
import propensi.project.Assettrackr.repository.*;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class DummyService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminDb;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private SecurityOfficerRepository securityOfficerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void createUser() {
        var admin1 = new Admin();
        admin1.setUsername("admin1");

        // Encode the password before setting it
        String encodedPassword = passwordEncoder.encode("admin1");  // Encoding the password
        admin1.setPassword(encodedPassword);

        admin1.setNama("Admin 1");
        admin1.setEmail("admin1@gmail.com");
        admin1.setTanggal_bergabung("17 Agustus 1945");
        admin1.setTanggal_lahir("17 Agustus 1945");

        // Assuming you have fetched the 'RoleModel' corresponding to the admin role (id = 1)
        Optional<Role> adminRole = roleRepository.findRoleById(1);
        if (adminRole.isPresent()) {
            admin1.setRole(adminRole.get());  // Extract the RoleModel from the Optional and set it
        } else {
            // Handle the case where the role is not found, e.g., throw an exception or set a default role
            throw new RuntimeException("Role with ID 1 not found");
        }

        // Set additional fields if needed
        admin1.setFoto(null); // Set to actual photo byte array if available

        // Save the admin1 object to the database
        adminDb.save(admin1);

        var developer1 = new Developer();
        developer1.setUsername("developer1");

        // Encode the password before setting it
        encodedPassword = passwordEncoder.encode("developer1");  // Encoding the password
        developer1.setPassword(encodedPassword);

        developer1.setNama("Admin 1");
        developer1.setEmail("develper1@gmail.com");
        developer1.setTanggal_bergabung("17 Agustus 1945");
        developer1.setTanggal_lahir("17 Agustus 1945");
        developer1.setBidangKeahlian("Back End");

        // Assuming you have fetched the 'RoleModel' corresponding to the admin role (id = 1)
        Optional<Role> developerRole = roleRepository.findRoleById(1);
        if (developerRole.isPresent()) {
            developer1.setRole(developerRole.get());  // Extract the RoleModel from the Optional and set it
        } else {
            // Handle the case where the role is not found, e.g., throw an exception or set a default role
            throw new RuntimeException("Role with ID 1 not found");
        }

        // Set additional fields if needed
        developer1.setFoto(null); // Set to actual photo byte array if available

        // Save the admin1 object to the database
        developerRepository.save(developer1);

        var securityOfficer1 = new SecurityOfficer();
        securityOfficer1.setUsername("securityOfficer1");

// Encode the password before setting it
        encodedPassword = passwordEncoder.encode("securityOfficer1");  // Encoding the password
        securityOfficer1.setPassword(encodedPassword);

        securityOfficer1.setNama("Security Officer 1");
        securityOfficer1.setEmail("securityofficer1@gmail.com");
        securityOfficer1.setTanggal_bergabung("17 Agustus 1945");
        securityOfficer1.setTanggal_lahir("17 Agustus 1945");

// Assuming you have fetched the 'RoleModel' corresponding to the security officer role (id = 2)
        Optional<Role> securityOfficerRole = roleRepository.findRoleById(4);
        if (securityOfficerRole.isPresent()) {
            securityOfficer1.setRole(securityOfficerRole.get());  // Extract the RoleModel from the Optional and set it
        } else {
            // Handle the case where the role is not found, e.g., throw an exception or set a default role
            throw new RuntimeException("Role with ID 2 not found");
        }

// Set additional fields if needed
        securityOfficer1.setFoto(null); // Set to actual photo byte array if available

// Save the securityOfficer1 object to the database
        securityOfficerRepository.save(securityOfficer1);

        var operator1 = new Operator();
        operator1.setUsername("operator1");

// Encode the password before setting it
        encodedPassword = passwordEncoder.encode("operator1");  // Encoding the password
        operator1.setPassword(encodedPassword);

        operator1.setNama("Operator 1");
        operator1.setEmail("operator1@gmail.com");
        operator1.setTanggal_bergabung("17 Agustus 1945");
        operator1.setTanggal_lahir("17 Agustus 1945");

// Assuming you have fetched the 'RoleModel' corresponding to the operator role (id = 3)
        Optional<Role> operatorRole = roleRepository.findRoleById(3);
        if (operatorRole.isPresent()) {
            operator1.setRole(operatorRole.get());  // Extract the RoleModel from the Optional and set it
        } else {
            // Handle the case where the role is not found, e.g., throw an exception or set a default role
            throw new RuntimeException("Role with ID 3 not found");
        }

// Set additional fields if needed
        operator1.setFoto(null); // Set to actual photo byte array if available

// Save the operator1 object to the database
        operatorRepository.save(operator1);


    }
}
