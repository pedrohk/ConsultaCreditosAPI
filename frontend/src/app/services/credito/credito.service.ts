import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Credito } from '../../models/credito.model';

const API_URL = 'http://localhost:8080/api/creditos';

@Injectable({
  providedIn: 'root'
})
export class CreditoService {

  constructor(private http: HttpClient) { }

  consultarPorNumeroCredito(numeroCredito: string): Observable<Credito[]> {
    return this.http.get<Credito[]>(`${API_URL}/credito/${numeroCredito}`);
  }

  consultarPorNumeroNfse(numeroNfse: string): Observable<Credito[]> {
    return this.http.get<Credito[]>(`${API_URL}/${numeroNfse}`);
  }
}
