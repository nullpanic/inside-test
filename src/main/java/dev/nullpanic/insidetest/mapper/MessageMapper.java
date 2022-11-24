package dev.nullpanic.insidetest.mapper;

import dev.nullpanic.insidetest.dto.MessageDTO;
import dev.nullpanic.insidetest.persist.models.Message;
import dev.nullpanic.insidetest.requests.MessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "message", source = "message")
    MessageDTO mapToDto(Message message);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "message", source = "message")
    Message mapToEntity(MessageRequest messageRequest);
}
