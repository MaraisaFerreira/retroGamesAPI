package com.study.mf.controllers;

import com.study.mf.dto.GameDTO;
import com.study.mf.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

  private final GameService gameService;
  private final PagedResourcesAssembler<GameDTO> assembler;

  public GameController(GameService gameService, PagedResourcesAssembler<GameDTO> assembler) {
    this.gameService = gameService;
    this.assembler = assembler;
  }

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

    Page<GameDTO> dtoPage = gameService.findAll(pageable).map(gameDTO -> {
      addHateoasLinks(gameDTO);
      return gameDTO;
    });

    Link link = linkTo(methodOn(GameController.class).findAll(page, size, direction, orderBy))
        .withSelfRel().withType("GET");

    return ResponseEntity.ok(assembler.toModel(dtoPage, link));
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

    Page<GameDTO> dtoPage = gameService.findByPartName(name, pageable).map(dto -> {
      addHateoasLinks(dto);
      return dto;
    });

    Link link = linkTo(methodOn(GameController.class).findByPartName(name, page, size, direction, orderBy))
        .withSelfRel().withType("GET");

    return ResponseEntity.ok(assembler.toModel(dtoPage, link));
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
    GameDTO dto = gameService.findById(id);
    addHateoasLinks(dto);
    return ResponseEntity.ok(dto);
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
    GameDTO dto = gameService.create(game);
    addHateoasLinks(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
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
    GameDTO dto = gameService.update(game);
    addHateoasLinks(dto);
    return ResponseEntity.ok(dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    gameService.delete(id);
    return ResponseEntity.noContent().build();
  }

  private void addHateoasLinks(GameDTO dto) {
    dto.add(linkTo(methodOn(GameController.class).findById(dto.getId())).withSelfRel().withType("GET"));
    dto.add(linkTo(methodOn(GameController.class).findAll(0, 50, "asc", "name")).withRel("findAll").withType("GET"));
    dto.add(linkTo(methodOn(GameController.class).create(dto)).withRel("create").withType("POST"));
    dto.add(linkTo(methodOn(GameController.class).update(dto)).withRel("update").withType("PUT"));
    dto.add(linkTo(methodOn(GameController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
  }
}
