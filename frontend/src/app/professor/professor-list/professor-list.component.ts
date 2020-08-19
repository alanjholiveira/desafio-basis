import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ConfirmationService } from 'primeng/api';

import { ProfessorService } from '../services/professor.service';
import { Professor } from '../models/professor.model';

@Component({
  selector: 'app-professor-list',
  templateUrl: './professor-list.component.html',
  styleUrls: ['./professor-list.component.css']
})
export class ProfessorListComponent implements OnInit {

  professores: Professor[];

  constructor(
    private professorService: ProfessorService,
    private confirmationService: ConfirmationService,
    private router: Router,
    ) { }

  ngOnInit() {
    this.listaProfessores();
  }

  listaProfessores() {
    this.professorService.listar().subscribe((professores: Professor[]) => {
      this.professores = professores;
    });
  }

  deletar(aluno: Professor) {
    this.professorService.deletar(aluno.id);
  }

}
