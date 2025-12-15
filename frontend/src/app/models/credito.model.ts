export interface Credito {
    numeroCredito: string;
    numeroNfse: string;
    dataConstituicao: string;
    valorIssqn: number;
    tipoCredito: string;
    simplesNacional: boolean;
    aliquota: number;
    valorFaturado: number;
    valorDeducao: number;
    baseCalculo: number;
}
