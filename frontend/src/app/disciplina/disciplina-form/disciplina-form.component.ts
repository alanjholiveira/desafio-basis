import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MessageService, SelectItem } from 'primeng/api';
import { Location } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';

import { Disciplina } from '../models/disciplina.model';
import { DisciplinaService } from '../services/disciplina.service';
import { DataFormatoUtils } from 'src/app/util/dataFormatoUtils';
import { ProfessorService } from 'src/app/professor/services/professor.service';
import { Professor } from 'src/app/professor/models/professor.model';

@Component({
  selector: 'app-disciplina-form',
  templateUrl: './disciplina-form.component.html',
  styleUrls: ['./disciplina-form.component.css'],
  providers: [MessageService]
})

export class DisciplinaFormComponent implements OnInit {

  public professores: Professor[];
  public listaProfessores: any[];
  public ativaStatus: SelectItem[];

  public disciplina: Disciplina = new Disciplina();
  public form: FormGroup;

  public title: string = 'Cadastro';
  public pt = DataFormatoUtils.formatarDataBrasil();
  public submitted: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private disciplinaService: DisciplinaService,
    private location: Location,
    private professorService: ProfessorService
  ) { }

  ngOnInit() {
    this.construirForm();
    this.getProfessores();

    this.route.params.subscribe(disciplina => {
      if (disciplina['id']) {
        this.title = "Atualização";

        this.disciplinaService.obterDisciplina(disciplina['id'])
            .subscribe(data => {
              this.disciplina = data;
              this.setForm();
            });
      }
    });
  }

  onSubmit(){
    this.submitted = true;
    if (this.form.valid) {
      this.form.value.professor = this.vincularProfessor(this.form.value.professor);
      this.salvar(this.form.value)
    } else {
      this.messageService.add({
        severity: 'error',
        detail: 'Campos preenchido incorretamente'
      });
    }
  }

  onCancel(){
    this.submitted = false;
    this.form.reset();
    this.location.back();
  }

  private getProfessores() {
    this.professorService.listar().subscribe((data: Professor[]) => {
      this.professores = this.converterModelProfessor(data);
      this.listaProfessores = this.converteDropDown(data);
    })
  }

  private converteDropDown(arr) {
    return arr.map(disc => {
      return {
        value: disc.id,
        label: disc.nome
      }
    });
  }

  private converterModelProfessor(arr: Professor[]) {
    return arr.map(disc => {
      return {
        id: disc.id,
        matricula: disc.matricula,
        nome: disc.nome
      }
    });
  }

  private construirForm()
  {
    this.form = this.formBuilder.group({
      id: [null],
      nome: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      descricao: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(200)]],
      cargaHoraria: ['', [Validators.required, Validators.minLength(0), Validators.maxLength(5)]],
      professor: [[]],
      ativa: [[]]
    });
    this.ativaStatus = [
      {label:'Sim', value:1},
      {label:'Não', value:0},
  ];
  }

  private setForm() {
    this.form.get('id').setValue(this.disciplina.id)
    this.form.get('nome').setValue(this.disciplina.nome);
    this.form.get('descricao').setValue(this.disciplina.descricao);
    this.form.get('cargaHoraria').setValue(this.disciplina.cargaHoraria);
    this.form.get('ativa').setValue(this.disciplina.ativa)
    this.form.get('professor').setValue(this.disciplina.professor.id);
  }

  private vincularProfessor(professorSelecionado: any) {
    return this.professores.find(disc => disc.id == professorSelecionado )
  }

  private salvar(disciplina: Disciplina) {
    if (!disciplina.id) {
      this.disciplinaService.store(disciplina).subscribe(data => {
        this.messageService.add({
          severity: 'success',
          detail: `Disciplina ${data.nome} Cadastro com Sucesso`
        });
      });
    } else {
      this.disciplinaService.update(disciplina).subscribe(data => {
        this.messageService.add({
          severity: 'success',
          detail: `Disciplina ${data.nome} Atualizado com Sucesso`
        });
      });
    }
  }
  
  private obterProfessor(){
    if (this.disciplina.professor) {
      return this.disciplina.professor.id
    }
  }

}
