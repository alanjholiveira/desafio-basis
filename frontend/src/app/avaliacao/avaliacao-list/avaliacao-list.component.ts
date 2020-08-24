import { Component, OnInit } from '@angular/core';
import { AvaliacaoService } from '../services/avaliacao.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Avaliacao } from '../models/avaliacao.model';

@Component({
  selector: 'app-avaliacao-list',
  templateUrl: './avaliacao-list.component.html',
  styleUrls: ['./avaliacao-list.component.css'],
  providers: [DialogService]
})
export class AvaliacaoListComponent implements OnInit {

  avaliacoes: Avaliacao[];
  ref: DynamicDialogRef;

  constructor(
    private avaliacaoService: AvaliacaoService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private messageService: MessageService,
    private dialogService: DialogService
  ) { }

  ngOnInit() {
    this.listarAvaliacoes();
  }
  
  ngOnDestroy() {
    if (this.ref) {
        this.ref.close();
    }
  }   

  private listarAvaliacoes() {
    this.avaliacaoService.listar().subscribe((avaliacao: Avaliacao[]) => {
      this.avaliacoes = avaliacao;
    });
  }

}
