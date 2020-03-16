package br.com.basis.prova.recurso;

import br.com.basis.prova.ProvaApplication;
import br.com.basis.prova.builder.DisciplinaBuilder;
import br.com.basis.prova.builder.ProfessorBuilder;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.recurso.util.TestUtil;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collection;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProvaApplication.class)
@Transactional
public class ProfessorRecursoTest {

    @Autowired
    private ProfessorBuilder professorBuilder;

    @Autowired
    private DisciplinaBuilder disciplinaBuilder;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProfessorMapper professorMapper;

    public ProfessorRecursoTest(){

    }

    private void deletaDados() {
        Collection<Professor> lista = professorBuilder.obterTodos();
        lista.forEach(professor -> professorBuilder.excluirPorId(professor.getId()));
    }

    @Before
    public void inicializaTeste() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.deletaDados();
        this.professorBuilder.setCustomizacao(null);
    }

    @Test
    public void obterTodos() throws Exception {
        Professor professor = professorBuilder.construir();
        mockMvc.perform(get("/api/professores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id", hasSize(1)));
    }

    @Test
    public void detalhar() throws Exception {
        Professor professor = professorBuilder.construir();
        mockMvc.perform(get("/api/professores/detalhes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void cadastrar() throws  Exception {
        Professor professor = professorBuilder.construirEntidade();
        efetuarCadastro(professor, status().isCreated());
    }

    @Test
    public void cadastrarNomeExcedendoCaracteres() throws Exception {
        Professor professor = professorBuilder.construirEntidade();
        professor.setNome("Nome que ultrapassa a quantia de cinquenta caracteres");
        efetuarCadastro(professor, status().isBadRequest());
    }

    @Test
    public void cadastrarMatriculaIncorreta1() throws Exception {
        Professor professor = professorBuilder.construirEntidade();
        professor.setMatricula("111");
        efetuarCadastro(professor, status().isBadRequest());
    }

    @Test
    public void cadastrarMatriculaIncorreta2() throws Exception {
        Professor professor = professorBuilder.construirEntidade();
        professor.setMatricula("1111111");
        efetuarCadastro(professor, status().isBadRequest());
    }

    @Test
    public void cadastrarAreaAtuacaoExcedendoCaracteres() throws Exception {
        Professor professor = professorBuilder.construirEntidade();
        professor.setArea("Aenean placerat. In vulputate urna eu arcu. Aliquam erat " +
                "volutpat. Suspendisse potenti. Morbi mattis felis at nunc. Duis viverra " +
                "diam non justo. In nisl. Nullam sit amet magna in magna gravida vehicula." +
                "Mauris tincidunt sem sed arcu. Nunc posuere. Nullam lectus justo,");
        efetuarCadastro(professor, status().isBadRequest());
    }


    @Test(expected = DateTimeException.class)
    public void cadastrarDataNascimentoInvalida1() throws Exception {
        Professor professor = professorBuilder.construirEntidade();
        professor.setDataNascimento(LocalDate.of(1988, 15, 32));
        mockMvc.perform(post("/api/professores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorMapper.toDto(professor))));
    }

    @Test(expected = DateTimeException.class)
    public void cadastrarDataNascimentoInvalida2() throws Exception {
        Professor professor = professorBuilder.construirEntidade();
        professor.setDataNascimento(LocalDate.of(1988, 1, 40));
        mockMvc.perform(post("/api/professores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorMapper.toDto(professor))));
    }


    @Test
    public void cadastrarProfessorVazio() throws Exception {
        Professor professor = new Professor();
        efetuarCadastro(professor, status().isBadRequest());
    }

    @Test
    public void atualizar() throws Exception {
        Professor professor = professorBuilder.construir();
        professor.setNome("Professor editado");
        mockMvc.perform(put("/api/professores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorMapper.toDto(professor))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Professor editado"))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarProfessorVazio() throws Exception {
        Professor professor = professorBuilder.construir();
        professor = new Professor();
        executarAtualizar(professor, status().isBadRequest());
    }

    @Test
    public void atualizarNomeExcedendoCaracteres() throws Exception {
        Professor professor = professorBuilder.construir();
        professor.setNome("Nome que ultrapassa a quantia de cinquenta caracteres");
        executarAtualizar(professor, status().isBadRequest());
    }

    @Test
    public void atualizarMatriculaIncorreta1() throws Exception {
        Professor professor = professorBuilder.construir();
        professor.setMatricula("111");
        executarAtualizar(professor, status().isBadRequest());
    }

    @Test
    public void atualizarMatriculaIncorreta2() throws Exception {
        Professor professor = professorBuilder.construir();
        professor.setMatricula("1234567");
        executarAtualizar(professor, status().isBadRequest());
    }

    @Test
    public void atualizarAreaAtuacaoExxcedendoCaracteres() throws Exception {
        Professor professor = professorBuilder.construir();
        professor.setArea("Aenean placerat. In vulputate urna eu arcu. Aliquam erat " +
                "volutpat. Suspendisse potenti. Morbi mattis felis at nunc. Duis viverra " +
                "diam non justo. In nisl. Nullam sit amet magna in magna gravida vehicula." +
                "Mauris tincidunt sem sed arcu. Nunc posuere. Nullam lectus justo,");
        executarAtualizar(professor, status().isBadRequest());
    }

    @Test(expected = DateTimeException.class)
    public void atualizarDataNascimentoInvalida1() throws Exception {
        Professor professor = professorBuilder.construir();
        professor.setDataNascimento(LocalDate.of(1900, 20, 01));
        mockMvc.perform(post("/api/professores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorMapper.toDto(professor))));
    }

    @Test(expected = DateTimeException.class)
    public void atualizarDataNascimentoInvalida2() throws Exception {
        Professor professor = professorBuilder.construirEntidade();
        professor.setDataNascimento(LocalDate.of(1900, 12, 79));
        mockMvc.perform(post("/api/professores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorMapper.toDto(professor))));
    }

    @Test
    public void excluir() throws Exception {
        Professor professor = professorBuilder.construir();
        mockMvc.perform(delete("/api/professores/" + professor.getId()))
                .andExpect(status().isOk());

        Assert.assertEquals(0, professorBuilder.obterTodos().size());
    }

    @Test
    public void excluirProfessorComDisciplinas() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        mockMvc.perform(delete("/api/professores/" + disciplina.getProfessor().getId()))
                .andExpect(status().isBadRequest());
    }



    private void efetuarCadastro(Professor professor, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(post("/api/professores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorMapper.toDto(professor))))
                .andExpect(resultMatcher);
    }

    private void executarAtualizar(Professor professor, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(put("/api/professores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorMapper.toDto(professor))))
                .andExpect(resultMatcher);
    }

}
