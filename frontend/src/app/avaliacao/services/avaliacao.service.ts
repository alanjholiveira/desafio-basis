import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Avaliacao } from '../models/avaliacao.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AvaliacaoService {

  private readonly API = `${environment.API}avaliacoes`;
  avaliacoes: Avaliacao[] = [];

  constructor(
    private http: HttpClient
  ) { }


  /** Listar Avaliaçoes */
  public listar(): Observable<Avaliacao[]> {
    return this.http.get<Avaliacao[]>(this.API)
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
