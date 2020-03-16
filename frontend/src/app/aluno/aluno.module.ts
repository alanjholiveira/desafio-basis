import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService} from 'primeng/api';
import {ButtonModule} from 'primeng/button';
import {TableModule} from 'primeng/table';
import {InputTextModule} from 'primeng/inputtext';
import {InputMaskModule} from 'primeng/inputmask';
import {PanelModule, Panel} from 'primeng/panel';
import {ToastModule} from 'primeng/toast';
import {CalendarModule} from 'primeng/calendar';
import {MultiSelectModule} from 'primeng/multiselect';

import { AlunoListComponent } from './aluno-list/aluno-list.component';
import { AlunoFormComponent } from './aluno-form/aluno-form.component';

@NgModule({
  declarations: [
    AlunoListComponent,
    AlunoFormComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    ConfirmDialogModule,
    TableModule,
    InputMaskModule,
    InputTextModule,
    PanelModule,
    ToastModule,
    CalendarModule,
    MultiSelectModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    ConfirmationService,
  ]
})
export class AlunoModule { }
