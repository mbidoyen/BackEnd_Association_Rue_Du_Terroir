package fr.mathieu.rueduterroir.controller;

import fr.mathieu.rueduterroir.controller.response_handler.SignUpResponseHandler;
import fr.mathieu.rueduterroir.controller.response_handler.constant.SignUpResponseConstant;
import fr.mathieu.rueduterroir.dto.tenant.TenantSubscription;
import fr.mathieu.rueduterroir.model.entity.Address;
import fr.mathieu.rueduterroir.model.entity.ImageFile;
import fr.mathieu.rueduterroir.model.entity.Role;
import fr.mathieu.rueduterroir.model.entity.Tenant;
import fr.mathieu.rueduterroir.model.mapper.ImageFileMapper;
import fr.mathieu.rueduterroir.model.mapper.TenantMapper;
import fr.mathieu.rueduterroir.security.AuthorityConstant;
import fr.mathieu.rueduterroir.service.IAddressService;
import fr.mathieu.rueduterroir.service.IImageFileService;
import fr.mathieu.rueduterroir.service.IRoleService;
import fr.mathieu.rueduterroir.service.ITenantService;
import fr.mathieu.rueduterroir.utils.LoggerUtil;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@Validated
public class TenantController {
    private final ITenantService tenantService;
    private final IAddressService addressService;
    private final IRoleService roleService;
    private final IImageFileService imageFileService;
    private final TenantMapper tenantMapper;
    private final ImageFileMapper imageFileMapper;
    private final PasswordEncoder passwordEncoder;
    private final SignUpResponseHandler signUpResponseHandler;

    @Autowired
    public TenantController(ITenantService tenantService, IAddressService addressService, IRoleService roleService, IImageFileService imageFileService, TenantMapper tenantMapper, ImageFileMapper imageFileMapper, PasswordEncoder passwordEncoder, SignUpResponseHandler signUpResponseHandler, SignUpResponseHandler signUpResponseHandler1) {
        this.tenantService = tenantService;
        this.addressService = addressService;
        this.roleService = roleService;
        this.imageFileService = imageFileService;
        this.tenantMapper = tenantMapper;
        this.imageFileMapper = imageFileMapper;
        this.passwordEncoder = passwordEncoder;
        this.signUpResponseHandler = signUpResponseHandler1;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody TenantSubscription tenantSubscription, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid data provided");
        }

        try {
            return handleSignup(tenantSubscription);
        } catch (ValidationException ve) {
            LoggerUtil.Error("Validation error during tenant signup: " + ve.getMessage());
            return ResponseEntity.badRequest().body("Invalid data provided");
        } catch (DataIntegrityViolationException dve) {
            return signUpResponseHandler.handleDataIntegrityViolation(dve);
        } catch (Exception e) {
            LoggerUtil.Error("Error during tenant signup: " + e.getMessage());
            return ResponseEntity.internalServerError().body(SignUpResponseConstant.ERROR_SUBSCRIPTION);
        }
    }

    private ResponseEntity<String> handleSignup(TenantSubscription tenantSubscription) {
        Optional<Address> address = addressService.getAddressById(tenantSubscription.getAddressId());
        if (address.isEmpty()) {
            return ResponseEntity.badRequest().body(SignUpResponseConstant.INVALID_ADDRESS);
        }

        if (tenantService.findByAddress(address.get()).isPresent()) {
            return ResponseEntity.badRequest().body(SignUpResponseConstant.ADDRESS_OCCUPIED);
        }

        Optional<Role> defaultRole = roleService.getRoleByName(AuthorityConstant.TENANT);
        if (defaultRole.isEmpty()) {
            return ResponseEntity.internalServerError().body(SignUpResponseConstant.ERROR_SUBSCRIPTION);
        }

        Tenant newTenant = createTenant(tenantSubscription, address.get(), defaultRole.get());
        Tenant savedTenant = tenantService.save(newTenant);

        if (tenantSubscription.getImageFileDto() != null) {
            boolean isImageUploaded = imageFileService.uploadTenantImage(tenantSubscription.getImageFileDto(), tenantSubscription.getEmail());
            if (!isImageUploaded) {
                return ResponseEntity.internalServerError().body(SignUpResponseConstant.ERROR_UPLOAD_FILE_TO_DIRECTORY);
            }
            LoggerUtil.Success("The image has been successfully uploaded");
            ImageFile imageFile = imageFileMapper.imageUploadDTOToImageFile(tenantSubscription.getImageFileDto());
            ImageFile isImageSaved = imageFileService.save(imageFile, savedTenant);
            if (isImageSaved == null){
                return ResponseEntity.internalServerError().body(SignUpResponseConstant.ERROR_SAVE_FILE_TO_BDD);
            }
            LoggerUtil.Success("The image was successfully saved");
        }

        return signUpResponseHandler.generateResponse(savedTenant);
    }

    private Tenant createTenant(TenantSubscription tenantSubscription, Address address, Role defaultRole) {
        Tenant newTenant = tenantMapper.tenantSubscriptiontoTenant(tenantSubscription);
        newTenant.setPassword(passwordEncoder.encode(tenantSubscription.getPassword()));
        newTenant.setIsEnabled(true);
        newTenant.setAddress(address);
        newTenant.addRole(defaultRole);
        return newTenant;
    }
}
