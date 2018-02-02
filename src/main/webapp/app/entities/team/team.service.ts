import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Team } from './team.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TeamService {

    private resourceUrl =  SERVER_API_URL + 'api/teams';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(team: Team): Observable<Team> {
        const copy = this.convert(team);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(team: Team): Observable<Team> {
        const copy = this.convert(team);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Team> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any, resourceUrl?: string): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        resourceUrl = resourceUrl != null ? resourceUrl : this.resourceUrl;
        return this.http.get(resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Team.
     */
    private convertItemFromServer(json: any): Team {
        const entity: Team = Object.assign(new Team(), json);
        entity.createdDate = this.dateUtils
            .convertDateTimeFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a Team to a JSON which can be sent to the server.
     */
    private convert(team: Team): Team {
        const copy: Team = Object.assign({}, team);

        copy.createdDate = this.dateUtils.toDate(team.createdDate);
        return copy;
    }
}
