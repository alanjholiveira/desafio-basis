import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DisciplinaListComponent } from './disciplina-list/disciplina-list.component';
import { DisciplinaFormComponent } from './disciplina-form/disciplina-form.component';



@NgModule({
  declarations: [DisciplinaListComponent, DisciplinaFormComponent],
  imports: [
    CommonModule
  ]
})
export class DisciplinaModule { }
