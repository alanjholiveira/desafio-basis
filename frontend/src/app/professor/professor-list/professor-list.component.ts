import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ConfirmationService, MessageService } from 'primeng/api';

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
    private messageService: MessageService
    ) { }

  ngOnInit() {
    this.listaProfessores();
  }

  listaProfessores() {
    this.professorService.listar().subscribe((professores: Professor[]) => {
      this.professores = professores;
    });
  }

  deletar(professor: Professor) {
    this.confirmationService.confirm({
      message: 'Tem certeza que você deseja excluir este registro?',
      header: 'Confirmação de exclusão',
      icon: 'fa fa-trash',
      acceptLabel: 'Confirmar',
      rejectLabel: 'Cancelar',
      accept: () => {
        this.professorService.deletar(professor.matricula).subscribe(() => {
          this.removeItemTable(professor.id);
          this.messageService.add({
            severity: 'success',
            detail: `Professor ${professor.nome} deletado com sucesso`
          });
        });
      }
    });
  }

  private removeItemTable(id: number) {
    this.professores = this.professores.filter(professor => professor.id !== id);
  }

}
