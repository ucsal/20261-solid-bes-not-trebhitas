package br.com.ucsal.olimpiadas.Repositorio;

import java.util.ArrayList;
import java.util.List;

import br.com.ucsal.olimpiadas.model.Prova;


public class ProvaRepository {
    private final List<Prova> provas = new ArrayList<>();

    public void salvar(Prova pp) {

        provas.add(pp);
    }

    public List<Prova> listar() {

        return provas;
    }

    public boolean IdentificaPorId(long id) {

        return provas.stream().anyMatch(pp -> pp.getId() == id);
    }
}
