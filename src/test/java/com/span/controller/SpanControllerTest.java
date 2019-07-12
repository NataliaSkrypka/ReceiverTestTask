package com.span.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.span.model.Span;
import com.span.model.SpanRequest;
import com.span.service.SpanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SpanController.class)
public class SpanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpanService spanService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createSpan() throws Exception {
        SpanRequest spanRequest = new SpanRequest();
        spanRequest.setValue(1);
        spanRequest.setElements(Arrays.asList(UUID.fromString("2-2-2-2-2"), UUID.fromString("3-3-3-3-3")));
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        Span span3 = Span.builder().id(UUID.fromString("3-3-3-3-3")).value(3).el(new HashSet<>()).build();
        Set<Span> spanChildren = new HashSet<>();
        spanChildren.add(span2);
        spanChildren.add(span3);
        Span span1 = Span.builder().id(UUID.fromString("1-1-1-1-1")).value(1).el(spanChildren).build();
        when(spanService.createSpan(spanRequest)).thenReturn(span1);

        mockMvc.perform(post("/span")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(spanRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(span1)));
    }

    @Test
    public void getRepo() throws Exception {
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        Span span3 = Span.builder().id(UUID.fromString("3-3-3-3-3")).value(3).el(new HashSet<>()).build();
        Set<Span> spanChildren = new HashSet<>();
        spanChildren.add(span2);
        spanChildren.add(span3);
        Span span1 = Span.builder().id(UUID.fromString("1-1-1-1-1")).value(1).el(spanChildren).build();
        Span span4 = Span.builder().id(UUID.fromString("4-4-4-4-4")).value(4).el(new HashSet<>()).build();
        List<Span> repo = Arrays.asList(span1, span2, span3, span4);
        when(spanService.getRepo()).thenReturn(repo);

        mockMvc.perform(get("/repo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(repo)));
    }

    @Test
    public void getSpecifiedSpan() throws Exception {
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        when(spanService.findById(UUID.fromString("2-2-2-2-2"))).thenReturn(span2);

        mockMvc.perform(get("/span/2-2-2-2-2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(span2)));
    }

    @Test
    public void getMissingSpan() throws Exception {
        mockMvc.perform(get("/span/1"))
                .andExpect(status().is4xxClientError());
    }
}