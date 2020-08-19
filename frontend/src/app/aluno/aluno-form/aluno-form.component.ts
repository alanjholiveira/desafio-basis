import { Disciplina } from '../../disciplina/models/disciplina.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Location, formatDate } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DatePipe } from '@angular/common';

import { DataFormatoUtils } from './../../util/dataFormatoUtils';
import { Aluno } from '../models/aluno.model';
import { AlunoService } from '../services/aluno.service';

@Component({
  selector: 'app-aluno-form',
  templateUrl: './aluno-form.component.html',
  styleUrls: ['./aluno-form.component.css'],
  providers: [MessageService]
})
export class AlunoFormComponent implements OnInit {

  public aluno: Aluno = new Aluno();
  public form: FormGroup;
  public listaDisciplinas: any[];  

  public title: string = 'Cadastro';
  public pt = DataFormatoUtils.formatarDataBrasil();
  public submitted: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private alunoService: AlunoService,
    private location: Location,
    private datePipe: DatePipe
  ) { }

  ngOnInit() {
    this.construirForm();
    this.listaDisciplinas = this.converteDropDown(this.alunoService.getDisciplinas());
    this.route.params.subscribe( aluno => {
      if (aluno['id']) { 
        this.title = "Atualização";       

        this.alunoService.obterAluno(aluno['id'])
            .subscribe(data => {
              this.aluno = data;
              this.setForm();
            }, error => console.log(error));

      }
    }); 
  }

  private setForm() {
    // this.listaDisciplinas = this.converteDropDown(this.disciplinaList.getListaDisciplina());
    this.form.get('id').setValue(this.aluno.id);
    this.form.get('nome').setValue(this.aluno.nome);
    this.form.get('cpf').setValue(this.aluno.cpf);
    this.form.get('dataNascimento').setValue(this.aluno.dataNascimento);
    this.form.get('matricula').setValue(this.aluno.matricula);
    this.form.get('disciplinas').setValue(this.obterDisciplinas());
  }

  construirForm() {
    this.form = this.formBuilder.group({
      id: [null],
      nome: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
      cpf: ['', [Validators.required, Validators.maxLength(11)]],
      dataNascimento: [new Date(), [Validators.required]],
      matricula: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
      disciplinas: [[]]
    })
  }

  converteDropDown(arr) {
    return arr.map(disc => {
      return {
        value: disc.id,
        label: disc.nome
      }
    })
  }

  obterDisciplinas() {
    if (this.aluno.disciplinas) {
      return this.aluno.disciplinas.map(disc => disc.id);
    }
  }

  vincularDisciplinas(disciplinasSelecionadas: any[]) {
    let disciplinas = this.alunoService.getDisciplinas();
    return disciplinas.filter(disc => {
      console.log('VINCULAR DISCIPLINA')
      return disciplinasSelecionadas.some(sel => sel == disc.id);
    });
  }

  onSubmit() {

    this.submitted = true;
    if (this.form.valid) {
      this.form.value.disciplinas = this.vincularDisciplinas(this.form.value.disciplinas);
      this.alunoService.save(this.form.value);

      this.router.navigate(['/alunos']);
    } else {
      alert('Campos preenchido incorreto')
    }
   
  }

  onCancel(){
    this.submitted = false;
    this.form.reset();
    this.location.back();
  }

}
