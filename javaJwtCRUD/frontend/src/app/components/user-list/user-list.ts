import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { UtenteService, Utente } from '../../services/utente';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, DatePipe],
 templateUrl: './user-list.html',
  styleUrl: './user-list.css'
})

export class UserList implements OnInit {
  users: Utente[] = [];
  deletedUsers: Utente[] = [];
  newUser: any = { nome: '', cognome: '', email: '', password: '' };
  editingId: string | null = null;
  editUser: any = {};
  error = '';
  success = '';

  constructor(
    private utenteService: UtenteService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loadUsers();
    this.loadDeletedUsers();
  }

  loadUsers() {
    this.utenteService.getAll().subscribe({
      next: (users) => {
        this.users = [...users];
        this.cdr.detectChanges();
      },
      error: () => this.error = 'Errore nel caricamento utenti'
    });
  }

  loadDeletedUsers() {
    this.utenteService.getDeleted().subscribe({
      next: (users) => {
        this.deletedUsers = [...users];
        this.cdr.detectChanges();
      },
      error: () => console.log('Errore nel caricamento utenti eliminati')
    });
  }


  createUser() {
    this.utenteService.create(this.newUser).subscribe({
      next: () => {
        this.success = 'Utente aggiunto con successo';
        this.newUser = { nome: '', cognome: '', email: '', password: '' };
        this.loadUsers();
        setTimeout(() => this.success = '', 3000);
      },
      error: () => this.error = 'Errore nella creazione utente'
    });
  }

  startEdit(user: Utente) {
    this.editingId = user.id!;
    this.editUser = { ...user };
  }

  saveEdit() {
    this.utenteService.update(this.editUser).subscribe({
      next: () => {
        this.editingId = null;
        this.loadUsers();
      },
      error: () => this.error = 'Errore nella modifica utente'
    });
  }

  cancelEdit() {
    this.editingId = null;
    this.editUser = {};
  }

  deleteUser(id: string) {
    if (confirm('Sei sicuro di voler eliminare questo utente?')) {
      this.utenteService.delete(id).subscribe({
        next: () => {
          this.users = this.users.filter(u => u.id !== id);
          this.cdr.detectChanges();
          setTimeout(() => {
            this.loadUsers();
            this.loadDeletedUsers();
          }, 300);
        },
        error: () => this.error = 'Errore nella eliminazione utente'
      });
    }
  }
}
