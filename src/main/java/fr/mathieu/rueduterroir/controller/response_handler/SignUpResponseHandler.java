package fr.mathieu.rueduterroir.controller.response_handler;

import fr.mathieu.rueduterroir.controller.response_handler.constant.SignUpResponseConstant;
import fr.mathieu.rueduterroir.model.entity.Tenant;
import fr.mathieu.rueduterroir.utils.LoggerUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class SignUpResponseHandler {

    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException dve) {
        LoggerUtil.Error("Data integrity violation during tenant signup: " + dve.getMessage());
        if (dve.getMessage().contains("address")) {
            return ResponseEntity.badRequest().body(SignUpResponseConstant.ADDRESS_OCCUPIED);
        } else if (dve.getMessage().contains("garage")) {
            return ResponseEntity.badRequest().body(SignUpResponseConstant.GARAGE_OCCUPIED);
        } else {
            return ResponseEntity.internalServerError().body(SignUpResponseConstant.ERROR_SUBSCRIPTION);
        }
    }

    public ResponseEntity<String> generateResponse(Tenant savedTenant) {
        if (savedTenant != null) {
            LoggerUtil.Success("New tenant was saved");
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } else {
            LoggerUtil.Error("Error during subscription, the tenant was not saved");
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
