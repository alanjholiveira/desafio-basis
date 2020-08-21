import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AlunoService } from '../services/aluno.service';
import { ConfirmationService, MessageService } from 'primeng/api';

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
    private router: Router,
    private messageService: MessageService
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

  private removeItemTable(id: number) {
    this.alunos = this.alunos.filter(aluno => aluno.id !== id);
  }

}
