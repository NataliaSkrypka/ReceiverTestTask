package com.span.repo.impl;

import com.span.model.Span;
import com.span.repo.SpanRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class SpanRepositoryImpl implements SpanRepository {

    ConcurrentMap<UUID, Span> repo;

    public SpanRepositoryImpl() {
        this.repo = new ConcurrentHashMap<>();
    }

    public Map<UUID, Span> getRepo() {
        return repo;
    }

    public Span findById(UUID id) {
        return repo.get(id);
    }

    public Span save(Span span) {
        saveSpan(span);
        return span;
    }

    private void saveSpan(Span span) {
        repo.put(span.getId(), span);
        span.getEl().parallelStream().forEach(this::saveSpan);
    }

    public void delete(Span span) {
        UUID id = span.getId();
        repo.remove(id);
        repo.forEach((key, value) -> value.getEl().remove(span));
    }
}
