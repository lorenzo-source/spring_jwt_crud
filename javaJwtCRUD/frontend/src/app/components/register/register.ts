import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="container">
      <div class="card">
        <h2>Registrazione</h2>
        <div class="form-group">
          <input type="text" placeholder="Nome" [(ngModel)]="nome" class="input"/>
        </div>
        <div class="form-group">
          <input type="text" placeholder="Cognome" [(ngModel)]="cognome" class="input"/>
        </div>
        <div class="form-group">
          <input type="email" placeholder="Email" [(ngModel)]="email" class="input"/>
        </div>
        <div class="form-group">
          <input type="password" placeholder="Password" [(ngModel)]="password" class="input"/>
        </div>
        <button (click)="register()" class="btn" [disabled]="loading">
          {{ loading ? 'Caricamento...' : 'Registrati' }}
        </button>
        <p class="link">Hai già un account? <a routerLink="/login">Accedi</a></p>
        <p class="error" *ngIf="error">{{ error }}</p>
        <p class="success" *ngIf="success">{{ success }}</p>
      </div>
    </div>
  `,
  styles: [`
    .container { display: flex; justify-content: center; align-items: center; height: 100vh; background: #f5f5f5; }
    .card { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
    h2 { margin-bottom: 24px; color: #1a3a5c; }
    .form-group { margin-bottom: 16px; }
    .input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; box-sizing: border-box; }
    .btn { width: 100%; padding: 12px; background: #1a3a5c; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
    .btn:hover { background: #2a4a6c; }
    .link { margin-top: 16px; text-align: center; font-size: 14px; }
    .error { color: red; font-size: 14px; margin-top: 8px; }
    .success { color: green; font-size: 14px; margin-top: 8px; }
  `]
})
export class Register {
  nome = '';
  cognome = '';
  email = '';
  password = '';
  error = '';
  success = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    if (this.loading) return;

    if (!this.email || !this.password || !this.nome || !this.cognome) {
      this.error = 'Tutti i campi sono obbligatori';
      return;
    }

    if (this.password.length < 6) {
      this.error = 'La password deve essere di almeno 6 caratteri';
      return;
    }

    this.loading = true;
    this.error = '';
    this.authService.register(this.email, this.password, this.nome, this.cognome).subscribe({
      next: () => {
        this.loading = false;
        this.success = 'Registrazione completata. Puoi ora effettuare il login.';
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: () => {
        this.loading = false;
        this.error = 'Errore durante la registrazione. Email già in uso.';
      }
    });
  }
}
