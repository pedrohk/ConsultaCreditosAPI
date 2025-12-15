import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet
  ],
  template: `
    <header style="background-color: #3f51b5; color: white; padding: 15px; text-align: center;">
      <h1>Consulta de Créditos</h1>
    </header>
    <main style="padding: 20px;">
      <router-outlet></router-outlet>
    </main>
    <footer style="text-align: center; padding: 10px; font-size: 0.8em; color: #777;">
      <p>&copy;</p>
    </footer>
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ConsultaCreditosFrontend';
}

