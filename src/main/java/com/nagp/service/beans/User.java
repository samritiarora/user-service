package com.nagp.service.beans;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The type User entity.
 */
@lombok.Data
public class User
{

    private UUID id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Age is mandatory")
    @Min(value = 0, message = "The value must be positive")
    private int age;
    @NotBlank(message = "Address is mandatory")
    private String address;

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof User)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        User c = (User) o;

        // Compare the data members and return accordingly
        return name.compareTo(c.name) == 0
                && Integer.compare(age, c.age) == 0
                && address.compareTo(c.address) ==0;
    }
}
