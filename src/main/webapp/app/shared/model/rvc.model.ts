import { Moment } from 'moment';
import { IDemandeImportation } from 'app/shared/model//demande-importation.model';

export interface IRVC {
    id?: number;
    numeroRVC?: string;
    montantRVC?: number;
    numeroNavire?: string;
    nomNavire?: string;
    paysProvenance?: string;
    heureDepart?: Moment;
    heureArrivee?: Moment;
    demandeImportation?: IDemandeImportation;
}

export class RVC implements IRVC {
    constructor(
        public id?: number,
        public numeroRVC?: string,
        public montantRVC?: number,
        public numeroNavire?: string,
        public nomNavire?: string,
        public paysProvenance?: string,
        public heureDepart?: Moment,
        public heureArrivee?: Moment,
        public demandeImportation?: IDemandeImportation
    ) {}
}
