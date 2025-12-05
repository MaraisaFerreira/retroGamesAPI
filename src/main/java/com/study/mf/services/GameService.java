package com.study.mf.services;

import com.study.mf.controllers.GameController;
import com.study.mf.dto.GameDTO;
import com.study.mf.exceptions.CustomBadRequestException;
import com.study.mf.exceptions.CustomResourceNotFoundException;
import com.study.mf.model.Game;

import static com.study.mf.mappers.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.study.mf.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    private GameRepository repository;

    @Autowired
    PagedResourcesAssembler<GameDTO> assembler;

    public PagedModel<EntityModel<GameDTO>> findAll(Pageable pageable) {
        Page<Game> gamePage = repository.findAll(pageable);

        Page<GameDTO> dtoPage = gamePage.map(game -> {
            GameDTO dto = parseObject(game, GameDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Sort.Order order = pageable.getSort().iterator().next();
        String direction = order.getDirection().toString();
        String itemOrder = order.getProperty();

        Link pageLinks = linkTo(methodOn(GameController.class).findAll(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            direction,
            itemOrder
        )).withSelfRel().withType("GET");

        return assembler.toModel(dtoPage, pageLinks);
    }

    public PagedModel<EntityModel<GameDTO>> findByPartName(String name, Pageable pageable) {
        Page<Game> gamePage = repository.findByPartName(name, pageable);
        Page<GameDTO> gameDTOPage = gamePage.map(game -> {
            GameDTO dto = parseObject(game, GameDTO.class);
            addHateoasLinks(dto);
            return dto;
        });
        Sort.Order order = pageable.getSort().iterator().next();
        String direction = order.getDirection().toString();
        String orderBy = order.getProperty();

        Link pageLink = linkTo(methodOn(GameController.class).findByPartName(
            name,
            pageable.getPageNumber(),
            pageable.getPageSize(),
            direction,
            orderBy
        )).withSelfRel().withType("GET");

        return assembler.toModel(gameDTOPage, pageLink);
    }

    public GameDTO findById(Long id) {
        if (id == null) throw new CustomBadRequestException("Id cannot be null");

        Game game = repository.findById(id)
            .orElseThrow(() -> new CustomResourceNotFoundException("Game Not Found"));

        GameDTO dto = parseObject(game, GameDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public GameDTO create(GameDTO game) {
        if (game.getName() == null || game.getConsole() == null || game.getYear() == null) {
            throw new CustomBadRequestException("Name / Console and Year cannot be null.");
        }

        Game toSave = parseObject(game, Game.class);
        Game saved = repository.save(toSave);

        GameDTO dto = parseObject(saved, GameDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public GameDTO update(GameDTO game) {
        if (game.getId() == null) throw new CustomBadRequestException("Id cannot be null");

        Game entity = repository.findById(game.getId())
            .orElseThrow(() -> new CustomResourceNotFoundException("Game Not Found"));

        if (game.getName() != null) entity.setName(game.getName());
        if (game.getConsole() != null) entity.setConsole(game.getConsole());
        if (game.getYear() != null) entity.setYear(game.getYear());

        GameDTO dto = parseObject(entity, GameDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        if (id == null) throw new CustomBadRequestException("Id cannot be null");

        Game game = repository.findById(id)
            .orElseThrow(() -> new CustomResourceNotFoundException("Game Not Found"));
        repository.delete(game);
    }

    private void addHateoasLinks(GameDTO dto) {
        dto.add(linkTo(methodOn(GameController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(GameController.class).findAll(0, 50, "asc", "name")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(GameController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(GameController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(GameController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
