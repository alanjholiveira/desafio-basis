import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';

import { AlunoService } from '../services/aluno.service';
import { Aluno } from '../models/aluno.model';
import { AlunoDetailComponent } from '../aluno-detail/aluno-detail.component';

@Component({
  selector: 'app-aluno-list',
  templateUrl: './aluno-list.component.html',
  styleUrls: ['./aluno-list.component.css'],
  providers: [DialogService]
})
export class AlunoListComponent implements OnInit, OnDestroy {

  alunos: Aluno[];
  ref: DynamicDialogRef;

  constructor(
    private alunoService: AlunoService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private messageService: MessageService,
    private dialogService: DialogService
  ) { }

  ngOnInit() {
    this.listarAlunos();
  }

  listarAlunos() {
    this.alunoService.listar().subscribe((aluno: Aluno[]) => {
      this.alunos = aluno;
    });
  }

  deletar(aluno: Aluno) {
    this.confirmationService.confirm({
      message: 'Tem certeza que você deseja excluir este registro?',
      header: 'Confirmação de exclusão',
      icon: 'fa fa-trash',
      acceptLabel: "Confirmar",
      rejectLabel: "Cancelar",
      accept: () => {
        this.alunoService.deletar(aluno.matricula).subscribe(() => {
          this.removeItemTable(aluno.id);
           this.messageService.add(
             { 
               severity:'success',
               detail: `Aluno ${aluno.nome} deletado com sucesso`
             }
           )
         }
       );
      },
    })           
  }

  showDetail(aluno: Aluno) {
    this.ref = this.dialogService.open(AlunoDetailComponent, {
      data: aluno,
      header: `Detalhe do Aluno: ${aluno.nome} - Matricula: ${aluno.matricula}`,
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
    this.alunos = this.alunos.filter(aluno => aluno.id !== id);
  }

}
