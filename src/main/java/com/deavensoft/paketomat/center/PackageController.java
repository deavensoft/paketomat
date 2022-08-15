package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.dto.PackageDTO;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.center.model.User;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.exceptions.NoSuchUserException;
import com.deavensoft.paketomat.exceptions.PaketomatException;
import com.deavensoft.paketomat.mapper.PackageMapper;
import com.deavensoft.paketomat.user.UserService;
import com.deavensoft.paketomat.exceptions.NoSuchPackageException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/packages")
@Slf4j
@AllArgsConstructor
public class PackageController {

    private final PackageService packageService;
    private static final String MESSAGE = "Package with id ";
    private final UserService userService;
    private final PackageMapper packageMapper;

    @GetMapping
    @Operation(summary = "Get packages", description = "Get all packages")
    @ApiResponse(responseCode = "200", description = "All packages are returned")
    public List<PackageDTO> getAllPackages() {
        List<Package> packages = packageService.getAllPackages();
        List<PackageDTO> packageDTOS = new ArrayList<>();

        for (Package pa : packages) {
            packageDTOS.add(packageMapper.packageToPackageDTO(pa));
        }
        log.info("All packages are returned");
        return packageDTOS;
    }

    @PostMapping
    @Operation(summary = "Add new package", description = "Add new package to the distributive center")
    @ApiResponse(responseCode = "200", description = "New package added")
    public int savePackage(@RequestBody PackageDTO newPackage) throws PaketomatException {
        Package p = packageMapper.packageDTOToPackage(newPackage);
        p.setPaid(Paid.NOT_PAID);
        p.setStatus(Status.NEW);
        packageService.save(p);
        log.info("New package added to the database");

        Optional<User> user = userService.findUserById(p.getUser().getId());
        if (user.isEmpty()) {
            throw new NoSuchUserException("There is no user with id " + newPackage.getUser().getId(), HttpStatus.OK, 200);
        } else {
            String messages = "User exist";
            log.info(messages);
            EmailDetails emailDetails = new EmailDetails();
            Map<String, Object> model = new HashMap<>();
            emailDetails.setMsgBody("Package arrived at the distribution center");
            emailDetails.setRecipient(user.get().getEmail());
            emailDetails.setSubject("test");
            model.put("msgBody", emailDetails.getMsgBody());
            return 1;
        }
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get package", description = "Get package with specified id")
    @ApiResponse(responseCode = "200", description = "Package with specified id returned")
    public PackageDTO getPackageById(@PathVariable(name = "id") Long id) throws NoSuchPackageException {
        Optional<Package> p = packageService.findPackageById(id);

        if (p.isEmpty()) {
            throw new NoSuchPackageException("There is no package with id " + id, HttpStatus.OK, 200);
        } else {
            Package pa = p.get();
            PackageDTO packageDTO = packageMapper.packageToPackageDTO(pa);
            String mess = MESSAGE + id + " is returned";
            log.info(mess);
            return packageDTO;
        }
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete package", description = "Delete package with specified id")
    @ApiResponse(responseCode = "200", description = "Package with specified id deleted")
    public int deletePackageById(@PathVariable(name = "id") Long id) throws NoSuchPackageException {
        try {
            packageService.deletePackageById(id);
            String mess = MESSAGE + id + " is deleted";
            log.info(mess);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchPackageException(MESSAGE + id + " can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return 1;
    }

    @DeleteMapping
    @Operation(summary = "Delete packages", description = "Delete all packages")
    @ApiResponse(responseCode = "200", description = "All packages are deleted from database")
    public int deleteAllPackages() {
        packageService.deleteAll();
        return 1;
    }

    @GetMapping(path = "/pay")
    @Operation(summary = "Pay for package", description = "Pay for package with specified id")
    public void payForThePackage(@RequestParam(name = "id") Long id) {
        packageService.payment(id, Paid.PAID);

    }
}
