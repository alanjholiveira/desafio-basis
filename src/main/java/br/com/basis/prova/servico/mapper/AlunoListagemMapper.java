package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoListagemDTO;
import org.mapstruct.Mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface AlunoListagemMapper extends EntityMapper<AlunoListagemDTO, Aluno> {


    @Override
    public default AlunoListagemDTO toDto(Aluno entity) {
        AlunoListagemDTO alunoListagemDTO = new AlunoListagemDTO();
        alunoListagemDTO.setId(entity.getId());
        alunoListagemDTO.setNome(entity.getNome());
        alunoListagemDTO.setMatricula(entity.getMatricula());

        //idade
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("dd");

        int ano1 = entity.getDataNascimento().getYear();
        int ano2 = Integer.parseInt(y.format(new Date()));
        int idade = ano2 - ano1;

        int mes1 = entity.getDataNascimento().getMonth().getValue();
        int mes2 = Integer.parseInt(m.format(new Date()));

        int dia1 = entity.getDataNascimento().getDayOfMonth();
        int dia2 = Integer.parseInt(d.format(new Date()));

        if (mes2 < mes1){
            idade --;
        } else {
            if(dia2 < dia1){
                idade --;
            }
        }

        alunoListagemDTO.setIdade(idade);

        return alunoListagemDTO;
    }
}
