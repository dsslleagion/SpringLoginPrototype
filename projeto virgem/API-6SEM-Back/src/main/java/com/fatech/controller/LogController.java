package com.fatech.controller;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fatech.dto.LogDTO;
import com.fatech.entity.Log;
import com.fatech.entity.Redzone;
import com.fatech.service.LogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(value = "/log")
@CrossOrigin
@Tag(name = "Log")
public class LogController {
    @Autowired
    private LogService service;

    @Operation(summary = "Realiza a busca de registros", method = "GET", description = "Busca todos os registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros"),
            @ApiResponse(responseCode = "400", description = "Não existe nenhum registro")
    })
    @GetMapping
    public List<LogDTO> buscarTodosLogs() {
        return service.buscarTodosLogs();
    }

    @Operation(summary = "Realiza a criação de registros", method = "POST", description = "Cria um registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cria o registro"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar o registro")
    })
    @PostMapping("/post")
    public ResponseEntity<Log> criarLog(@RequestBody Log novoLog) {
        try {
            novoLog.setData(LocalDateTime.now());
            Log logCriado = service.criarLog(novoLog);
            return new ResponseEntity<>(logCriado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Realiza a busca de registro por ID", method = "GET", description = "Busca registro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o registro"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LogDTO> buscarLogPorId(@PathVariable Long id) {
        try {
            LogDTO log = service.buscarLogPorId(id);
            return new ResponseEntity<>(log, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Realiza a exclusão do registro por ID", method = "DELETE", description = "Exclui o registro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLog(@PathVariable Long id) {
        try {
            service.deletarLog(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Realiza a exclusão de todos os registros", method = "DELETE", description = "Deleta todos os registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "400", description = "")
    })
    @DeleteMapping("/all")
    public ResponseEntity<Void> deletarTodosLogs() {
        service.deletarTodosLogs();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Realiza a busca de registro por ID da redzone", method = "GET", description = "Busca registro por id_redzone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o registro"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/redzone/{redzoneId}")
    public ResponseEntity<List<LogDTO>> findLogsByRedzoneId(@PathVariable Redzone redzoneId) {
        List<Log> logs = service.findLogsByRedzoneId(redzoneId);
        List<LogDTO> logDTOs = logs.stream()
                .map(log -> new LogDTO(log.getId(), log.getEntradaAsString(), log.getData(), log.getLotacao()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(logDTOs);
    }

    @Operation(summary = "Realiza a busca de registros filtrando pelo ID da redzone e um período de datas", method = "GET", description = "Busca registro por id_redzone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/redzone/{redzoneId}/dates")
    public ResponseEntity<List<Log>> findByRedzoneIdAndDateRange(
            @PathVariable Redzone redzoneId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Log> logs = service.findByRedzoneIdAndDateRange(redzoneId, startDate, endDate);
        return ResponseEntity.ok(logs);
    }

}
