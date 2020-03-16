package br.com.basis.prova.recurso;

import br.com.basis.prova.ProvaApplication;
import br.com.basis.prova.builder.AlunoBuilder;
import br.com.basis.prova.builder.DisciplinaBuilder;
import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.recurso.util.TestUtil;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AlunoMapper;
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
public class AlunoRecursoTest {

    @Autowired
    private AlunoBuilder alunoBuilder;

    @Autowired
    private DisciplinaBuilder disciplinaBuilder;

    @Autowired
    private AlunoMapper alunoMapper;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Rule
    public ExpectedException excecaoEsperada = ExpectedException.none();

    public AlunoRecursoTest(){

    }

    private void deletaDados() {
        Collection<Aluno> lista = alunoBuilder.obterTodos();
        lista.forEach(aluno -> alunoBuilder.excluirPorId(aluno.getId()));
    }

    @Before
    public void inicializaTeste() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.deletaDados();
        this.alunoBuilder.setCustomizacao(null);
    }

    @Test
    public void cadastrar() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        executarCadastrarAluno(aluno, status().isCreated());
    }

    @Test
    public void cadastrarAlunoVazio() throws Exception {
        Aluno aluno = new Aluno();
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void cadastrarAlunoCpfInvalido1() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setCpf("123456789012");
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void cadastrarAlunoCpfInvalido2() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setCpf("123456");
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void cadastrarAlunoCpfInvalido3() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setCpf("123.123.123");
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void cadastrarAlunoCpfInvalido4() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setCpf("abcqwedfg11");
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test(expected = RegraNegocioException.class)
    public void cadastrarAlunoCpfDuplicado() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        Aluno aluno1 = alunoBuilder.construir();

        excecaoEsperada.expect(RegraNegocioException.class);
    }

    @Test(expected = RegraNegocioException.class)
    public void cadastrarAlunoMatriculaDuplicada() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        Aluno aluno1 = alunoBuilder.customizar(entidade -> {
            entidade.setCpf("05894382718");
        }).construir();

        excecaoEsperada.expect(RegraNegocioException.class);
    }

    @Test
    public void cadastrarAlunoExcedendoCaracteres() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setNome("Nome que ultrapassa a quantia de cinquenta caracteres");
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test(expected = DateTimeException.class)
    public void cadastrarAlunoDataNascimentoInvalida() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setDataNascimento(LocalDate.of(1988, 15, 32));
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void cadastrarAlunoDataNascimentoModificada() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setDataNascimento(LocalDate.of(1988, 02, 10));
        executarCadastrarAluno(aluno, status().isCreated());
    }

    @Test
    public void cadastrarAunoMatriculaInvalida1() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setMatricula("123");
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void cadastrarAlunoMatriculaInvalida2() throws Exception {
        Aluno aluno = alunoBuilder.construirEntidade();
        aluno.setMatricula("1234567");
        executarCadastrarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void atualizar() throws Exception {

        Aluno aluno = alunoBuilder.construir();
        aluno.setNome("Edição Teste");

        mockMvc.perform(put("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoMapper.toDto(aluno))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome" ).value("Edição Teste"))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarAlunoVazio() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        aluno = new Aluno();
        executarAtualizarAluno(aluno, status().isBadRequest());
    }


    @Test
    public void atualizarAlunoCpfValido() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        aluno.setCpf("05894382718");
        mockMvc.perform(put("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoMapper.toDto(aluno))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf" ).value("05894382718"))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarAlunoCpfInvalido1() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        aluno.setCpf("123456789012");
        executarAtualizarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void atualizarAlunoCpfInvalido2() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        aluno.setCpf("123456789");
        executarAtualizarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void atualizarAlunoCpfInvalido3() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        aluno.setCpf("123.123.123");
        executarAtualizarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void atualizarAlunoCpfInvalido4() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        aluno.setCpf("abcwhyegd12");
       executarAtualizarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void excluir() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        mockMvc.perform(delete("/api/alunos/" + aluno.getId()))
                .andExpect(status().isOk());

        Assert.assertEquals(0, alunoBuilder.obterTodos().size());
    }

    @Test
    public void excluirAlunoMatriculado() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        Aluno aluno = alunoBuilder.construir();
        aluno.getDisciplinas().add(disciplina);
        alunoBuilder.persistir(aluno);
        mockMvc.perform(delete("/api/alunos/" + aluno.getId()))
                .andExpect(status().isBadRequest());

        Assert.assertEquals(1, alunoBuilder.obterTodos().size());
    }

    @Test
    public void matricularAlunoDisciplinaInativa() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        disciplina.setAtiva(0);
        disciplinaBuilder.persistir(disciplina);
        Aluno aluno = alunoBuilder.construir();
        aluno.getDisciplinas().add(disciplina);
        executarAtualizarAluno(aluno, status().isBadRequest());
    }

    @Test
    public void matricularAluno() throws Exception {
        Disciplina disciplina = disciplinaBuilder.construir();
        disciplinaBuilder.persistir(disciplina);
        Aluno aluno = alunoBuilder.construir();
        aluno.getDisciplinas().add(disciplina);
        executarAtualizarAluno(aluno, status().isOk());
    }

    @Test
    public void obterTodos() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        mockMvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id", hasSize(1)));
    }

    @Test
    public void detalhar() throws Exception {
        Aluno aluno = alunoBuilder.construir();
        mockMvc.perform(get("/api/alunos/detalhes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private void executarCadastrarAluno(Aluno aluno, ResultMatcher created) throws Exception {
        mockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoMapper.toDto(aluno))))
                .andExpect(created);
    }

    private void executarAtualizarAluno(Aluno aluno, ResultMatcher result) throws Exception {
        mockMvc.perform(put("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoMapper.toDto(aluno))))
                .andExpect(result);
    }

}
