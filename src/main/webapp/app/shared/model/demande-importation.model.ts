import { Moment } from 'moment';

export interface IDemandeImportation {
    id?: number;
    numeroDordre?: string;
    numeroFactureProforma?: string;
    dateReceptionFacture?: Moment;
    dateDepotFacture?: Moment;
    numeroPR?: string;
    dateReceptionPR?: Moment;
    numeroDI?: string;
    montantFOB?: number;
    montantFRET?: number;
    observation?: string;
}

export class DemandeImportation implements IDemandeImportation {
    constructor(
        public id?: number,
        public numeroDordre?: string,
        public numeroFactureProforma?: string,
        public dateReceptionFacture?: Moment,
        public dateDepotFacture?: Moment,
        public numeroPR?: string,
        public dateReceptionPR?: Moment,
        public numeroDI?: string,
        public montantFOB?: number,
        public montantFRET?: number,
        public observation?: string
    ) {}
}
