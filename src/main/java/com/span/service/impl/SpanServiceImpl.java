package com.span.service.impl;

import com.span.model.Span;
import com.span.model.SpanRequest;
import com.span.repo.SpanRepository;
import com.span.service.SpanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpanServiceImpl implements SpanService {
    private final SpanRepository spanRepository;

    public List<UUID> generate(UUID id) {
        Span mainSpan = findById(id);
        return new ArrayList<>(handleIds(mainSpan));
    }

    private List<UUID> handleIds(Span mainSpan) {
        List<UUID> ids = new ArrayList<>();
        ids.add(mainSpan.getId());
        mainSpan.getEl()
                .parallelStream().forEach(childSpan -> ids.addAll(handleIds(childSpan)));
        return ids;
    }

    public Span create(int value, Set<Span> children) {
        Span span = Span.builder().value(value).el(children).build();
        return spanRepository.save(span);
    }

    public Span update(Span span, Set<Span> newChildren) {
        Span thisSpan = spanRepository.findById(span.getId());
        thisSpan.getEl().addAll(newChildren);
        spanRepository.save(thisSpan);
        return thisSpan; // update in storage
    }

    public void delete(Span span) {
        // ceheck
        spanRepository.delete(span);
    }

    public Span findById(UUID id) {
        return spanRepository.findById(id);
    }

    @Override
    public List<Span> getRepo() {
        return new ArrayList<>(spanRepository.getRepo().values());
    }

    @Override
    public Span createSpan(SpanRequest spanRequest) {
        Set<Span> children = spanRequest.getElements()
                .stream()
                .map(this::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return create(spanRequest.getValue(), children);
    }
}
