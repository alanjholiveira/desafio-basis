import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app.routing.module';
import { AlunoModule } from './aluno/aluno.module';
import { DisciplinaModule } from './disciplina/disciplina.module';
import { ProfessorModule } from './professor/professor.module';

import {MenuModule} from 'primeng/menu';
import {TabMenuModule} from 'primeng/tabmenu';
import {ConfirmationService} from 'primeng/api';

import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { HomeComponent } from './home/home.component';


@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MenuModule,
    TabMenuModule,
    AlunoModule,
    DisciplinaModule,
    ProfessorModule,
    AppRoutingModule
  ],
  providers: [
    ConfirmationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
