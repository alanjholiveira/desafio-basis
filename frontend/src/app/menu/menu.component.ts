import { Component, OnInit } from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  items = [];

  activeItem;

  constructor() { }

  ngOnInit() {
    this.items = [
      {label: 'Home', icon: 'pi pi-home', link: ''},
      {label: 'Alunos', icon: 'pi pi-users', link : '/alunos'},
      {label: 'Professores', icon: 'pi pi-briefcase', link : '/professores'},
      {label: 'Disciplinas', icon: 'pi pi-id-card', link: '/disciplinas'},
    ];

    this.activeItem = this.items[0];
  }

  ativarItem(event, index) {
    this.activeItem = this.items[index];
  }

}
