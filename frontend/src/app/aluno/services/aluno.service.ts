import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { environment } from 'src/environments/environment';
import { Aluno } from '../models/aluno.model';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AlunoService {

  private readonly API = `${environment.API}alunos`;
  alunos: Aluno[] = [];

  constructor(
    private http: HttpClient,
  ) { }

  /** Listar Aluno */
  public listar(): Observable<Aluno[]> {
    //obtem lista de alunos
    return this.http.get<Aluno[]>(this.API)
                    .pipe(
                      catchError(this.handleError)
                    )
  }

  /** Deletar Aluno */
  public deletar(matricula: number) {
    // apaga aluno
    return this.http.delete(this.API + '/' + matricula)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Obter Aluno */
  public obterAluno(id: number): Observable<Aluno> {
    // obtem aluno por id
    return this.http.get<Aluno>(this.API + '/' + id)
                    .pipe(
                      catchError(this.handleError)
                    ); 
  }

  /** Criar Aluno */
  public store(aluno: Aluno): Observable<Aluno> {
    //salva aluno
    return this.http.post<Aluno>(this.API, aluno)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Atualizar Aluno */
  public update(aluno: Aluno): Observable<Aluno> {
    // atualiza aluno
    return this.http.put<Aluno>(this.API, aluno)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Handle Error */
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
