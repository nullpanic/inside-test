package dev.nullpanic.insidetest.mapper;

import dev.nullpanic.insidetest.dto.UserDTO;
import dev.nullpanic.insidetest.persist.models.User;
import dev.nullpanic.insidetest.requests.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "messages", source = "messages")
    UserDTO mapToDto(User user);

    User mapToEntity(UserRequest userRequest);


}
