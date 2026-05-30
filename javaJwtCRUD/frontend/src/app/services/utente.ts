import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Utente {
  id?: string;
  email?: string;
  nome?: string;
  cognome?: string;
  password?: string;
  dataNascita?: string;
  dataCreazione?: string;
  dataUltimoLogin?: string;
  dataCancellazione?: string;
  refreshToken?: string;
}

@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  private baseUrl = 'http://localhost:8080/api/utenti';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Utente[]> {
    return this.http.get<Utente[]>(this.baseUrl);
  }

  getById(id: string): Observable<Utente> {
    return this.http.get<Utente>(`${this.baseUrl}/${id}`);
  }

  create(utente: any): Observable<Utente> {
    return this.http.post<Utente>(this.baseUrl, utente);
  }

  update(utente: Utente): Observable<Utente> {
    return this.http.put<Utente>(this.baseUrl, utente);
  }

  delete(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  getDeleted(): Observable<Utente[]> {
    return this.http.get<Utente[]>(`${this.baseUrl}/deleted`);
  }
}


