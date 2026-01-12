package com.study.mf.controllers;

import com.study.mf.dto.GameDTO;
import com.study.mf.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
        }
    )
    public ResponseEntity<PagedModel<EntityModel<GameDTO>>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "50") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        @RequestParam(value = "order_by", defaultValue = "name") String orderBy
    ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ?
            Sort.Direction.DESC : Sort.Direction.ASC;

        Field[] fields = GameDTO.class.getDeclaredFields();
        List<String> sortOptions = Arrays.stream(fields).map(Field::getName).toList();
        String by_field = sortOptions.contains(orderBy.toLowerCase()) ?
            orderBy.toLowerCase() : sortOptions.get(1);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, by_field));

        return ResponseEntity.ok(gameService.findAll(pageable));
    }

    @GetMapping(
        value = "/search/{name}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
        }
    )
    public ResponseEntity<PagedModel<EntityModel<GameDTO>>> findByPartName(
        @PathVariable String name,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "30") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        @RequestParam(value = "order_by", defaultValue = "name") String orderBy
    ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ?
            Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, orderBy));

        return ResponseEntity.ok(gameService.findByPartName(name, pageable));
    }

    @GetMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
        }
    )
    public ResponseEntity<GameDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.findById(id));
    }

    @PostMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
        },
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
        }
    )
    public ResponseEntity<GameDTO> create(@RequestBody GameDTO game) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.create(game));
    }

    @PutMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
        },
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
        }
    )
    public ResponseEntity<GameDTO> update(@RequestBody GameDTO game) {
        return ResponseEntity.ok(gameService.update(game));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
