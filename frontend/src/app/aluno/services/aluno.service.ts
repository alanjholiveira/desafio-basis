import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';

import { environment } from 'src/environments/environment';
import { Aluno } from '../models/aluno.model';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AlunoService {

  private readonly API = `${environment.API}alunos`;
  alunos: Aluno[] = [];

  constructor(
    private http: HttpClient,
    private datePipe: DatePipe
  ) { }

  // httpOptions = {
  //   headers: new HttpHeaders({'Content-Type': 'application/json'})
  // }

  public listar(): Observable<Aluno[]> {
    //obtem lista de alunos
    return this.http.get<Aluno[]>(this.API)
                    .pipe(
                      catchError(this.handleError)
                    )
  }

  listar2(): Observable<any>{
    return this.http.get(this.API);
  }

  public deletar(id: number) {
    // apaga aluno
    return this.http.delete(this.API + '/' + id)
                    .pipe(
                      catchError(this.handleError)
                    )
                    .subscribe(() =>
                      alert("Aluno deletado com sucesso")
                    );
  }

  public obterAluno(id: number): Observable<Aluno> {
    // obtem aluno por id
    return this.http.get<Aluno>(this.API + '/' + id)
                    .pipe(
                      map( aluno => {
                        // aluno.dataNascimento = new Date(this.datePipe.transform(Date.now()));
                        return aluno;
                      }),
                      catchError(this.handleError)
                    ); 
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
    return this.http.post<Aluno>(this.API, aluno)
                    .pipe(
                      catchError(this.handleError)
                    ).subscribe( () =>
                        alert('Aluno Cadastro com Sucesso')
                      );
  }

  private update(aluno: Aluno) {
    // atualiza aluno
    return this.http.put<Aluno>(this.API, aluno)
                    .pipe(
                      catchError(this.handleError)
                    ).subscribe( dados =>
                      alert('Aluno Atualizado com Sucesso')
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
