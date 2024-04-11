package com.schedular.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Username is required")
    private String userName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    // Assuming roleType should be one of the predefined values
    @NotBlank(message = "Role type is required")
    private String roleType;

    @Pattern(regexp="(^$|[0-9]{10})", message="Mobile number should be 10 digits")
    private String mobile;

}
