package al.thinktech.tutorial.testsuite.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import al.thinktech.tutorial.testsuite.exception.EventException;
import al.thinktech.tutorial.testsuite.exception.EventException.EventExceptionType;
import al.thinktech.tutorial.testsuite.repository.IEventRepository;
import al.thinktech.tutorial.testsuite.repository.model.EventModel;
import al.thinktech.tutorial.testsuite.service.IEventService;
import al.thinktech.tutorial.testsuite.service.dto.CreateEventDto;
import al.thinktech.tutorial.testsuite.service.dto.EventDto;
import al.thinktech.tutorial.testsuite.service.mapper.IEventMapper;

@Service
public class EventServiceImpl implements IEventService {
	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	private final IEventRepository eventRepo;
	private final IEventMapper eventMapper;

	@Autowired
	public EventServiceImpl(IEventRepository eventRepo, IEventMapper eventMapper) {
		super();
		this.eventRepo = eventRepo;
		this.eventMapper = eventMapper;
	}

	@Override
	public EventDto findById(Long id) {
		logger.info("Entered findById() with params : id [{}]", id);
		return eventMapper.toDto(findModelById(id));
	}

	private EventModel findModelById(Long id) {
		logger.debug("Entered findModelById() with params : id [{}]", id);
		Optional<EventModel> event = eventRepo.findById(id);
		if (!event.isPresent()) {
			throw new EventException(EventExceptionType.EVENT_NOT_FOUND);
		}
		return event.get();
	}

	@Override
	public List<EventDto> findAll() {
		logger.info("Entered findAll()");
		return eventMapper.toDtoList(eventRepo.findAll());
	}

	@Override
	public EventDto createEvent(CreateEventDto dto) {
		logger.info("Entered createEvent() with params : dto [{}]", dto);
		return eventMapper.toDto(eventRepo.save(eventMapper.toModel(dto)));
	}

	@Override
	public void startEvent(Long id) {
		logger.info("Entered startEvent() with params : id [{}]", id);
		EventModel event = findModelById(id);
		event.setStartedAt(Instant.now());
		eventRepo.save(event);
	}

	@Override
	public void finishEvent(Long id) {
		logger.info("Entered finishEvent() with params : id [{}]", id);
		EventModel event = findModelById(id);
		if (event.getStartedAt() == null) {
			logger.error("Event is not allowed to finish. It needs to start first");
			throw new EventException(EventExceptionType.FINISH_OPERATION_NOT_ALLOWED);
		}
		event.setFinishedAt(Instant.now());
		eventRepo.save(event);
	}

	@Override
	public EventDto updateEvent(Long id, EventDto dto) {
		logger.info("Entered updateEvent() with params : id [{}], dto [{}]", id, dto);
		EventModel event = findModelById(id);
		EventModel updatedEvent = eventMapper.toModel(dto, event);
		return eventMapper.toDto(eventRepo.save(updatedEvent));
	}

	@Override
	public void deleteEvent(Long id) {
		logger.info("Entered deleteEvent() with params : id [{}]", id);
		EventModel event = findModelById(id);
		eventRepo.delete(event);
	}

}
