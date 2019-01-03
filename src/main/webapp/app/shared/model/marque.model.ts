export interface IMarque {
    id?: number;
    libelle?: string;
}

export class Marque implements IMarque {
    constructor(public id?: number, public libelle?: string) {}
}
