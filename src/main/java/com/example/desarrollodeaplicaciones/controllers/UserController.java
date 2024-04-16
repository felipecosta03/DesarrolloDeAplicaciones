package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.ErrorMessageDTO;
import com.example.desarrollodeaplicaciones.dtos.ErrorMessageValidationDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.services.IUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
public class UserController {

  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @PostMapping()
  public ResponseEntity<StatusDTO> add(@RequestBody UserDTO user) {
    StatusDTO statusDTO = userService.add(user);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }


  @PutMapping
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User updated"),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Database error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid user data",
            content = {
              @Content(schema = @Schema(implementation = ErrorMessageValidationDTO.class))
            })
      })
  public ResponseEntity<StatusDTO> update(@Valid @RequestBody UserDTO user) {
    StatusDTO statusDTO = userService.update(user);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @GetMapping("/{id}")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Retrieve user by id"),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
    return ResponseEntity.status(200).body(userService.findById(id));
  }

  @PatchMapping("/{id}/image")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User image updated"),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Image upload error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Database error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<StatusDTO> updateImage(
      @PathVariable Long id, @RequestParam("image") MultipartFile image) {
    StatusDTO statusDTO = userService.updateUserImage(id, image);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{id}/image")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User image deleted"),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Delete image error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Database error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<StatusDTO> deleteImage(@PathVariable Long id) {
    StatusDTO statusDTO = userService.deleteImage(id);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{id}")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User deleted"),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Database error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<StatusDTO> delete(@PathVariable Long id) {
    StatusDTO statusDTO = userService.delete(id);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }
}
