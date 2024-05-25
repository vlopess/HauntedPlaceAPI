package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.Models.StringWrapper;
import com.hauntedplace.HauntedPlaceAPI.Services.FirebaseStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FirebaseStorageService firebaseStorageService;

    @Autowired
    public FileController(FirebaseStorageService firebaseStorageService) {
        this.firebaseStorageService = firebaseStorageService;
    }


    @PostMapping("/files/upload")
    @Operation(summary = "Autorização necessária", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> uploadFileUser(@RequestParam("file") MultipartFile file){
        StringWrapper filePath = firebaseStorageService.upload(file);
        return ResponseEntity.ok().body(filePath);
    }

    @PostMapping("/files/remove/{filename}")
    @Operation(summary = "Autorização necessária", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> removeFileUser(@PathVariable String filename) throws Exception {
        StringWrapper filePath = firebaseStorageService.remove(filename);
        return ResponseEntity.ok().body(filePath);
    }
}
