import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';

import { environment } from 'src/environments/environment';
import { Aluno } from '../models/aluno.model';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';

import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class AlunoService {

  private readonly API = `${environment.API}alunos`;
  alunos: Aluno[] = [];

  constructor(
    private http: HttpClient,
    private datePipe: DatePipe,
    private messageService: MessageService
  ) { }

  public listar(): Observable<Aluno[]> {
    //obtem lista de alunos
    return this.http.get<Aluno[]>(this.API)
                    .pipe(
                      catchError(this.handleError)
                    )
  }

  public deletar(matricula: number) {
    // apaga aluno
    return this.http.delete(this.API + '/' + matricula)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  public obterAluno(id: number): Observable<Aluno> {
    // obtem aluno por id
    return this.http.get<Aluno>(this.API + '/' + id)
                    .pipe(
                      catchError(this.handleError)
                    ); 
  }

  // public save(aluno: Aluno) {
  //   if (!aluno.id) {
  //     return this.store(aluno);
  //   }
  //   return this.update(aluno);
  // }

  // public getDisciplinas() {
  //   // obtem lista de disciplinas do aluno
  //   return [];
  // }

  public store(aluno: Aluno): Observable<Aluno> {
    //salva aluno
    return this.http.post<Aluno>(this.API, aluno)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  public update(aluno: Aluno): Observable<Aluno> {
    // atualiza aluno
    return this.http.put<Aluno>(this.API, aluno)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Codigo do erro: ${error.status},
                          messagem: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}
