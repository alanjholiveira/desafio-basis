import { Component, OnInit } from '@angular/core';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';

import { DisciplinaService } from '../services/disciplina.service';
import { DisciplinaDetail } from '../models/disciplina.detail.model'
import { Professor } from 'src/app/professor/models/professor.model';

@Component({
  selector: 'app-disciplina-detail',
  templateUrl: './disciplina-detail.component.html',
  styleUrls: ['./disciplina-detail.component.css']
})
export class DisciplinaDetailComponent implements OnInit {

  public disciplinaDetail: DisciplinaDetail = new DisciplinaDetail();
  public professor: Professor = new Professor();

  constructor(
    public disciplinaService: DisciplinaService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) { }

  ngOnInit() {
    this.listarDetailDisciplina(this.config.data.id);
  }

  closeDialog() {
    this.ref.close()
  }

  private listarDetailDisciplina(id: number) {
    this.disciplinaService.detail(id).subscribe(data => {
      this.disciplinaDetail = data;
      this.professor = data.professor;
    })
  }

}
