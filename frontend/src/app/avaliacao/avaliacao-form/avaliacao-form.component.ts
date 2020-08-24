import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Location } from '@angular/common';
import { Avaliacao } from '../models/avaliacao.model';
import { DataFormatoUtils } from 'src/app/util/dataFormatoUtils';
import { AvaliacaoService } from '../services/avaliacao.service';
import { DisciplinaService } from 'src/app/disciplina/services/disciplina.service';
import { Disciplina } from 'src/app/disciplina/models/disciplina.model';
import { AlunoService } from 'src/app/aluno/services/aluno.service';
import { Aluno } from 'src/app/aluno/models/aluno.model';

@Component({
  selector: 'app-avaliacao-form',
  templateUrl: './avaliacao-form.component.html',
  styleUrls: ['./avaliacao-form.component.css'],
  providers: [MessageService]
})
export class AvaliacaoFormComponent implements OnInit {

  public avaliacao: Avaliacao = new Avaliacao();
  public form: FormGroup;
  public disciplinas: Disciplina[];
  public alunos: Aluno[];
  public listaDisciplinas: any[];
  public listaAlunos: any[];
  public alunoSelecionado: Aluno;

  public title: string = 'Cadastro';
  public pt = DataFormatoUtils.formatarDataBrasil();
  public submitted: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private avaliacaoService: AvaliacaoService,
    private location: Location,
    private disciplinaService: DisciplinaService,
    private alunoService: AlunoService
  ) { }

  ngOnInit() {
    this.construirForm();
    this.getAlunos();

    this.route.params.subscribe(avaliacao => {
      if (avaliacao['id']) {
        this.title = "Atualização";

        this.avaliacaoService.obterAvaliacao(avaliacao['id']).subscribe(data => {
          this.avaliacao = data;
          this.setForm();
        })
      }
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.valid) {
      this.form.value.aluno = this.vincularAluno(this.form.value.aluno)
      this.form.value.disciplina = this.vincularDisciplina(this.form.value.disciplina);
      this.salvar(this.form.value)
    } else {
      this.messageService.add({
        severity: 'error',
        detail: 'Campos preenchido incorretamente'
      })
    }
  }

  onCancel() {
    this.submitted = false;
    this.form.reset();
    this.location.back();
  }

  onChange(aluno){
   this.getDisciplinas(aluno);
  }

  private setForm() {
    this.form.get('id').setValue(this.avaliacao.id);
    this.form.get('data').setValue(this.avaliacao.data);
    this.form.get('nota').setValue(this.avaliacao.nota);
    this.form.get('aluno').setValue(this.avaliacao.aluno.id);
    this.getDisciplinas(this.avaliacao.aluno.id);
    this.form.get('disciplina').setValue(this.avaliacao.disciplina.id);
  }

  private vincularAluno(alunoSelecionado) {
    return this.alunos.find(disc => disc.id == alunoSelecionado)
  }

  private vincularDisciplina(disciplinaSelecionado) {
    return this.disciplinas.find(disc => disc.id == disciplinaSelecionado);
  }

  private getAlunos() {
    this.alunoService.listar().subscribe((data: Aluno[]) => {
      this.alunos = this.converterModelAluno(data);
      this.listaAlunos = this.converteDorpDown(data);
    })
  }

  private getDisciplinas(id) {
    this.alunoService.detail(id).subscribe(data => {
      this.disciplinas = this.converterModelDisciplina(data.disciplinas);
      this.listaDisciplinas = this.converteDorpDown(data.disciplinas);
    })
  }

  private construirForm() {
    this.form = this.formBuilder.group({
      id: [null],
      data: ['', [Validators.required]],
      nota: ['', [Validators.required]],
      disciplina: [[], [Validators.required]],
      aluno: [[], [Validators.required]]
    });
  }

  private converterModelAluno(arr: Aluno[]) {
    return arr.map(disc => {
      return {
        id: disc.id,
        nome: disc.nome,
        matricula: disc.matricula
      }
    });
  }

  private converterModelDisciplina(arr: any) {
    return arr.map(disc => {
      return {
        id: disc.id,
        nome: disc.nome
      }
    });
  }

  private converteDorpDown(arr) {
    return arr.map(disc => {
      return {
        value: disc.id,
        label: disc.nome
      }
    })
  }

  private salvar(avaliacao: Avaliacao) {
    if (!avaliacao.id) {
      this.avaliacaoService.store(avaliacao).subscribe(data => {
        this.messageService.add({
          severity: 'success',
          detail: `Avaliação do Aluno(a) ${data.aluno.nome} referente a Disciplina ${data.disciplina.nome} salvo com Sucesso`
        });
      });
    } else {
      this.avaliacaoService.update(avaliacao).subscribe(data => {
        this.messageService.add({
          severity: 'success',
          detail: `Avaliação do Aluno(a) ${data.aluno.nome} referente a Disciplina ${data.disciplina.nome} atualizado com Sucesso`
        });
      });
    }
  }

}
