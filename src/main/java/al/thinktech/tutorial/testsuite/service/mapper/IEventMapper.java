package al.thinktech.tutorial.testsuite.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import al.thinktech.tutorial.testsuite.repository.model.EventModel;
import al.thinktech.tutorial.testsuite.service.dto.CreateEventDto;
import al.thinktech.tutorial.testsuite.service.dto.EventDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IEventMapper {
	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "description", target = "description")
	@Mapping(source = "startedAt", target = "startedAt")
	@Mapping(source = "finishedAt", target = "finishedAt")
	public EventDto toDto(EventModel model);

	@Mapping(source = "name", target = "name")
	@Mapping(source = "description", target = "description")
	public EventModel toModel(CreateEventDto dto);

	@Mapping(source = "name", target = "model.name")
	@Mapping(source = "description", target = "model.description")
	public EventModel toModel(EventDto dto, @MappingTarget EventModel model);

	public default List<EventDto> toDtoList(List<EventModel> models) {
		return models.stream().map(this::toDto).collect(Collectors.toList());
	}
}
