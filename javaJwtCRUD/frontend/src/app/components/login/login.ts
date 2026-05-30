import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="container">
      <div class="card">
        <h2>Login</h2>
        <div class="form-group">
          <input
            type="email"
            placeholder="Email"
            [(ngModel)]="email"
            class="input"/>
        </div>
        <div class="form-group">
          <input
            type="password"
            placeholder="Password"
            [(ngModel)]="password"
            class="input"/>
        </div>
        <button (click)="login()" class="btn" [disabled]="loading">
          {{ loading ? 'Caricamento...' : 'Login' }}
        </button>
        <p class="link">Non hai un account? <a routerLink="/register">Registrati</a></p>
        <p class="error" *ngIf="error">{{ error }}</p>
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
  `]
})
export class Login {
  email = '';
  password = '';
  error = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    if (this.loading) return;
    this.loading = true;
    this.error = '';

    this.authService.login(this.email, this.password).subscribe({
      next: (response: any) => {
        this.loading = false;
        const text = response as string;
        const lines = text.split('\n');
        const token = lines[0].replace('Token:', '').trim();
        const refreshToken = lines[1].replace('Refresh Token:', '').trim();
        this.authService.saveToken(token);
        this.authService.saveRefreshToken(refreshToken);
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.loading = false;
        this.error = 'Email o password errati';
      }
    });
  }
}
