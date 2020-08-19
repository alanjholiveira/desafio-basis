import { Component, OnInit } from '@angular/core';
import { DisciplinaService } from '../services/disciplina.service';

import { Disciplina } from '../models/disciplina.model';
import { ConfirmationService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-disciplina-list',
  templateUrl: './disciplina-list.component.html',
  styleUrls: ['./disciplina-list.component.css']
})
export class DisciplinaListComponent implements OnInit {

  disciplinas: Disciplina[];

  constructor(
    private disciplinaService: DisciplinaService,
    private confirmationService: ConfirmationService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.listarDisciplinas();
  }

  listarDisciplinas() {
    this.disciplinaService.listar().subscribe((disciplinas: Disciplina[]) => {
      console.log(disciplinas);
      this.disciplinas = disciplinas;
    });
  }

  deletar(disciplina: Disciplina) {
  }

}
