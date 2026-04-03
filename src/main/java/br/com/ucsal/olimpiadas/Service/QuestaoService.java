package br.com.ucsal.olimpiadas.Service;

import br.com.ucsal.olimpiadas.Repositorio.QuestaoRepository;
import br.com.ucsal.olimpiadas.model.Questao;

import java.util.List;

public class QuestaoService {

    private final QuestaoRepository repo;

    public QuestaoService(QuestaoRepository repo) {
        this.repo = repo;
    }

    public void cadastrarQuestao(Questao q) {
        repo.salvar(q);

    }

    public List<Questao> IdentificadoPorId(long provaId) {
        return repo.IdentificadoPorId(provaId);
    }

}
