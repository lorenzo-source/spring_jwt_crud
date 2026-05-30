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
  template: `
    <div class="container">
      <div class="header">
        <h1>Gestione Utenti</h1>
        <a routerLink="/dashboard" class="btn-back">← Dashboard</a>
      </div>

      <div class="add-form">
        <h3>Aggiungi Utente</h3>
        <div class="form-row">
          <input type="text" placeholder="Nome" [(ngModel)]="newUser.nome" class="input"/>
          <input type="text" placeholder="Cognome" [(ngModel)]="newUser.cognome" class="input"/>
          <input type="email" placeholder="Email" [(ngModel)]="newUser.email" class="input"/>
          <input type="password" placeholder="Password" [(ngModel)]="newUser.password" class="input"/>
          <button (click)="createUser()" class="btn">Aggiungi</button>
        </div>
        <p class="error" *ngIf="error">{{ error }}</p>
        <p class="success" *ngIf="success">{{ success }}</p>
      </div>

      <table class="table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Cognome</th>
            <th>Email</th>
            <th>Creato il</th>
            <th>Ultimo login</th>
            <th>Azioni</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let user of users">
            <td>
              <span *ngIf="editingId !== user.id">{{ user.nome || '—' }}</span>
              <input *ngIf="editingId === user.id" [(ngModel)]="editUser.nome" class="input-small"/>
            </td>
            <td>
              <span *ngIf="editingId !== user.id">{{ user.cognome || '—' }}</span>
              <input *ngIf="editingId === user.id" [(ngModel)]="editUser.cognome" class="input-small"/>
            </td>
            <td>{{ user.email }}</td>
            <td>{{ user.dataCreazione | date:'dd/MM/yyyy HH:mm' }}</td>
            <td>{{ user.dataUltimoLogin ? (user.dataUltimoLogin | date:'dd/MM/yyyy HH:mm') : '—' }}</td>
            <td>
              <button *ngIf="editingId !== user.id" (click)="startEdit(user)" class="btn-edit">Modifica</button>
              <button *ngIf="editingId === user.id" (click)="saveEdit()" class="btn-save">Salva</button>
              <button *ngIf="editingId === user.id" (click)="cancelEdit()" class="btn-cancel">Annulla</button>
              <button (click)="deleteUser(user.id!)" class="btn-delete">Elimina</button>
            </td>
          </tr>
        </tbody>
      </table>

      <h2 style="color: #1a3a5c; margin-top: 40px;">Utenti Eliminati</h2>
      <table class="table" *ngIf="deletedUsers.length > 0">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Cognome</th>
            <th>Email</th>
            <th>Creato il</th>
            <th>Eliminato il</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let user of deletedUsers">
            <td>{{ user.nome || '—' }}</td>
            <td>{{ user.cognome || '—' }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.dataCreazione | date:'dd/MM/yyyy HH:mm' }}</td>
            <td style="color: #e74c3c;">{{ user.dataCancellazione | date:'dd/MM/yyyy HH:mm' }}</td>
          </tr>
        </tbody>
      </table>
      <p *ngIf="deletedUsers.length === 0" class="empty">Nessun utente eliminato.</p>

      <p *ngIf="users.length === 0" class="empty">Nessun utente trovato.</p>
    </div>
  `,
  styles: [`
    .container { padding: 40px; max-width: 1200px; margin: 0 auto; }
    .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
    h1 { color: #1a3a5c; }
    .btn-back { color: #1a3a5c; text-decoration: none; font-size: 14px; }
    .add-form { background: white; padding: 24px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 30px; }
    .add-form h3 { color: #1a3a5c; margin-bottom: 16px; }
    .form-row { display: flex; gap: 12px; flex-wrap: wrap; }
    .input { padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; flex: 1; min-width: 150px; }
    .input-small { padding: 4px 8px; border: 1px solid #ddd; border-radius: 4px; font-size: 13px; width: 100px; }
    .btn { padding: 8px 16px; background: #1a3a5c; color: white; border: none; border-radius: 4px; cursor: pointer; }
    .table { width: 100%; border-collapse: collapse; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    th { background: #1a3a5c; color: white; padding: 12px 16px; text-align: left; font-size: 14px; }
    td { padding: 12px 16px; border-bottom: 1px solid #eee; font-size: 14px; }
    tr:hover { background: #f9f9f9; }
    .btn-edit { padding: 4px 10px; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer; margin-right: 6px; font-size: 12px; }
    .btn-save { padding: 4px 10px; background: #27ae60; color: white; border: none; border-radius: 4px; cursor: pointer; margin-right: 6px; font-size: 12px; }
    .btn-cancel { padding: 4px 10px; background: #95a5a6; color: white; border: none; border-radius: 4px; cursor: pointer; margin-right: 6px; font-size: 12px; }
    .btn-delete { padding: 4px 10px; background: #e74c3c; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 12px; }
    .error { color: red; font-size: 14px; margin-top: 8px; }
    .success { color: green; font-size: 14px; margin-top: 8px; }
    .empty { text-align: center; color: #999; padding: 40px; }
  `]
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
