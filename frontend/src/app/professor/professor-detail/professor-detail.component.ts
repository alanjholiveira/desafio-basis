import { Component, OnInit } from '@angular/core';
import { ProfessorService } from '../services/professor.service';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { ProfessorDetail } from '../models/professor.detail.model';

@Component({
  selector: 'app-professor-detail',
  templateUrl: './professor-detail.component.html',
  styleUrls: ['./professor-detail.component.css']
})
export class ProfessorDetailComponent implements OnInit {

  public professorDetail: ProfessorDetail = new ProfessorDetail();
  public disciplinas: any;

  constructor(
    private professorService: ProfessorService,
    private ref: DynamicDialogRef,
    private config: DynamicDialogConfig
  ) { }

  ngOnInit() {
    this.listarDetailProfessor(this.config.data.id);
  }

  closeDialog() {
    this.ref.close();
  }

  private listarDetailProfessor(id: number) {
    this.professorService.detail(id).subscribe(data => {
      this.professorDetail = data;
      this.disciplinas = data.disciplinas;
    })
  }

}
