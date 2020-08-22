import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { environment } from 'src/environments/environment';
import { Professor } from '../models/professor.model';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProfessorService {

  private readonly API = `${environment.API}professores`;
  professores: Professor[] = [];
  professor: Professor;

  constructor(
    private http: HttpClient,
  ) { }

  /** Listar professores */
  public listar(): Observable<Professor[]> {
    return this.http.get<Professor[]>(this.API)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Deletar professor */
  public deletar(matricula: number) {
    return this.http.delete(this.API + '/' + matricula)
                    .pipe(
                      catchError(this.handleError)
                    )
  }

  /** Obter Professor */
  public obterProfessor(id: number): Observable<Professor> {
    return this.http.get<Professor>(this.API + '/' + id)
               .pipe(
                 catchError(this.handleError)
               );
  }

  /** Salvar Professor */
  public store(professor: Professor): Observable<Professor> {
    return this.http.post<Professor>(this.API, professor)
                    .pipe(
                      catchError(this.handleError)
                    )
  }

  /** Atualizar Professor */
  public update(professor: Professor): Observable<Professor> {
    return this.http.put<Professor>(this.API, professor)
                    .pipe(
                      catchError(this.handleError)
                    )
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
