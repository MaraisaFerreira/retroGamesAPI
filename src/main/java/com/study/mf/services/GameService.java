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
    private final GameRepository repository;

  public GameService(GameRepository repository) {
    this.repository = repository;
  }

  public Page<GameDTO> findAll(Pageable pageable) {
        Page<Game> gamePage = repository.findAll(pageable);

        return gamePage.map(game ->
            parseObject(game, GameDTO.class)
        );
    }

    public Page<GameDTO> findByPartName(String name, Pageable pageable) {
        Page<Game> gamePage = repository.findByPartName(name, pageable);
        return gamePage.map(game -> parseObject(game, GameDTO.class));
    }

    public GameDTO findById(Long id) {
        if (id == null) throw new CustomBadRequestException("Id cannot be null");

        Game game = repository.findById(id)
            .orElseThrow(() -> new CustomResourceNotFoundException("Game Not Found"));

        return parseObject(game, GameDTO.class);
    }

    public GameDTO create(GameDTO game) {
        if (game.getName() == null || game.getConsole() == null || game.getYear() == null) {
            throw new CustomBadRequestException("Name / Console and Year cannot be null.");
        }

        Game toSave = parseObject(game, Game.class);
        Game saved = repository.save(toSave);

        return parseObject(saved, GameDTO.class);
    }

    @Transactional
    public GameDTO update(GameDTO game) {
        if (game.getId() == null) throw new CustomBadRequestException("Id cannot be null");

        Game entity = repository.findById(game.getId())
            .orElseThrow(() -> new CustomResourceNotFoundException("Game Not Found"));

        if (game.getName() != null) entity.setName(game.getName());
        if (game.getConsole() != null) entity.setConsole(game.getConsole());
        if (game.getYear() != null) entity.setYear(game.getYear());

        return parseObject(entity, GameDTO.class);
    }

    public void delete(Long id) {
        if (id == null) throw new CustomBadRequestException("Id cannot be null");

        Game game = repository.findById(id)
            .orElseThrow(() -> new CustomResourceNotFoundException("Game Not Found"));
        repository.delete(game);
    }
}
