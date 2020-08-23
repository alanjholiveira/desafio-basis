import { Component, OnInit, OnDestroy } from '@angular/core';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';

import { AlunoService } from '../services/aluno.service';
import { AlunoAvaliacao } from '../models/aluno.avaliacao.model';

@Component({
  selector: 'app-aluno-avaliacao',
  templateUrl: './aluno-avaliacao.component.html',
  styleUrls: ['./aluno-avaliacao.component.css']
})
export class AlunoAvaliacaoComponent implements OnInit, OnDestroy {

  public alunoAvaliacao: AlunoAvaliacao = new AlunoAvaliacao();

  constructor(
    private alunoService: AlunoService,
    private ref: DynamicDialogRef,
    private config: DynamicDialogConfig
  ) { }

  ngOnInit() {
    this.listarAvaliacoes(this.config.data.id);
  }

  ngOnDestroy() {
    if (this.ref) {
      this.ref.close
    }
  }

  closeDialog() {
    this.ref.close()
  }

  private listarAvaliacoes(id: number) {
    this.alunoService.avaliacao(id).subscribe(data => {
      this.alunoAvaliacao = data;
    })
  }

}
