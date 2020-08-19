import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';

import { environment } from 'src/environments/environment';
import { Professor } from '../models/professor.model';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProfessorService {

  private readonly API = `${environment.API}professores`;
  professores: Professor[] = [];
  professor: Professor;

  constructor(
    private http: HttpClient,
    private datePipe: DatePipe
  ) { }

  /** Listar professores */
  public listar(): Observable<Professor[]> {
    return this.http.get<Professor[]>(this.API)
                    .pipe(
                      catchError(this.handleError)
                    );
  }

  /** Deletar professor */
  public deletar(id: number) {

  }

  /** Obter Professor */
  public obterProfessor(id: number) {

  }

  /** Salvar Professor */
  public save(professor: Professor) {
    
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
