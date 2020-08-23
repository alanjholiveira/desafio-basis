import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Location } from '@angular/common';

import { ProfessorService } from '../services/professor.service';
import { Professor } from '../models/professor.model';
import { DataFormatoUtils } from 'src/app/util/dataFormatoUtils';

@Component({
  selector: 'app-professor-form',
  templateUrl: './professor-form.component.html',
  styleUrls: ['./professor-form.component.css'],
  providers: [MessageService]
})
export class ProfessorFormComponent implements OnInit {

  public professor: Professor = new Professor();
  public form: FormGroup;
  
  public title: string = 'Cadastro';
  public pt = DataFormatoUtils.formatarDataBrasil();
  public submitted: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private professorService: ProfessorService,
    private location: Location,
  ) { }

  ngOnInit() {
    this.construirForm();

    this.route.params.subscribe( professor => {
      if (professor['id']) {
        this.title = "Atualização";

        this.professorService.obterProfessor(professor['id'])
            .subscribe(data => {
              this.professor = data;
              this.setForm();
            }, error => console.log(error));
      }
    })
  }

  public construirForm() {
    this.form = this.formBuilder.group({
      id: [null],
      nome: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
      matricula: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
      area: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(200)]],
      dataNascimento: [new Date(), [Validators.required]]
    })
  }


  private setForm() {
    this.form.get('id').setValue(this.professor.id);
    this.form.get('nome').setValue(this.professor.nome);
    this.form.get('area').setValue(this.professor.area);
    this.form.get('dataNascimento').setValue(this.professor.dataNascimento);
    this.form.get('matricula').setValue(this.professor.matricula);
  }

  public onSubmit() {
    this.submitted = true;
    if (this.form.valid) {
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

  private salvar(professor: Professor) {
    if (!professor.id) {
      this.professorService.store(professor).subscribe( data => {
        this.messageService.add(
          {
            severity: 'success',
            detail: `Professor(a) ${data.nome} Cadastro(a) com Sucesso`
          }
        )
      });
    } else {
      this.professorService.update(professor).subscribe( data => {
        this.messageService.add({
          severity: 'success',
          detail: `Professor(a) ${data.nome} Atualizado(a) com sucesso`
        });
      });
    }    
  }

}
