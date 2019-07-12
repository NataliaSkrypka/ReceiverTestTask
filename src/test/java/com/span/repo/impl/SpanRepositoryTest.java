package com.span.repo.impl;

import com.span.model.Span;
import com.span.repo.SpanRepository;
import com.span.repo.impl.SpanRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class SpanRepositoryTest {

    private Span mainSpan;
    private SpanRepositoryImpl spanRepository = new SpanRepositoryImpl();

    @Before
    public void setUp() {
        Span span1 = Span.builder().id(UUID.fromString("1-1-1-1-1")).value(1).el(new HashSet<>()).build();
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        Span span3 = Span.builder().id(UUID.fromString("3-3-3-3-3")).value(3).el(new HashSet<>()).build();
        Span span4 = Span.builder().id(UUID.fromString("4-4-4-4-4")).value(4).el(new HashSet<>()).build();
        HashSet<Span> span5Children = new HashSet<>();
        span5Children.add(span1);
        span5Children.add(span2);
        span5Children.add(span3);
        span5Children.add(span4);
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
        repo.put(UUID.fromString("4-4-4-4-4"), span4);
        repo.put(UUID.fromString("5-5-5-5-5"), span5);
        repo.put(UUID.fromString("6-6-6-6-6"), span6);
    }

    @Test
    public void findById() {
        Span spanActual = spanRepository.findById(UUID.fromString("2-2-2-2-2"));
        assertEquals(Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build(), spanActual);
    }

    @Test
    public void save() {
        SpanRepositoryImpl spanRepository = new SpanRepositoryImpl();
        Span resultActual = spanRepository.save(mainSpan);
        assertEquals(mainSpan, resultActual);

        Span span1 = Span.builder().id(UUID.fromString("1-1-1-1-1")).value(1).el(new HashSet<>()).build();
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        Span span3 = Span.builder().id(UUID.fromString("3-3-3-3-3")).value(3).el(new HashSet<>()).build();
        Span span4 = Span.builder().id(UUID.fromString("4-4-4-4-4")).value(4).el(new HashSet<>()).build();
        HashSet<Span> span5Children = new HashSet<>();
        span5Children.add(span1);
        span5Children.add(span2);
        span5Children.add(span3);
        span5Children.add(span4);
        Span span5 = Span.builder().id(UUID.fromString("5-5-5-5-5")).value(5).el(span5Children).build();
        HashSet<Span> span6Children = new HashSet<>();
        span6Children.add(span1);
        span6Children.add(span5);
        Span span6 = Span.builder().id(UUID.fromString("6-6-6-6-6")).value(6).el(span6Children).build();
        HashSet<Span> span7Children = new HashSet<>();
        span7Children.add(span5);
        span7Children.add(span6);
        Span span7 = Span.builder().id(UUID.fromString("7-7-7-7-7")).value(7).el(span7Children).build();

        ConcurrentHashMap<UUID, Span> repoExpected = new ConcurrentHashMap<>();
        repoExpected.put(UUID.fromString("7-7-7-7-7"), span7);
        repoExpected.put(UUID.fromString("1-1-1-1-1"), span1);
        repoExpected.put(UUID.fromString("2-2-2-2-2"), span2);
        repoExpected.put(UUID.fromString("3-3-3-3-3"), span3);
        repoExpected.put(UUID.fromString("4-4-4-4-4"), span4);
        repoExpected.put(UUID.fromString("5-5-5-5-5"), span5);
        repoExpected.put(UUID.fromString("6-6-6-6-6"), span6);
        assertEquals(repoExpected, spanRepository.getRepo());
    }

    @Test
    public void delete() {
        spanRepository.delete(Span.builder().id(UUID.fromString("1-1-1-1-1")).value(1).el(new HashSet<>()).build());
        Map<UUID, Span> repoActual = spanRepository.getRepo();
        Map<UUID, Span> repoExpected = new ConcurrentHashMap<>();
        Span span2 = Span.builder().id(UUID.fromString("2-2-2-2-2")).value(2).el(new HashSet<>()).build();
        Span span3 = Span.builder().id(UUID.fromString("3-3-3-3-3")).value(3).el(new HashSet<>()).build();
        Span span4 = Span.builder().id(UUID.fromString("4-4-4-4-4")).value(4).el(new HashSet<>()).build();
        HashSet<Span> span5Children = new HashSet<>();
        span5Children.add(span2);
        span5Children.add(span3);
        span5Children.add(span4);
        Span span5 = Span.builder().id(UUID.fromString("5-5-5-5-5")).value(5).el(span5Children).build();
        HashSet<Span> span6Children = new HashSet<>();
        span6Children.add(span5);
        Span span6 = Span.builder().id(UUID.fromString("6-6-6-6-6")).value(6).el(span6Children).build();
        HashSet<Span> span7Children = new HashSet<>();
        span7Children.add(span5);
        span7Children.add(span6);
        Span span7 = Span.builder().id(UUID.fromString("7-7-7-7-7")).value(7).el(span7Children).build();

        repoExpected.put(UUID.fromString("2-2-2-2-2"), span2);
        repoExpected.put(UUID.fromString("3-3-3-3-3"), span3);
        repoExpected.put(UUID.fromString("4-4-4-4-4"), span4);
        repoExpected.put(UUID.fromString("5-5-5-5-5"), span5);
        repoExpected.put(UUID.fromString("6-6-6-6-6"), span6);
        repoExpected.put(UUID.fromString("7-7-7-7-7"), span7);

        assertEquals(repoExpected, repoActual);
    }
}