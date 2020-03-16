import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfessorListComponent } from './professor-list/professor-list.component';
import { ProfessorFormComponent } from './professor-form/professor-form.component';



@NgModule({
  declarations: [ProfessorListComponent, ProfessorFormComponent],
  imports: [
    CommonModule
  ]
})
export class ProfessorModule { }
