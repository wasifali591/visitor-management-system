package in.theexplorers.vms.authservice.user.converter;

import in.theexplorers.vms.authservice.user.dto.CreateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UserResponse;
import in.theexplorers.vms.authservice.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting User DTO to Entity.
 */
@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    /**
     * Constructs a new {@link UserConverter} with the given {@link ModelMapper}.
     *
     * @param modelMapper the {@link ModelMapper} instance used for converting between entities and DTOs
     */
    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts CreateUserRequest to User entity.
     */
    public User toEntity(CreateUserRequest request) {
        return modelMapper.map(request, User.class);
    }

    /**
     * Converts entity to response.
     */
    public UserResponse toResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}