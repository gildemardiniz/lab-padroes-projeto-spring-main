package one.digitalinnovation.gof.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LojaRepository extends CrudRepository<Loja, Long> {

    @Query("select loja from Loja loja where trim(loja.cnpj) =trim(:cnpj) ")
    Optional<Loja> findByCnpj(String cnpj);

}