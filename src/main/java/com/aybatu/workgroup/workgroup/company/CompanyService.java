/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aybatu.workgroup.workgroup.company;

import com.aybatu.workgroup.workgroup.admin.Admin;
import com.aybatu.workgroup.workgroup.userAccountRequests.AccountTypes;
import com.aybatu.workgroup.workgroup.company.employee.Employee;
import com.aybatu.workgroup.workgroup.company.employee.EmployeeRepository;
import com.aybatu.workgroup.workgroup.manager.Manager;
import com.aybatu.workgroup.workgroup.userAccountRequests.UserAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

/**
 *
 * @author aybatukerkukluoglu
 */
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public Company registerCompany(Company company) {

        return companyRepository.save(company);
    }

    public Company getCompanyByRegistrationNumber(String registrationNumber) {
        // Implement the logic to retrieve the company from the repository using the registration number
        // Return the retrieved company or null if not found
        return companyRepository.findByRegistrationNumber(registrationNumber);
    }

    public AccountTypes authenticateUser(String emailAddress, String password, String registrationNumber) {
        Company company = getCompanyByRegistrationNumber(registrationNumber);

        if (company != null) {
            // Check if the provided credentials match the owner account
            if (company.getOwnerAccount().getEmailAddress().equals(emailAddress)
                    && company.getOwnerAccount().getPassword().equals(password)) {
                return AccountTypes.ADMIN;
            }

            // Check the employee accounts
            for (Employee employee : company.getEmployeeAccounts()) {
                if (employee.getEmailAddress().equals(emailAddress)
                        && employee.getPassword().equals(password)) {
                    return AccountTypes.EMPLOYEE;
                }
            }

            // Check the manager accounts
            for (Manager manager : company.getManagerAccounts()) {
                if (manager.getEmailAddress().equals(emailAddress)
                        && manager.getPassword().equals(password)) {
                    return AccountTypes.MANAGER;
                }
            }
        }

        // No matching account found
        return null;
    }

    public boolean addEmployeeAccount(String registrationNumber, Employee employeeAccount) {
        Company company = companyRepository.findByRegistrationNumber(registrationNumber);
        List<Employee> employeeAccounts = company.getEmployeeAccounts();

        if (!isEmailAddressRegisted(employeeAccount.getEmailAddress(), company)) {
            employeeAccounts.add(employeeAccount);
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    public boolean addManagerAccount(String registrationNumber, Manager managerAccount) {
        Company company = companyRepository.findByRegistrationNumber(registrationNumber);
        List<Manager> managerAccounts = company.getManagerAccounts();

        if (!isEmailAddressRegisted(managerAccount.getEmailAddress(), company)) {
            managerAccounts.add(managerAccount);
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    public boolean deleteManagerAccount(String registrationNumber, Manager managerAccount) {
        Company company = companyRepository.findByRegistrationNumber(registrationNumber);
        List<Manager> managerAccounts = company.getManagerAccounts();

        if (isEmailAddressRegisted(managerAccount.getEmailAddress(), company)) {
            managerAccounts.remove(managerAccount);
            companyRepository.save(company);
            return true;
        }

        return false;
    }

    public boolean updateUserAccount(String companyRegistrationNumber, UserAccount userAccount, String originalEmailAddress, AccountTypes originalAccountType) {
        Company company = companyRepository.findByRegistrationNumber(companyRegistrationNumber);

        if (company == null) {
            // Company not found with the given registration number
            return false;
        }

        List<Employee> employeeAccounts = company.getEmployeeAccounts();
        List<Manager> managerAccounts = company.getManagerAccounts();

        if (originalAccountType.equals(AccountTypes.MANAGER)) {
            Manager manager = company.getManagerAccounts().stream()
                    .filter(foundUserAccount -> originalEmailAddress.equals(foundUserAccount.getEmailAddress()))
                    .findFirst()
                    .orElse(null);

            if (manager != null) {
                if (!userAccount.getAccountType().equals(AccountTypes.MANAGER.toString())) {
                    // Move the user to the employee accounts list
                    Employee newEmployee = new Employee(
                            userAccount.getEmailAddress(),
                            userAccount.getUserFirstName(),
                            userAccount.getUserLastName(),
                            userAccount.getPassword()
                    );
                    employeeAccounts.add(newEmployee);
                    managerAccounts.remove(manager);
                } else {
                    // Update the manager account with the new information
                    manager.setEmailAddress(userAccount.getEmailAddress());
                    manager.setUserFirstName(userAccount.getUserFirstName());
                    manager.setUserLastName(userAccount.getUserLastName());
                    manager.setPassword(userAccount.getPassword());
                }
            }
        } else {
            Employee employee = company.getEmployeeAccounts().stream()
                    .filter(foundUserAccount -> originalEmailAddress.equals(foundUserAccount.getEmailAddress()))
                    .findFirst()
                    .orElse(null);

            if (employee != null) {
                if (!userAccount.getAccountType().equals(AccountTypes.EMPLOYEE.toString())) {
                    // Move the user to the manager accounts list
                    Manager newManager = new Manager(
                            userAccount.getEmailAddress(),
                            userAccount.getUserFirstName(),
                            userAccount.getUserLastName(),
                            userAccount.getPassword()
                    );
                    managerAccounts.add(newManager);
                    employeeAccounts.remove(employee);
                } else {
                    // Update the employee account with the new information
                    employee.setEmailAddress(userAccount.getEmailAddress());
                    employee.setUserFirstName(userAccount.getUserFirstName());
                    employee.setUserLastName(userAccount.getUserLastName());
                    employee.setPassword(userAccount.getPassword());
                }
            }
        }

        // Save the updated company object in the database
        companyRepository.save(company);
        return true;
    }

    public boolean deleteEmployeeAccount(String registrationNumber, Employee employeeAccount) {
        Company company = companyRepository.findByRegistrationNumber(registrationNumber);
        List<Employee> employeeAccounts = company.getEmployeeAccounts();

        if (isEmailAddressRegisted(employeeAccount.getEmailAddress(), company)) {
            employeeAccounts.remove(employeeAccount);
            companyRepository.save(company);
            return true;
        }

        return false;
    }

    public boolean isEmailAddressRegisted(String emailAddress, Company company) {

        List<Employee> employeeList = company.getEmployeeAccounts();
        List<Manager> managerList = company.getManagerAccounts();
        Admin ownerAccount = company.getOwnerAccount();

        if (!employeeList.isEmpty()) {
            for (Employee e : employeeList) {
                if (e.getEmailAddress().equalsIgnoreCase(emailAddress)) {
                    return true;
                }
            }
        }
        if (ownerAccount.getEmailAddress().equalsIgnoreCase(emailAddress)) {
            return true;
        }
        if (!managerList.isEmpty()) {
            for (Manager m : managerList) {
                if (m.getEmailAddress().equalsIgnoreCase(emailAddress)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    public Company findCompanyByOwnerAccountEmailAddress(String emailAddress) {
        return companyRepository.findByOwnerAccountEmailAddress(emailAddress);
    }
}
