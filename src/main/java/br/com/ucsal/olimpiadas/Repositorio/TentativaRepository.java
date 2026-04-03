package br.com.ucsal.olimpiadas.Repositorio;

import br.com.ucsal.olimpiadas.model.Tentativa;

import java.util.ArrayList;
import java.util.List;

public class TentativaRepository {

    private final List<Tentativa> tentativas = new ArrayList<>();

    public void salvar(Tentativa t) {
        tentativas.add(t);

    }

    public List<Tentativa> listar() {

        return tentativas;
    }
}
