package br.com.ucsal.olimpiadas.Repositorio;

import br.com.ucsal.olimpiadas.model.Questao;

import java.util.ArrayList;
import java.util.List;


public class QuestaoRepository {

    private final List<Questao> questoes = new ArrayList<>();

    public void salvar(Questao q) {
        questoes.add(q);
    }

    public List<Questao> listar() {
        return questoes;
    }

    public List<Questao> IdentificadoPorId(long provaId) {
        return questoes.stream()
                .filter(q -> q.getProvaId() == provaId).toList();
    }
}
