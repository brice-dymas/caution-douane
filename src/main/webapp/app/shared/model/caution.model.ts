import { IBanque } from 'app/shared/model//banque.model';

export interface ICaution {
    id?: number;
    numeroCaution?: string;
    numeroDossier?: string;
    reference?: string;
    montant?: number;
    banque?: IBanque;
}

export class Caution implements ICaution {
    constructor(
        public id?: number,
        public numeroCaution?: string,
        public numeroDossier?: string,
        public reference?: string,
        public montant?: number,
        public banque?: IBanque
    ) {}
}
