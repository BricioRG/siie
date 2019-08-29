import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEscuela } from 'app/shared/model/escuela.model';

type EntityResponseType = HttpResponse<IEscuela>;
type EntityArrayResponseType = HttpResponse<IEscuela[]>;

@Injectable({ providedIn: 'root' })
export class EscuelaService {
  public resourceUrl = SERVER_API_URL + 'api/escuelas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/escuelas';

  constructor(protected http: HttpClient) {}

  create(escuela: IEscuela): Observable<EntityResponseType> {
    return this.http.post<IEscuela>(this.resourceUrl, escuela, { observe: 'response' });
  }

  update(escuela: IEscuela): Observable<EntityResponseType> {
    return this.http.put<IEscuela>(this.resourceUrl, escuela, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEscuela>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscuela[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscuela[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
