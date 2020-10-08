package al.thinktech.tutorial.testsuite.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import al.thinktech.tutorial.testsuite.IntegrationTestConfig;
import al.thinktech.tutorial.testsuite.exception.EventException.EventExceptionType;
import al.thinktech.tutorial.testsuite.exception.dto.ErrorDto;
import al.thinktech.tutorial.testsuite.service.dto.CreateEventDto;
import al.thinktech.tutorial.testsuite.service.dto.EventDto;

@IntegrationTestConfig
public class EventControllerIT {
	protected final static int MINIMUM_SIZE = 1;

	protected MockMvc mockMvc;

	protected ObjectMapper objectMapper;

	@Autowired
	public EventControllerIT(MockMvc mockMvc, ObjectMapper objectMapper) {
		super();
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}

	@Test
	public void findAllEvents() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/v1/events").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		List<EventDto> events = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
				new TypeReference<List<EventDto>>() {
				});
		assertTrue(events.size() >= MINIMUM_SIZE);
	}

	@Test
	public void findEventById() throws Exception {
		EventDto expected = new EventDto(1L, "Thinktech IoT Hackathon #1",
				"ThinkTech IoT Hackathon organized by Landmark Technologies and Moveo Albania.", null, null);
		MvcResult result = mockMvc.perform(get("/api/v1/events/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		EventDto event = objectMapper.readValue(result.getResponse().getContentAsByteArray(), EventDto.class);
		assertNotNull(event);
		assertEquals(expected, event);
	}

	@Test
	public void findEventByIdNotFound() throws Exception {
		ErrorDto expected = new ErrorDto(EventExceptionType.EVENT_NOT_FOUND.getCode(),
				EventExceptionType.EVENT_NOT_FOUND.getMessage());
		MvcResult result = mockMvc.perform(get("/api/v1/events/11111").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
		ErrorDto error = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorDto.class);
		assertNotNull(error);
		assertEquals(expected, error);
	}

	@Test
	public void createEvent() throws Exception {
		EventDto expected = new EventDto(6L, "Thinktech IoT Hackathon #6",
				"ThinkTech IoT Hackathon organized by Landmark Technologies and Moveo Albania.This is the sixth Edition.",
				null, null);
		CreateEventDto createDto = new CreateEventDto("Thinktech IoT Hackathon #6",
				"ThinkTech IoT Hackathon organized by Landmark Technologies and Moveo Albania.This is the sixth Edition.");
		MvcResult result = mockMvc.perform(post("/api/v1/events").content(objectMapper.writeValueAsBytes(createDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
		EventDto event = objectMapper.readValue(result.getResponse().getContentAsByteArray(), EventDto.class);
		assertNotNull(event);
		assertEquals(expected, event);

		MvcResult resultSaved = mockMvc.perform(get("/api/v1/events/6").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		EventDto eventSaved = objectMapper.readValue(resultSaved.getResponse().getContentAsByteArray(), EventDto.class);
		assertNotNull(event);
		assertEquals(expected, eventSaved);
	}

	@Test
	public void updateEvent() throws Exception {
		Instant timestamp = Instant.now();
		EventDto expectedBeforeUpdate = new EventDto(3L, "Thinktech IoT Hackathon #3",
				"ThinkTech IoT Hackathon organized by Landmark Technologies and Moveo Albania.This is the third Edition.",
				null, null);
		MvcResult resultBeforeUpdate = mockMvc.perform(get("/api/v1/events/3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		EventDto eventBeforeUpdate = objectMapper.readValue(resultBeforeUpdate.getResponse().getContentAsByteArray(),
				EventDto.class);
		assertNotNull(eventBeforeUpdate);
		assertEquals(expectedBeforeUpdate, eventBeforeUpdate);

		EventDto event = new EventDto(3L, "Updated", "Updated", timestamp, timestamp);
		MvcResult result = mockMvc.perform(put("/api/v1/events/3").content(objectMapper.writeValueAsBytes(event))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		EventDto eventAfterUpdate = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
				EventDto.class);
		assertNotNull(eventAfterUpdate);
		assertEquals(event, eventAfterUpdate);
	}

	@Test
	public void updateEventNotFound() throws Exception {
		Instant timestamp = Instant.now();
		EventDto event = new EventDto(3L, "Updated", "Updated", timestamp, timestamp);
		mockMvc.perform(put("/api/v1/events/3000000000000").content(objectMapper.writeValueAsBytes(event))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void startEvent() throws Exception {
		mockMvc.perform(patch("/api/v1/events/4/start").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();
		MvcResult result = mockMvc.perform(get("/api/v1/events/4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		EventDto event = objectMapper.readValue(result.getResponse().getContentAsByteArray(), EventDto.class);
		assertNotNull(event.getStartedAt());
	}

	@Test
	public void startEventNotFound() throws Exception {
		mockMvc.perform(patch("/api/v1/events/10000000/start").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void finishEvent() throws Exception {
		mockMvc.perform(patch("/api/v1/events/5/finish").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();
		MvcResult result = mockMvc.perform(get("/api/v1/events/5").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		EventDto event = objectMapper.readValue(result.getResponse().getContentAsByteArray(), EventDto.class);
		assertNotNull(event.getStartedAt());
	}

	@Test
	public void finishEventNotFound() throws Exception {
		mockMvc.perform(patch("/api/v1/events/10000000/finish").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void deleteEvent() throws Exception {
		mockMvc.perform(delete("/api/v1/events/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();
	}

	@Test
	public void deleteEventNotFound() throws Exception {
		mockMvc.perform(delete("/api/v1/events/10000000").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound()).andReturn();
	}

}
