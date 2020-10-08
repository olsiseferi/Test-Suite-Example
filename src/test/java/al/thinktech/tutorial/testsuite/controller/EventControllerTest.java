package al.thinktech.tutorial.testsuite.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import al.thinktech.tutorial.testsuite.service.IEventService;
import al.thinktech.tutorial.testsuite.service.dto.CreateEventDto;
import al.thinktech.tutorial.testsuite.service.dto.EventDto;

@WebMvcTest(controllers = EventController.class)
public class EventControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IEventService eventService;

	@Test
	public void findAll() throws Exception {

		when(eventService.findAll()).thenReturn(Arrays.asList(new EventDto(1L, "Name", "Description", null, null)));

		MvcResult result = mockMvc.perform(get("/api/v1/events").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		List<EventDto> events = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
				new TypeReference<List<EventDto>>() {
				});
		assertTrue(events.size() == 1);
		verify(eventService).findAll();
	}

	@Test
	public void findEventById() throws Exception {
		when(eventService.findById(anyLong())).thenReturn(new EventDto(1L, "Name", "Description", null, null));

		MvcResult result = mockMvc.perform(get("/api/v1/events/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		EventDto event = objectMapper.readValue(result.getResponse().getContentAsByteArray(), EventDto.class);
		assertNotNull(event);
		verify(eventService).findById(anyLong());

	}

	@Test
	public void createEvent() throws Exception {
		when(eventService.createEvent(any(CreateEventDto.class)))
				.thenReturn(new EventDto(1L, "Created", "Created", null, null));

		MvcResult result = mockMvc.perform(
				post("/api/v1/events").content(objectMapper.writeValueAsBytes(new CreateEventDto("Created", "Created")))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		EventDto event = objectMapper.readValue(result.getResponse().getContentAsByteArray(), EventDto.class);
		assertNotNull(event);

		verify(eventService).createEvent(any(CreateEventDto.class));

	}

	@Test
	public void updateEvent() throws Exception {
		when(eventService.updateEvent(anyLong(), any(EventDto.class)))
				.thenReturn(new EventDto(1L, "Updated", "Updated", null, null));

		MvcResult result = mockMvc.perform(put("/api/v1/events/1")
				.content(objectMapper.writeValueAsBytes(new EventDto(1L, "Updated", "Updated", null, null)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		EventDto eventBeforeUpdate = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
				EventDto.class);
		assertNotNull(eventBeforeUpdate);

		verify(eventService).updateEvent(anyLong(), any(EventDto.class));
	}

	@Test
	public void startEvent() throws Exception {
		doNothing().when(eventService).startEvent(anyLong());

		mockMvc.perform(patch("/api/v1/events/1/start").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();
	}

	@Test
	public void finishEvent() throws Exception {
		doNothing().when(eventService).finishEvent(anyLong());

		mockMvc.perform(patch("/api/v1/events/1/finish").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();
	}

	@Test
	public void deleteEvent() throws Exception {
		doNothing().when(eventService).deleteEvent(anyLong());

		mockMvc.perform(delete("/api/v1/events/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();
	}
}
