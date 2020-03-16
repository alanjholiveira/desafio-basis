package br.com.basis.prova.recurso;

import br.com.basis.prova.ProvaApplication;
import br.com.basis.prova.builder.DisciplinaBuilder;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.recurso.util.TestUtil;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.DisciplinaMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class DisciplinaRecursoTest {

    @Autowired
    private DisciplinaBuilder disciplinaBuilder;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DisciplinaMapper disciplinaMapper;

    @Rule
    public ExpectedException excecaoEsperada = ExpectedException.none();

    public DisciplinaRecursoTest(){

    }

    private void deletaDados() {
        Collection<Disciplina> lista = disciplinaBuilder.obterTodos();
        lista.forEach(disciplina -> disciplinaBuilder.excluirPorId(disciplina.getId()));
    }

    @Before
    public void inicializaTeste() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.deletaDados();
        this.disciplinaBuilder.setCustomizacao(null);
    }

    @Test
    public void obterTodos() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        mockMvc.perform(get("/api/disciplinas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id", hasSize(1)));
    }

    @Test
    public void detalhar() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        mockMvc.perform(get("/api/disciplinas/detalhes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void cadastrar() throws  Exception {
        Disciplina disciplina = disciplinaBuilder.construirEntidade();
        executarCadastroDisciplina(disciplina, status().isCreated());
    }


    @Test
    public void cadastrarDisciplinaVazia() throws Exception {
        Disciplina disciplina = new Disciplina();
        executarCadastroDisciplina(disciplina, status().isBadRequest());
    }

    @Test
    public void cadastrarDisciplinaNomeExcessoCaracteres() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construirEntidade();
        disciplina.setNome("campo excedendo a quantidade exorbitante de cinquenta caracteres");
        executarCadastroDisciplina(disciplina, status().isBadRequest());
    }

    @Test
    public void cadastrarDisciplinaDescricaoExcessoCaracteres() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construirEntidade();
        disciplina.setDescricao("Aenean placerat. In vulputate urna eu arcu. Aliquam erat " +
                "volutpat. Suspendisse potenti. Morbi mattis felis at nunc. Duis viverra " +
                "diam non justo. In nisl. Nullam sit amet magna in magna gravida vehicula." +
                "Mauris tincidunt sem sed arcu. Nunc posuere. Nullam lectus justo,");
        executarCadastroDisciplina(disciplina, status().isBadRequest());
    }

    @Test
    public void cadastrarDisciplinaCargaHorariaZero() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construirEntidade();
        disciplina.setCargaHoraria(0);
        executarCadastroDisciplina(disciplina, status().isBadRequest());
    }

    @Test(expected = RegraNegocioException.class)
    public void cadastrarDisciplinaProfessorInvalido() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construirEntidade();
        Professor professor = disciplina.getProfessor();
        professor.setId(10000);
        disciplina.setProfessor(professor);
        disciplinaBuilder.persistir(disciplina);
        excecaoEsperada.expect(RegraNegocioException.class);
    }

    @Test(expected = RegraNegocioException.class)
    public void excluirDisciplinaProfessorInvalido() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construirEntidade();
        Professor professor = disciplina.getProfessor();
        professor.setId(10000);
        disciplina.setProfessor(professor);
        disciplinaBuilder.persistir(disciplina);
        mockMvc.perform(delete("/api/disciplinas/" + disciplina.getId()));
        excecaoEsperada.expect(RegraNegocioException.class);
    }

    @Test
    public void atualizar() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        disciplina.setNome("Disciplina editada");

        mockMvc.perform(put("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaMapper.toDto(disciplina))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Disciplina editada"))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarDisciplinaVazia() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        disciplina = new Disciplina();
        executarAtualizarDisciplina(disciplina, status().isBadRequest());
    }

    @Test
    public void atualizarDisciplinaNomeExcessoCaracteres() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        disciplina.setNome("campo excedendo a quantidade exorbitante de cinquenta caracteres");
        executarCadastroDisciplina(disciplina, status().isBadRequest());
    }

    @Test
    public void atualizarDisciplinaDescricaoExcessoCaracteres() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        disciplina.setDescricao("Aenean placerat. In vulputate urna eu arcu. Aliquam erat " +
                "volutpat. Suspendisse potenti. Morbi mattis felis at nunc. Duis viverra " +
                "diam non justo. In nisl. Nullam sit amet magna in magna gravida vehicula." +
                "Mauris tincidunt sem sed arcu. Nunc posuere. Nullam lectus justo,");
        executarCadastroDisciplina(disciplina, status().isBadRequest());
    }

    @Test
    public void atualizarDisciplinaCargaHorariaZero() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        disciplina.setCargaHoraria(0);
        executarCadastroDisciplina(disciplina, status().isBadRequest());
    }

    @Test
    public void excluir() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        mockMvc.perform(delete("/api/disciplinas/" + disciplina.getId()))
                .andExpect(status().isOk());

        Assert.assertEquals(0, disciplinaBuilder.obterTodos().size());
    }

    private void executarCadastroDisciplina(Disciplina disciplina, ResultMatcher created) throws Exception {
        mockMvc.perform(post("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaMapper.toDto(disciplina))))
                .andExpect(created);
    }

    private void executarAtualizarDisciplina(Disciplina disciplina, ResultMatcher created) throws Exception {
        mockMvc.perform(put("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaMapper.toDto(disciplina))))
                .andExpect(created);
    }
}
