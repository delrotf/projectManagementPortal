import { BaseEntity } from './../../shared';

export class Team implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public imageUrl?: string,
        public imageContentType?: string,
        public image?: any,
        public processOrder?: boolean,
        public processExternalTask?: boolean,
        public active?: boolean,
        public createdDate?: any,
        public teamHeadId?: number,
    ) {
        this.processOrder = false;
        this.processExternalTask = false;
        this.active = false;
    }
}
