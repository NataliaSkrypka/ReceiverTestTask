package com.span.service.impl;

import com.span.model.Span;
import com.span.model.SpanRequest;
import com.span.repo.SpanRepository;
import com.span.repo.impl.SpanRepositoryImpl;
import com.span.service.SpanService;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SpanServiceImplTest {
    private Span mainSpan;
    private SpanService spanService;
    private SpanRepository spanRepository = new SpanRepositoryImpl();

    @Before
    public void setUp() {
        Span span1 = Span.builder().id(UUID.fromString("1-1-1-1-1")).value(1).el(new HashSet<>()).build();
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        Span span3 = Span.builder().id(UUID.fromString("3-3-3-3-3")).value(3).el(new HashSet<>()).build();
        HashSet<Span> span5Children = new HashSet<>();
        span5Children.add(span1);
        span5Children.add(span2);
        span5Children.add(span3);
        Span span5 = Span.builder().id(UUID.fromString("5-5-5-5-5")).value(5).el(span5Children).build();
        HashSet<Span> span6Children = new HashSet<>();
        span6Children.add(span1);
        span6Children.add(span5);
        Span span6 = Span.builder().id(UUID.fromString("6-6-6-6-6")).value(6).el(span6Children).build();
        HashSet<Span> span7Children = new HashSet<>();
        span7Children.add(span5);
        span7Children.add(span6);
        mainSpan = Span.builder().id(UUID.fromString("7-7-7-7-7")).value(7).el(span7Children).build();

        Map<UUID, Span> repo = spanRepository.getRepo();
        repo.put(UUID.fromString("7-7-7-7-7"), mainSpan);
        repo.put(UUID.fromString("1-1-1-1-1"), span1);
        repo.put(UUID.fromString("2-2-2-2-2"), span2);
        repo.put(UUID.fromString("3-3-3-3-3"), span3);
        repo.put(UUID.fromString("5-5-5-5-5"), span5);
        repo.put(UUID.fromString("6-6-6-6-6"), span6);

        spanService = new SpanServiceImpl(spanRepository);
    }

    @Test
    public void generate() {
        List<UUID> idsActual = spanService.generate(UUID.fromString("7-7-7-7-7"));
        List<UUID> idsExpected = Arrays.asList(UUID.fromString("7-7-7-7-7"), UUID.fromString("5-5-5-5-5"), UUID.fromString("1-1-1-1-1"),
                UUID.fromString("2-2-2-2-2"), UUID.fromString("3-3-3-3-3"), UUID.fromString("6-6-6-6-6"), UUID.fromString("1-1-1-1-1"),
                UUID.fromString("5-5-5-5-5"), UUID.fromString("1-1-1-1-1"), UUID.fromString("2-2-2-2-2"), UUID.fromString("3-3-3-3-3"));
        assertEquals(idsExpected, idsActual);
    }

    @Test
    public void create() {
        Span span1 = Span.builder().id(UUID.fromString("1-1-1-1-1")).value(1).el(new HashSet<>()).build();
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        Span span3 = Span.builder().id(UUID.fromString("3-3-3-3-3")).value(3).el(new HashSet<>()).build();
        HashSet<Span> span5Children = new HashSet<>();
        span5Children.add(span1);
        span5Children.add(span2);
        span5Children.add(span3);
        Span spanActual = spanService.create(5, span5Children);
        Span spanExpected = Span.builder().value(5).el(span5Children).build();
        assertEquals(spanExpected.getValue(), spanActual.getValue());
        assertEquals(spanExpected.getEl(), spanActual.getEl());
    }

    @Test
    public void update() {
        Span span1 = Span.builder().id(UUID.fromString("1-1-1-1-1")).value(1).el(new HashSet<>()).build();
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        Span span3 = Span.builder().id(UUID.fromString("3-3-3-3-3")).value(3).el(new HashSet<>()).build();
        HashSet<Span> span5Children = new HashSet<>();
        span5Children.add(span1);
        span5Children.add(span2);
        span5Children.add(span3);

        Set<Span> expectedChildren = mainSpan.getEl();
        expectedChildren.addAll(span5Children);
        Span spanActual = spanService.update(mainSpan, span5Children);
        assertEquals(7, spanActual.getValue());
        assertEquals(expectedChildren, spanActual.getEl());
    }

    @Test
    public void findById() {
        Set<Span> childrenExpected = mainSpan.getEl();
        Span spanExpected = Span.builder().id(UUID.fromString("7-7-7-7-7")).value(7).el(childrenExpected).build();
        Span spanActual = spanService.findById(UUID.fromString("7-7-7-7-7"));
        assertEquals(spanExpected, spanActual);
    }

    @Test
    public void createSpan() {
        Set<Span> childExpected = new HashSet<>();
        childExpected.add(mainSpan);
        SpanRequest spanRequest = new SpanRequest();
        spanRequest.setValue(8);
        spanRequest.setElements(Collections.singletonList(UUID.fromString("7-7-7-7-7")));
        Span spanActual = spanService.createSpan(spanRequest);
        assertEquals(8, spanActual.getValue());
        assertEquals(childExpected, spanActual.getEl());
    }

}