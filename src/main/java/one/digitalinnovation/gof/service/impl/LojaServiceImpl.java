package one.digitalinnovation.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.model.Loja;
import one.digitalinnovation.gof.model.LojaRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.LojaService;
import one.digitalinnovation.gof.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link LojaService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class LojaServiceImpl implements LojaService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private LojaRepository lojaRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<Loja> buscarTodos() {
		// Buscar todos os lojas.
		return lojaRepository.findAll();
	}

	@Override
	public Loja buscarPorId(Long id) {
		// Buscar Loja por ID.
		Optional<Loja> loja = lojaRepository.findById(id);
		return loja.get();
	}

	@Override
	public Loja buscarPorCNPJ(String cnpj) {
		// Buscar Loja por CNPJ.
		Optional<Loja> loja = lojaRepository.findByCnpj(cnpj);
		return loja.get();
	}

	@Override
	public void inserir(Loja loja) {
		salvarLojaComCep(loja);
	}

	@Override
	public void atualizar(Long id, Loja loja) {
		// Buscar loja por ID, caso exista:
		Optional<Loja> lojaBuscada = lojaRepository.findById(id);
		if (lojaBuscada.isPresent()) {
			salvarLojaComCep(loja);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar loja por ID.
		lojaRepository.deleteById(id);
	}

	private void salvarLojaComCep(Loja loja) {
		// Verificar se o Endereco do loja já existe (pelo CEP).
		String cep = loja.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		loja.setEndereco(endereco);
		// Inserir loja, vinculando o Endereco (novo ou existente).
		lojaRepository.save(loja);
	}

}
