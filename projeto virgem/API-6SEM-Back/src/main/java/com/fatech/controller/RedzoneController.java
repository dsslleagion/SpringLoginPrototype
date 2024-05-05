package com.fatech.controller;

import com.fatech.entity.Redzone;
import com.fatech.service.RedzoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/redzones")
@CrossOrigin
@Tag(name = "Redzone")
public class RedzoneController {

    @Autowired
    private RedzoneService redzoneService;

    @Operation(summary = "Criar uma redzone", description = "Cria uma nova redzone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Redzone criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar a redzone")
    })
    @PostMapping
    public ResponseEntity<Redzone> criarRedzone(@RequestBody Redzone redzone) {
        Redzone novaRedzone = redzoneService.criarRedzone(redzone);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRedzone);
    }
    @Operation(summary = "Buscar todas as redzones", description = "Retorna todas as redzones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todas as redzones"),
            @ApiResponse(responseCode = "400", description = "Não há redzones cadastradas")
    })
    @GetMapping
    public ResponseEntity<List<Redzone>> buscarTodasRedzones() {
        List<Redzone> redzones = redzoneService.buscarTodasRedzones();
        if (redzones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(redzones);
    }

    @Operation(summary = "Buscar redzone por ID", description = "Retorna uma redzone por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a redzone"),
            @ApiResponse(responseCode = "400", description = "Redzone não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Redzone> buscarRedzonePorId(@PathVariable long id) {
        Redzone redzone;
        try {
            redzone = redzoneService.buscarRedzonePorId(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(redzone);
    }
    @Operation(summary = "Excluir Definitivamente uma redzone por ID", description = "Exclui Definitivamente uma redzone por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Redzone excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Redzone não encontrada")
    })
    @DeleteMapping("/destruir/{id}")
    public ResponseEntity<Void> excluirRedzone(@PathVariable long id) {
        redzoneService.excluirRedzone(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Excluir uma redzone por ID", description = "Exclui uma redzone por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Redzone excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Redzone não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRedzone(@PathVariable long id) {
        redzoneService.deletarRedzone(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Atualizar uma redzone existente", description = "Atualiza uma redzone existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Redzone atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar a redzone")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Redzone> atualizarRedzone(@PathVariable long id, @RequestBody Redzone redzone) {
        redzone.setId_redzone(id);
        Redzone redzoneAtualizada = redzoneService.atualizarRedzone(redzone);
        return ResponseEntity.ok().body(redzoneAtualizada);
    }
}
