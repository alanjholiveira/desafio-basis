import { NgModule } from '@angular/core';
import { ModuleWithProviders } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { HomeComponent } from "./home/home.component";
import { AlunoFormComponent } from './aluno/aluno-form/aluno-form.component';
import { AlunoListComponent } from './aluno/aluno-list/aluno-list.component';
import { ProfessorFormComponent } from './professor/professor-form/professor-form.component';
import { ProfessorListComponent } from './professor/professor-list/professor-list.component';
import { DisciplinaFormComponent } from './disciplina/disciplina-form/disciplina-form.component';
import { DisciplinaListComponent } from './disciplina/disciplina-list/disciplina-list.component';

const appRoutes: Routes = [
  { path: "home", component: HomeComponent },
  { path: 'alunos', component: AlunoListComponent },
  { path: 'aluno', component: AlunoFormComponent },
  { path: 'aluno/:id', component: AlunoFormComponent },
  { path: 'professores', component: ProfessorListComponent },
  { path: 'professor', component: ProfessorFormComponent },
  { path: 'professor/:id', component: ProfessorFormComponent },
  { path: 'disciplinas', component: DisciplinaListComponent },
  { path: 'disciplina', component: DisciplinaFormComponent },
  { path: 'disciplina/:id', component: DisciplinaFormComponent },
  { path: "", redirectTo: "/home", pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}