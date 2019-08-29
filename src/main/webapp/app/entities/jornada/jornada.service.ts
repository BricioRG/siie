import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJornada } from 'app/shared/model/jornada.model';

type EntityResponseType = HttpResponse<IJornada>;
type EntityArrayResponseType = HttpResponse<IJornada[]>;

@Injectable({ providedIn: 'root' })
export class JornadaService {
  public resourceUrl = SERVER_API_URL + 'api/jornadas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/jornadas';

  constructor(protected http: HttpClient) {}

  create(jornada: IJornada): Observable<EntityResponseType> {
    return this.http.post<IJornada>(this.resourceUrl, jornada, { observe: 'response' });
  }

  update(jornada: IJornada): Observable<EntityResponseType> {
    return this.http.put<IJornada>(this.resourceUrl, jornada, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJornada>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJornada[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJornada[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
