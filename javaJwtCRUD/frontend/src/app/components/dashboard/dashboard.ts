import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="container">
      <div class="header">
        <h1>Dashboard</h1>
        <button (click)="logout()" class="btn-logout">Logout</button>
      </div>
      <div class="cards">
        <div class="card" routerLink="/users">
          <h3>Gestione Utenti</h3>
          <p>Visualizza, modifica ed elimina utenti</p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .container { padding: 40px; max-width: 1200px; margin: 0 auto; }
    .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 40px; }
    h1 { color: #1a3a5c; }
    .btn-logout { padding: 8px 16px; background: #e74c3c; color: white; border: none; border-radius: 4px; cursor: pointer; }
    .cards { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; }
    .card { background: white; padding: 24px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); cursor: pointer; border-left: 4px solid #1a3a5c; }
    .card:hover { transform: translateY(-2px); transition: transform 0.2s; }
    h3 { color: #1a3a5c; margin-bottom: 8px; }
    p { color: #666; font-size: 14px; }
  `]
})
export class Dashboard {
  constructor(private authService: AuthService, private router: Router) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
