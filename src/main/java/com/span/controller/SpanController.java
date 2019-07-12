package com.span.controller;

import com.span.model.Span;
import com.span.model.SpanRequest;
import com.span.service.SpanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SpanController {

    private final SpanService spanService;

    @PostMapping(value = "/span", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Span> createSpan(@RequestBody @Validated SpanRequest spanRequest) {
        return Optional.ofNullable(spanService.createSpan(spanRequest))
                .map(span -> new ResponseEntity<>(span, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(value = "/repo")
    public ResponseEntity<List<Span>> getRepo() { // List<Span> collect to List
        return Optional.of(spanService.getRepo())
                .map(list -> new ResponseEntity<>(list, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = {"/span/{id}"})
    @ResponseBody
    public ResponseEntity<Span> getSpecifiedSpan(@PathVariable("id") UUID id) {
        return Optional.ofNullable(spanService.findById(id))
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
