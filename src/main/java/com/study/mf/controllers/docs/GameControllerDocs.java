package com.study.mf.controllers.docs;

import com.study.mf.data.dto.GameDTO;
import com.study.mf.exceptions.ResponseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Games")
public interface GameControllerDocs {

    @Operation(
        summary = "Get All Games from DB.",
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_YAML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
            }),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    ResponseEntity<PagedModel<EntityModel<GameDTO>>> findAll(Integer page, Integer size,
                                                             String direction, String orderBy);


    @Operation(
        summary = "Get All Games with searched name.",
        parameters = @Parameter(name = "name", required = true,
            schema = @Schema(implementation = String.class)),
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_YAML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    ResponseEntity<PagedModel<EntityModel<GameDTO>>> findByPartName(String name, Integer page,
                                                                    Integer size, String direction,
                                                                    String orderBy);


    @Operation(
        summary = "Get a specific game from DB using its id.",
        parameters = @Parameter(name = "id", required = true,
            schema = @Schema(implementation = Integer.class)),
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GameDTO.class)
                ),
                @Content(
                    mediaType = MediaType.APPLICATION_XML_VALUE,
                    schema = @Schema(implementation = GameDTO.class)
                ),
                @Content(
                    mediaType = MediaType.APPLICATION_YAML_VALUE,
                    schema = @Schema(implementation = GameDTO.class)
                )
            }),
            @ApiResponse(description = "Not Found", responseCode = "404",
                content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                content = @Content(schema = @Schema(implementation = ResponseException.class)))

        }

    )
    ResponseEntity<GameDTO> findById(Long id);

    @Operation(
        summary = "Add a new game on DB",
        requestBody = @RequestBody(
            required = true,
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_YAML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
            }
        ),
        responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_YAML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    ResponseEntity<GameDTO> create(GameDTO game);

    @Operation(
        summary = "Add a new game on DB",
        requestBody = @RequestBody(
            required = true,
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_YAML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
            }
        ),
        responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
                @Content(mediaType = MediaType.APPLICATION_YAML_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))),
            }),
            @ApiResponse(description = "Not Found", responseCode = "404",
                content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    ResponseEntity<GameDTO> update(GameDTO game);

    @Operation(
        summary = "Remove a specific game from db using its id",
        parameters = {
            @Parameter(name = "id", required = true, schema = @Schema(implementation = Integer.class))
        },
        responses = {
            @ApiResponse(description = "Not Found", responseCode = "404",
                content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                content = @Content(schema = @Schema(implementation = ResponseException.class)))
        }
    )
    ResponseEntity<Void> delete(Long id);
}
