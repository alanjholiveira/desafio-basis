import { Component, OnInit, OnDestroy } from '@angular/core';
import { DynamicDialogRef, DialogService } from 'primeng/dynamicdialog';
import { ConfirmationService, MessageService } from 'primeng/api';

import { DisciplinaService } from '../services/disciplina.service';
import { Disciplina } from '../models/disciplina.model';
import { Router } from '@angular/router';
import { DisciplinaListagem } from '../models/disciplinaListagem.model';
import { DisciplinaDetailComponent } from '../disciplina-detail/disciplina-detail.component';

@Component({
  selector: 'app-disciplina-list',
  templateUrl: './disciplina-list.component.html',
  styleUrls: ['./disciplina-list.component.css'],
  providers: [DialogService]
})
export class DisciplinaListComponent implements OnInit, OnDestroy {

  disciplinas: DisciplinaListagem[];
  ref: DynamicDialogRef;

  constructor(
    private disciplinaService: DisciplinaService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private messageService: MessageService,
    private dialogService: DialogService
  ) { }

  ngOnInit() {
    this.listarDisciplinas();
  }

  listarDisciplinas() {
    this.disciplinaService.listar().subscribe((disciplinas: DisciplinaListagem[]) => {
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
        }, err => {
          this.messageService.add({
            severity: 'error',
            detail: `Não e possivel excluir disciplina ${disciplina.nome} com aluno(a) matriculado(a)`
          });
        });
      }
    });
  }

  showDetail(disciplina: Disciplina) {
    this.ref = this.dialogService.open(DisciplinaDetailComponent, {
      data: disciplina,
      header: `Detalhe da Disciplina: ${disciplina.nome}`,
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
    this.disciplinas = this.disciplinas.filter(disciplina => disciplina.id !== id);
  }

}
