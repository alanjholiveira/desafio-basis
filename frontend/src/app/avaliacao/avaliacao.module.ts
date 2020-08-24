import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { CardModule } from 'primeng/card';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { DropdownModule } from 'primeng/dropdown';

import { AvaliacaoListComponent } from './avaliacao-list/avaliacao-list.component';
import { AvaliacaoFormComponent } from './avaliacao-form/avaliacao-form.component';


@NgModule({
  declarations: [
    AvaliacaoListComponent,
    AvaliacaoFormComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    ConfirmDialogModule,
    TableModule,
    InputTextModule,
    PanelModule,
    ToastModule,
    CalendarModule,
    FormsModule,
    ReactiveFormsModule,
    DynamicDialogModule,
    CardModule,
    DropdownModule,
  ],
  providers: [
    ConfirmationService,
    MessageService
  ],
  entryComponents: [
    
  ]
})
export class AvaliacaoModule { }
