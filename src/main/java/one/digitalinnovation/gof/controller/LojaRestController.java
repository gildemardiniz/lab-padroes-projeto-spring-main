package one.digitalinnovation.gof.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.digitalinnovation.gof.model.Loja;
import one.digitalinnovation.gof.service.LojaService;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 * 
 * @author falvojr
 */
@RestController
@RequestMapping("lojas")
public class LojaRestController {

	@Autowired
	private LojaService lojaService;

	@GetMapping
	public ResponseEntity<Iterable<Loja>> buscarTodos() {
		return ResponseEntity.ok(lojaService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Loja> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(lojaService.buscarPorId(id));
	}

	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Loja> buscarPorCNPJ(@PathVariable String cnpj) {
		return ResponseEntity.ok(lojaService.buscarPorCNPJ(cnpj));
	}
	@PostMapping
	public ResponseEntity<Loja> inserir(@RequestBody Loja loja) {
		lojaService.inserir(loja);
		return ResponseEntity.ok(loja);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Loja> atualizar(@PathVariable Long id, @RequestBody Loja loja) {
		lojaService.atualizar(id, loja);
		return ResponseEntity.ok(loja);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		lojaService.deletar(id);
		return ResponseEntity.ok().build();
	}
}
