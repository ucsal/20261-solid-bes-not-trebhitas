package Service;

import Repositorio.QuestaoRepository;
import model.Participante;
import model.Questao;

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
