import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';

import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TableModule } from 'primeng/table';
import { InputMaskModule } from 'primeng/inputmask';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { ToastModule } from 'primeng/toast';
import { CalendarModule } from 'primeng/calendar';
import { MultiSelectModule } from 'primeng/multiselect';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { CardModule } from 'primeng/card';

import { ProfessorListComponent } from './professor-list/professor-list.component';
import { ProfessorFormComponent } from './professor-form/professor-form.component';
import { ProfessorDetailComponent } from './professor-detail/professor-detail.component';

@NgModule({
  declarations: [
    ProfessorListComponent,
    ProfessorFormComponent,
    ProfessorDetailComponent
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
    ReactiveFormsModule,
    DynamicDialogModule,
    CardModule
  ],
  providers: [
    ConfirmationService,
    MessageService,
  ],
  entryComponents: [
    ProfessorDetailComponent
  ]
})
export class ProfessorModule { }
