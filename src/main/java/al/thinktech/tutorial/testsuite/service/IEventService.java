package al.thinktech.tutorial.testsuite.service;

import java.util.List;

import al.thinktech.tutorial.testsuite.service.dto.CreateEventDto;
import al.thinktech.tutorial.testsuite.service.dto.EventDto;

public interface IEventService {

	public EventDto findById(Long id);

	public List<EventDto> findAll();

	public EventDto createEvent(CreateEventDto dto);

	public void startEvent(Long id);

	public void finishEvent(Long id);

	public EventDto updateEvent(Long id, EventDto dto);

	public void deleteEvent(Long id);
}
