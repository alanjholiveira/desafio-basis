import { Component, OnInit, OnDestroy } from '@angular/core';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';

import { AlunoService } from '../services/aluno.service';
import { AlunoDetail } from '../models/aluno.detail.model';

@Component({
  selector: 'app-aluno-detail',
  templateUrl: './aluno-detail.component.html',
  styleUrls: ['./aluno-detail.component.css']
})
export class AlunoDetailComponent implements OnInit, OnDestroy {

  public alunoDetail: AlunoDetail;
  public disciplinas: any;

  constructor(
    private alunoService: AlunoService,
    private ref: DynamicDialogRef,
    private config: DynamicDialogConfig
  ) { }

  ngOnInit() {
    this.listarDetailAluno(this.config.data.id);
  }
  
  ngOnDestroy() {
    if (this.ref) {
      this.ref.close
    }
  }

  closeDialog() {
    console.log('tes');
    this.ref.close()
  }

  private listarDetailAluno(id: number) {
    this.alunoService.detail(id).subscribe(data => {
      this.alunoDetail = data;
      this.disciplinas = data.disciplinas
    })
  }

}
