package com.span.model;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Value(staticConstructor = "of")
@EqualsAndHashCode(exclude = {"value","el"})
@ToString(exclude = {"el"})
@RequiredArgsConstructor
@Builder
public class Span {
    @Builder.Default
    private UUID id = UUID.randomUUID();// un
    private int value;
    private Set<Span> el;
}
