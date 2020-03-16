package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AlunoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AlunoServico {

    private AlunoMapper alunoMapper;
    private AlunoRepositorio alunoRepositorio;

    public AlunoServico(AlunoMapper alunoMapper, AlunoRepositorio alunoRepositorio) {
        this.alunoMapper = alunoMapper;
        this.alunoRepositorio = alunoRepositorio;
    }

    public AlunoDTO salvar(AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.toEntity(alunoDTO);

        if(verificarCPF(aluno)){
            throw new RegraNegocioException("CPF já existe");
        }

        return alunoMapper.toDto(aluno);
    }

    private boolean verificarCPF(Aluno aluno) {
        Aluno alunoCpf = alunoRepositorio.findByCpf(aluno.getCpf());
        return !(alunoCpf == null || alunoCpf.getId().equals(aluno.getId()));
    }

    public void excluir(String matricula) {
    }

    public List<AlunoDTO> consultar() {
        return new ArrayList<>();
    }

    public AlunoDetalhadoDTO detalhar(Integer id) {
        Aluno aluno = alunoRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Registro não encontrado"));
        return new AlunoDetalhadoDTO();
    }

}
