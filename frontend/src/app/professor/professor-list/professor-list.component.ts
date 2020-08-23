import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';

import { ProfessorService } from '../services/professor.service';
import { Professor } from '../models/professor.model';
import { ProfessorDetailComponent } from '../professor-detail/professor-detail.component';

@Component({
  selector: 'app-professor-list',
  templateUrl: './professor-list.component.html',
  styleUrls: ['./professor-list.component.css'],
  providers: [DialogService]
})
export class ProfessorListComponent implements OnInit, OnDestroy {

  professores: Professor[];
  ref: DynamicDialogRef;

  constructor(
    private professorService: ProfessorService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private messageService: MessageService,
    private dialogService: DialogService
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
        }, err => {
          this.messageService.add({
            severity: 'error',
            detail: `Não e possivel excluir professor(a) ${professor.nome} responsável por disciplina`
          });
        });
      }
    });
  }

  showDetail(professor: Professor) {
    this.ref = this.dialogService.open(ProfessorDetailComponent, {
      data: professor,
      header: `Detalhe do Professor(a): ${professor.nome}`,
      closable: false,
      width: '70%'
    });
  }

  ngOnDestroy() {
    if (this.ref) {
        this.ref.close();
    }
  } 

  private removeItemTable(id: number) {
    this.professores = this.professores.filter(professor => professor.id !== id);
  }

}
