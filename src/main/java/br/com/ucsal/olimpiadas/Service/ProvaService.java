package br.com.ucsal.olimpiadas.Service;

import br.com.ucsal.olimpiadas.Repositorio.ProvaRepository;
import br.com.ucsal.olimpiadas.model.Prova;

import java.util.List;

public class ProvaService {

    private final ProvaRepository repo;

    public ProvaService(ProvaRepository repo) {
        this.repo = repo;

    }

    public Prova cadastrarProva(String titulo, long id) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("título inválido");
        }

        var prova = new Prova();
        prova.setId(id);
        prova.setTitulo(titulo);

        repo.salvar(prova);

        return prova;
    }

    public List<Prova> listar() {

        return repo.listar();
    }

    public boolean IdentificadoPorId(long id) {
        return repo.IdentificaPorId(id);
    }
}
