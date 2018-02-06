import { BaseEntity } from './../../shared';

export class UserInfo implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public callingName?: string,
        public imageUrl?: string,
        public imageContentType?: string,
        public image?: any,
        public phone?: string,
        public userId?: number,
        public supervisorId?: number,
        public designationId?: number,
        public designationDesignation?: string,
    ) {
    }
}
