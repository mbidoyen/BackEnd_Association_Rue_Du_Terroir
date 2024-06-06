package fr.mathieu.rueduterroir.controller;

import fr.mathieu.rueduterroir.controller.constant.ResponseConstant;
import fr.mathieu.rueduterroir.model.dto.image_file.RegistrationImageUploadDTO;
import fr.mathieu.rueduterroir.model.dto.tenant.TenantSubscription;
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
import fr.mathieu.rueduterroir.utils.FileUtil;
import fr.mathieu.rueduterroir.utils.LoggerUtil;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

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
    @Value("${image.repository.base.url}")
    private String imageDirectoryUrl;


    @Autowired
    public TenantController(ITenantService tenantService, IAddressService addressService, IRoleService roleService, IImageFileService imageFileService, TenantMapper tenantMapper, ImageFileMapper imageFileMapper, PasswordEncoder passwordEncoder) {
        this.tenantService = tenantService;
        this.addressService = addressService;
        this.roleService = roleService;
        this.imageFileService = imageFileService;
        this.tenantMapper = tenantMapper;
        this.imageFileMapper = imageFileMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody TenantSubscription tenantSubscription, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException((Throwable) bindingResult);
        }

        try {
            Optional<Address> address = addressService.getAddressById(tenantSubscription.getAddressId());
            if (address.isEmpty()) {
                return ResponseEntity.badRequest().body(ResponseConstant.INVALID_ADDRESS);
            }

            if (tenantService.findByAddress(address.get()).isPresent()) {
                return ResponseEntity.badRequest().body(ResponseConstant.ADDRESS_OCCUPIED);
            }

            Optional<Role> defaultRole = roleService.getRoleByName(AuthorityConstant.TENANT);
            if (defaultRole.isEmpty()) {
                return ResponseEntity.internalServerError().body(ResponseConstant.ERROR_SUBSCRIPTION);
            }

            if (tenantSubscription.getImageFileDto() != null) {
                RegistrationImageUploadDTO imageUploadDTO = tenantSubscription.getImageFileDto();
                String uploadDir = imageDirectoryUrl + "/user_profile/" + tenantSubscription.getEmail();
                String uniqueFilename = UUID.randomUUID() + "_" + imageUploadDTO.getFilename();
                boolean isSaved = FileUtil.saveFile(uploadDir, uniqueFilename, imageUploadDTO.getData());
                if (!isSaved) {
                    return ResponseEntity.internalServerError().body(ResponseConstant.ERROR_UPLOAD_FILE_TO_DIRECTORY);
                }
                imageUploadDTO.setFilename(uniqueFilename);
            }

            Tenant newTenant = tenantMapper.tenantSubscriptiontoTenant(tenantSubscription);
            newTenant.setPassword(passwordEncoder.encode(tenantSubscription.getPassword()));
            newTenant.setIsArchived(false);
            newTenant.setIsEnabled(true);
            newTenant.setAddress(address.get());
            newTenant.addRole(defaultRole.get());

            Tenant savedTenant = tenantService.save(newTenant);

            ImageFile newImageFile = imageFileMapper.RegistrationImageUploadDTOToImageFile(tenantSubscription.getImageFileDto());
            newImageFile.setTenant(savedTenant);
            newImageFile.setIsArchived(false);
            imageFileService.save(newImageFile);


            if (savedTenant != null) {
                LoggerUtil.Success("New tenant was saved");
                return new ResponseEntity<>("Success", HttpStatus.CREATED);
            }

            LoggerUtil.Error("Error during subscription, the tenant was not saved");
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (ValidationException ve) {
            LoggerUtil.Error("Validation error during tenant signup: " + ve.getMessage());
            return ResponseEntity.badRequest().body("Invalid data provided");
        } catch (DataIntegrityViolationException dve) {
            LoggerUtil.Error("Data integrity violation during tenant signup: " + dve.getMessage());
            if (dve.getMessage().contains("address")) {
                return ResponseEntity.badRequest().body(ResponseConstant.ADDRESS_OCCUPIED);
            } else if (dve.getMessage().contains("garage")) {
                return ResponseEntity.badRequest().body(ResponseConstant.GARAGE_OCCUPIED);
            } else {
                return ResponseEntity.internalServerError().body(ResponseConstant.ERROR_SUBSCRIPTION);
            }
        } catch (Exception e) {
            LoggerUtil.Error("Error during tenant signup: " + e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseConstant.ERROR_SUBSCRIPTION);
        }
    }
}
