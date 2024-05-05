package com.fatech.controller;

import com.fatech.entity.Departamento;
import com.fatech.service.DepartamentoService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/departamentos")
@CrossOrigin
@Tag(name = "Departamento")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;


    @Operation(summary = "Criar um departamento", description = "Cria um novo departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar o departamento")
    })
    @PostMapping
    public ResponseEntity<Departamento> criarDepartamento(@RequestBody Departamento departamento) {
        Departamento novoDepartamento = departamentoService.criarDepartamento(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDepartamento);
    }

    @Operation(summary = "Atualizar um departamento existente", description = "Atualiza um departamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o departamento")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Departamento> atualizarDepartamento(@PathVariable long id, @RequestBody Departamento departamento) {
        departamento.setId_departamento(id);
        Departamento departamentoAtualizado = departamentoService.atualizarDepartamento(departamento);
        return ResponseEntity.ok().body(departamentoAtualizado);
    }
    @Operation(summary = "Buscar todos os departamentos", description = "Retorna todos os departamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os departamentos"),
            @ApiResponse(responseCode = "400", description = "Não há departamentos cadastrados")
    })
    @GetMapping
    public List<Departamento> buscarTodosDepartamentos() {
        return departamentoService.buscarTodosDepartamentos();
    }

    @Operation(summary = "Buscar departamento por ID", description = "Retorna um departamento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o departamento"),
            @ApiResponse(responseCode = "400", description = "Departamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Departamento> buscarDepartamentoPorId(@PathVariable long id) {
        Departamento departamento = departamentoService.buscarDepartamentoPorId(id);
        if (departamento != null) {
            return ResponseEntity.ok().body(departamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Excluir Definitivamente um departamento por ID", description = "Exclui Definitivamente um departamento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Departamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @DeleteMapping("/destruir/{id}")
    public ResponseEntity<Void> excluirDepartamento(@PathVariable long id) {
        departamentoService.excluirDepartamento(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Excluir um departamento por ID", description = "Exclui um departamento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Departamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDepartamento(@PathVariable long id) {
        departamentoService.deletarDepartamento(id);
        return ResponseEntity.noContent().build();
    }
    
}
