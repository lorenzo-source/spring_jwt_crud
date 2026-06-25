import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
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
