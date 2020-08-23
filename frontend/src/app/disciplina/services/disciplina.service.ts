import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { Disciplina } from '../models/disciplina.model';
import { environment } from 'src/environments/environment';
import { catchError } from 'rxjs/operators';
import { DisciplinaListagem } from '../models/disciplinaListagem.model';
import { DisciplinaDetail } from '../models/disciplina.detail.model';

@Injectable({
  providedIn: 'root'
})
export class DisciplinaService {

  private readonly API = `${environment.API}disciplinas`;

  disciplinas: Disciplina[] = [];
  disciplina: Disciplina;

  constructor(
    private http: HttpClient
  ) { }

  /** Listar Disciplina */
  public listar(): Observable<DisciplinaListagem[]> {
    return this.http.get<Disciplina[]>(this.API)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Deletar Disciplina */
  public deletar(id: number) {
    return this.http.delete(this.API + '/' + id)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Obter Disciplina */
  public obterDisciplina(id: number): Observable<Disciplina> {
    return this.http.get<Disciplina>(this.API + '/' + id)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Salvar Disciplina */
  public store(disciplina: Disciplina): Observable<Disciplina> {
    return this.http.post<Disciplina>(this.API, disciplina)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Atualizar Disciplina */
  public update(disciplina: Disciplina): Observable<Disciplina> {
    return this.http.put<Disciplina>(this.API, disciplina)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Detalhe Disciplina */
  public detail(id: number): Observable<DisciplinaDetail> {
    return this.http.get<DisciplinaDetail>(this.API + '/detalhes/' + id )
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
