package ru.konin.botik.java_t_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.konin.botik.java_t_bot.model.Anecdots;
import ru.konin.botik.java_t_bot.service.AnecdotsService;
import ru.konin.botik.java_t_bot.service.AnecdotsServiceImpl;
import ru.konin.botik.java_t_bot.model.AnecdotSaveDTO;
import java.util.List;

@RequestMapping("/api/anecdots")
@RequiredArgsConstructor
@RestController
public class AnecdotsController {

    private final AnecdotsService anecdotsService;

    @Operation(
            summary = "Добавить шутку",
            description = "Позволяет добавить новую шутку в базу данных. Возвращает созданный объект шутки.",
            tags = {"anecdots", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Шутка успешно создана",
                    content = @Content(schema = @Schema(implementation = Anecdots.class), mediaType = "application/json")),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Anecdots> addAnecdots(@RequestBody AnecdotSaveDTO anecdot) {
        Anecdots saved = anecdotsService.addAnecdots(anecdot);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(
            summary = "Получить все шутки",
            description = "Возвращает список всех шуток из базы. При указании параметра title — выполняет фильтрацию по заголовку.",
            tags = {"anecdots", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список шуток успешно получен",
                    content = @Content(schema = @Schema(implementation = Anecdots.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<Anecdots>> getAllAnecdots(
            @RequestParam(value = "title", required = false) String title) {
        List<Anecdots> anecdots = anecdotsService.getAllAnecdots(title);
        return ResponseEntity.ok(anecdots);
    }

    @Operation(
            summary = "Получить шутку по ID",
            description = "Возвращает шутку по её уникальному идентификатору. Если шутка не найдена — возвращает 404.",
            tags = {"anecdots", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно получена",
                    content = @Content(schema = @Schema(implementation = Anecdots.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<Anecdots> getAnecdotsById(@PathVariable("id") Long id) {
        Anecdots anecdot = anecdotsService.getAnecdotsById(id);
        return ResponseEntity.ok(anecdot);
    }

    @Operation(
            summary = "Редактировать шутку",
            description = "Позволяет изменить содержимое существующей шутки по её ID. При некорректных данных — 400, при отсутствии — 404.",
            tags = {"anecdots", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно обновлена",
                    content = @Content(schema = @Schema(implementation = Anecdots.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<Anecdots> editAnecdots(
            @PathVariable("id") Long id,
            @RequestBody AnecdotSaveDTO anecdot) {
        Anecdots editedAnecdot = anecdotsService.editAnecdots(id, anecdot);
        return ResponseEntity.ok(editedAnecdot);
    }

    @Operation(
            summary = "Удалить шутку",
            description = "Удаляет шутку по указанному ID. Возвращает подтверждение удаления.",
            tags = {"anecdots", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно удалена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnecdots(@PathVariable("id") Long id) {
        anecdotsService.deleteAnecdots(id);
        return ResponseEntity.ok("Шутка успешно удалена");
    }
}