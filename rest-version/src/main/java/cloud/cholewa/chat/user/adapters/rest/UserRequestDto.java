package cloud.cholewa.chat.user.adapters.rest;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequestDto {

    @NotNull
    @Size(min = 3, max = 50, message = "Invalid length of nick. Should be between 3 and 50")
    private String nick;

    @NotNull
    @Size(min = 5, message = "Password min length is 5")
    private String password;
}
