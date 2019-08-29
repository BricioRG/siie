import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEscolaridad } from 'app/shared/model/escolaridad.model';

type EntityResponseType = HttpResponse<IEscolaridad>;
type EntityArrayResponseType = HttpResponse<IEscolaridad[]>;

@Injectable({ providedIn: 'root' })
export class EscolaridadService {
  public resourceUrl = SERVER_API_URL + 'api/escolaridads';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/escolaridads';

  constructor(protected http: HttpClient) {}

  create(escolaridad: IEscolaridad): Observable<EntityResponseType> {
    return this.http.post<IEscolaridad>(this.resourceUrl, escolaridad, { observe: 'response' });
  }

  update(escolaridad: IEscolaridad): Observable<EntityResponseType> {
    return this.http.put<IEscolaridad>(this.resourceUrl, escolaridad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEscolaridad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscolaridad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscolaridad[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
