import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ProfessorListComponent } from './professor-list/professor-list.component';
import { ProfessorFormComponent } from './professor-form/professor-form.component';
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
import { ConfirmationService } from 'primeng/api';
import { ProfessorService } from './services/professor.service';

@NgModule({
  declarations: [
    ProfessorListComponent,
    ProfessorFormComponent
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
  ],
  providers: [
    ConfirmationService,
    ProfessorService,
    DatePipe
  ]
})
export class ProfessorModule { }
