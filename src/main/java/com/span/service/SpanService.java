package com.span.service;

import com.span.model.Span;
import com.span.model.SpanRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public interface SpanService {

    List<UUID> generate(UUID id);

    Span create(int value, Set<Span> children);

    Span update(Span span, Set<Span> newChildren);

    void delete(Span span);

    Span findById(UUID id);

    List<Span> getRepo(); //?

    Span createSpan (SpanRequest spanRequest);
}
