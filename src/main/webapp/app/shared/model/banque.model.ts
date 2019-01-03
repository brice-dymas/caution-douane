export interface IBanque {
    id?: number;
    numeroBanque?: string;
    nomBanque?: string;
    numeroCompte?: string;
}

export class Banque implements IBanque {
    constructor(public id?: number, public numeroBanque?: string, public nomBanque?: string, public numeroCompte?: string) {}
}
