package al.thinktech.tutorial.testsuite.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import al.thinktech.tutorial.testsuite.exception.EventException;
import al.thinktech.tutorial.testsuite.repository.IEventRepository;
import al.thinktech.tutorial.testsuite.repository.model.EventModel;
import al.thinktech.tutorial.testsuite.service.dto.CreateEventDto;
import al.thinktech.tutorial.testsuite.service.dto.EventDto;
import al.thinktech.tutorial.testsuite.service.impl.EventServiceImpl;
import al.thinktech.tutorial.testsuite.service.mapper.IEventMapper;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

	@Mock
	private IEventRepository eventRepo;
	@Mock
	private IEventMapper eventMapper;
	@InjectMocks
	private EventServiceImpl eventService;

	@Test
	public void findById() {
		when(eventRepo.findById(anyLong())).thenReturn(Optional.of(new EventModel()));
		when(eventMapper.toDto(any(EventModel.class))).thenReturn(new EventDto());

		assertNotNull(eventService.findById(1L));

		verify(eventRepo).findById(anyLong());
		verify(eventMapper).toDto(any(EventModel.class));
	}

	@Test
	public void findById_ThrowExceptionNotFound() {
		assertThrows(EventException.class, () -> {
			eventService.findById(1L);
		});
	}

	@Test
	public void findAll() {
		when(eventRepo.findAll()).thenReturn(Arrays.asList(new EventModel()));
		when(eventMapper.toDtoList(anyList())).thenReturn(Arrays.asList(new EventDto()));

		assertTrue(eventService.findAll().size() == 1);

		verify(eventRepo).findAll();
		verify(eventMapper).toDtoList(anyList());
	}

	@Test
	public void createEvent() {
		when(eventMapper.toModel(any(CreateEventDto.class))).thenReturn(new EventModel());
		when(eventRepo.save(any(EventModel.class))).thenReturn(new EventModel());
		when(eventMapper.toDto(any(EventModel.class))).thenReturn(new EventDto());

		assertNotNull(eventService.createEvent(new CreateEventDto()));

		verify(eventMapper).toModel(any(CreateEventDto.class));
		verify(eventRepo).save(any(EventModel.class));
		verify(eventMapper).toDto(any(EventModel.class));

	}

	@Test
	public void startEvent() {
		when(eventRepo.findById(anyLong())).thenReturn(Optional.of(new EventModel()));
		when(eventRepo.save(any(EventModel.class))).thenReturn(new EventModel());

		eventService.startEvent(1L);

		verify(eventRepo).findById(anyLong());
		verify(eventRepo).save(any(EventModel.class));
	}

	@Test
	public void finishEvent() {
		EventModel event = new EventModel();
		event.setStartedAt(Instant.now());
		when(eventRepo.findById(anyLong())).thenReturn(Optional.of(event));
		when(eventRepo.save(any(EventModel.class))).thenReturn(new EventModel());

		eventService.finishEvent(1L);

		verify(eventRepo).findById(anyLong());
		verify(eventRepo).save(any(EventModel.class));

	}

	@Test
	public void finishEvent_ThrowExceptionNotAllowed() {
		when(eventRepo.findById(anyLong())).thenReturn(Optional.of(new EventModel()));

		assertThrows(EventException.class, () -> {
			eventService.finishEvent(1L);
		});

		verify(eventRepo).findById(anyLong());

	}

	@Test
	public void updateEvent() {
		when(eventRepo.findById(anyLong())).thenReturn(Optional.of(new EventModel()));
		when(eventMapper.toModel(any(EventDto.class), any(EventModel.class))).thenReturn(new EventModel());
		when(eventRepo.save(any(EventModel.class))).thenReturn(new EventModel());
		when(eventMapper.toDto(any(EventModel.class))).thenReturn(new EventDto());

		assertNotNull(eventService.updateEvent(1L, new EventDto()));

		verify(eventRepo).findById(anyLong());
		verify(eventMapper).toModel(any(EventDto.class), any(EventModel.class));
		verify(eventRepo).save(any(EventModel.class));
		verify(eventMapper).toDto(any(EventModel.class));

	}

	@Test
	public void deleteEvent() {
		when(eventRepo.findById(anyLong())).thenReturn(Optional.of(new EventModel()));
		doNothing().when(eventRepo).delete(any(EventModel.class));

		eventService.deleteEvent(1L);

		verify(eventRepo).findById(anyLong());

	}

}
