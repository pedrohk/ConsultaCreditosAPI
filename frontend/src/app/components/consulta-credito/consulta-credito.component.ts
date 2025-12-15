import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreditoService } from '../../services/credito/credito.service';
import { Credito } from '../../models/credito.model';

@Component({
  standalone: true,
  selector: 'app-consulta-credito',
  templateUrl: './consulta-credito.component.html',
  styleUrls: ['./consulta-credito.component.css'],
  imports: [CommonModule, FormsModule]
})
export class ConsultaCreditoComponent implements OnInit {
  searchType: 'numeroCredito' | 'numeroNfse' = 'numeroCredito';
  searchValue = '';
  creditoResultado: Credito[] | null = null;
  erro: string | null = null;

  constructor(private creditoService: CreditoService) {}

  ngOnInit(): void {}

  consultar(): void {
    this.erro = null;
    this.creditoResultado = null;

    if (!this.searchValue?.trim()) {
      this.erro = 'Informe um valor para pesquisa.';
      return;
    }

    if (this.searchType === 'numeroCredito') {
      this.creditoService.consultarPorNumeroCredito(this.searchValue.trim()).subscribe({
        next: (res: any) => {
          this.creditoResultado = Array.isArray(res) ? res : (res ? [res] : []);
          if (!this.creditoResultado || this.creditoResultado.length === 0) {
            this.erro = 'Nenhum crédito encontrado.';
          }
        },
        error: () => this.erro = 'Erro ao consultar por número do crédito.'
      });
    } else {
      this.creditoService.consultarPorNumeroNfse(this.searchValue.trim()).subscribe({
        next: (res: any) => {
          this.creditoResultado = Array.isArray(res) ? res : (res ? [res] : []);
          if (!this.creditoResultado || this.creditoResultado.length === 0) {
            this.erro = 'Nenhum crédito encontrado.';
          }
        },
        error: () => this.erro = 'Erro ao consultar por número da NFSe.'
      });
    }
  }
}
