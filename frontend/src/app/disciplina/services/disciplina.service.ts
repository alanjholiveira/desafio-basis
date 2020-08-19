import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { Disciplina } from '../models/disciplina.model';
import { environment } from 'src/environments/environment';
import { catchError } from 'rxjs/operators';

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
  public listar(): Observable<Disciplina[]> {
    return this.http.get<Disciplina[]>(this.API)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Deletar disciplina */
  public deletar(id: number) {
    return this.http.delete(this.API + '/' + id)
  }

  /** Obter disciplina */
  public obterProfessor(id: number) {

  }

  /** Salvar disciplina */
  public save(professor: Disciplina) {
    
  }


  /** Tratar Error */
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
