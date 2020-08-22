import { Component, OnInit } from '@angular/core';
import { DisciplinaService } from '../services/disciplina.service';

import { Disciplina } from '../models/disciplina.model';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { DisciplinaListagem } from '../models/disciplinaListagem.model';

@Component({
  selector: 'app-disciplina-list',
  templateUrl: './disciplina-list.component.html',
  styleUrls: ['./disciplina-list.component.css']
})
export class DisciplinaListComponent implements OnInit {

  disciplinas: DisciplinaListagem[];

  constructor(
    private disciplinaService: DisciplinaService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private messageService: MessageService
  ) { }

  ngOnInit() {
    this.listarDisciplinas();
  }

  listarDisciplinas() {
    this.disciplinaService.listar().subscribe((disciplinas: DisciplinaListagem[]) => {
      console.log(disciplinas);
      this.disciplinas = disciplinas;
    });
  }

  deletar(disciplina: Disciplina) {
    this.confirmationService.confirm({
      message: 'Tem certeza que você deseja excluir este registro?',
      header: 'Confirmação de exclusão',
      icon: 'fa-fa-trash',
      acceptLabel: 'Confirmar',
      rejectLabel: 'Cancelar',
      accept: () => {
        this.disciplinaService.deletar(disciplina.id).subscribe(() => {
          this.removeItemTable(disciplina.id);
          this.messageService.add({
            severity: 'success',
            detail: `Disciplina ${disciplina.nome} deletado com sucesso`
          });
        });
      }
    });
  }

  private removeItemTable(id: number) {
    this.disciplinas = this.disciplinas.filter(disciplina => disciplina.id !== id);
  }

}
