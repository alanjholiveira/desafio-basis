import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AlunoService } from '../services/aluno.service';
import { ConfirmationService } from 'primeng/api';

import { Aluno } from '../models/aluno.model';

@Component({
  selector: 'app-aluno-list',
  templateUrl: './aluno-list.component.html',
  styleUrls: ['./aluno-list.component.css']
})
export class AlunoListComponent implements OnInit {

  alunos: Aluno[];

  constructor(
    private alunoService: AlunoService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) { }

  ngOnInit() {
    this.listarAlunos();
  }

  listarAlunos() {
    this.alunos = this.alunoService.listar();
  }

  deletar(aluno: Aluno) {
  }

}
