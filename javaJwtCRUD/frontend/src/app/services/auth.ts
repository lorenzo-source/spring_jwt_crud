import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  register(email: string, password: string, nome: string, cognome: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, { email, password, nome, cognome });
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`,
      { email, password },
      { responseType: 'text' }
    );
  }

  refresh(refreshToken: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/refresh`, { refreshToken });
  }

  saveToken(token: string): void {
    sessionStorage.setItem('token', token);
  }

  saveRefreshToken(token: string): void {
    sessionStorage.setItem('refreshToken', token);
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  getRefreshToken(): string | null {
    return sessionStorage.getItem('refreshToken');
  }

  logout(): void {
    sessionStorage.clear();
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
