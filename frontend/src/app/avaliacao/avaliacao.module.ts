import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { AvaliacaoListComponent } from './avaliacao-list/avaliacao-list.component';
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


@NgModule({
  declarations: [
    AvaliacaoListComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    ConfirmDialogModule,
    TableModule,
    InputTextModule,
    PanelModule,
    CalendarModule,
    FormsModule,
    ReactiveFormsModule,
    DynamicDialogModule,
    CardModule
  ],
  providers: [
    ConfirmationService,
    MessageService
  ],
  entryComponents: [
    
  ]
})
export class AvaliacaoModule { }
