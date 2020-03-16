import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environments/environment';
import { Aluno } from '../models/aluno.model';

@Injectable({
  providedIn: 'root'
})
export class AlunoService {

  private readonly API = `${environment.API}alunos`;
  alunos: Aluno[] = [];

  constructor(
    private http: HttpClient
  ) { }

  public listar() {
    //obtem lista de alunos
    return this.alunos;
  }

  public deletar(id: number) {
    // apaga aluno
  }

  public obterAluno(id: number) {
    // obtem aluno por id
    return new Aluno();
  }

  public save(aluno: Aluno) {
    if (!aluno.id) {
      return this.store(aluno);
    }
    return this.update(aluno);
  }

  public getDisciplinas() {
    // obtem lista de disciplinas do aluno
    return [];
  }

  private store(aluno: Aluno) {
    //salva aluno
  }

  private update(aluno: Aluno) {
    // atualiza aluno
  }

}
