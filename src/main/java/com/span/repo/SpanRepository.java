package com.span.repo;

import com.span.model.Span;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public interface SpanRepository {

    Span findById(UUID id);
    Span save(Span span);
    void delete(Span span);
    Map<UUID, Span> getRepo();
}
