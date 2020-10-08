package al.thinktech.tutorial.testsuite.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import al.thinktech.tutorial.testsuite.service.IEventService;
import al.thinktech.tutorial.testsuite.service.dto.CreateEventDto;
import al.thinktech.tutorial.testsuite.service.dto.EventDto;

@RestController
@RequestMapping(path = "/api/v1/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventController {
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	private final IEventService eventService;

	@Autowired
	public EventController(IEventService eventService) {
		super();
		this.eventService = eventService;
	}

	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<EventDto>> findAllEvents() {
		logger.info("Entered [GET /api/v1/events] [findAllEvents]");
		return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<EventDto> findEventById(@NotNull @Valid @PathVariable(name = "id") Long id) {
		logger.info("Entered [GET /api/v1/events/{id}] [findEventById] with params : id [{}]", id);
		return new ResponseEntity<>(eventService.findById(id), HttpStatus.OK);
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<EventDto> createEvent(@NotNull @Valid @RequestBody CreateEventDto request) {
		logger.info("Entered [POST /api/v1/events/}] [createEvent] with params : request [{}]", request);
		return new ResponseEntity<>(eventService.createEvent(request), HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<EventDto> updateEvent(@NotNull @Valid @PathVariable(name = "id") Long id,
			@NotNull @Valid @RequestBody EventDto request) {
		logger.info("Entered [PUT /api/v1/events/{id}] [updateEvent] with params : id [{}], request [{}]", id);
		return new ResponseEntity<>(eventService.updateEvent(id, request), HttpStatus.OK);
	}

	@PatchMapping(path = "/{id}/start")
	public @ResponseBody ResponseEntity<Void> startEvent(@NotNull @Valid @PathVariable(name = "id") Long id) {
		logger.info("Entered [PATCH /api/v1/events/{id}/start] [startEvent] with params : id [{}]", id);
		eventService.startEvent(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping(path = "/{id}/finish")
	public @ResponseBody ResponseEntity<Void> finishEvent(@NotNull @Valid @PathVariable(name = "id") Long id) {
		logger.info("Entered [PATCH /api/v1/events/{id}/finish] [startEvent] with params : id [{}]", id);
		eventService.finishEvent(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Void> deleteEvent(@NotNull @Valid @PathVariable(name = "id") Long id) {
		logger.info("Entered [DELETE /api/v1/events/{id}] [deleteEvent] with params : id [{}]", id);
		eventService.deleteEvent(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
