import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
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
