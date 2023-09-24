package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Loja;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de Loja. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author falvojr
 */
public interface LojaService {

	Iterable<Loja> buscarTodos();

	Loja buscarPorId(Long id);
	
	Loja buscarPorCNPJ(String cnpj);

	void inserir(Loja loja);

	void atualizar(Long id, Loja loja);

	void deletar(Long id);

}
