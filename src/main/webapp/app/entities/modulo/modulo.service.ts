import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModulo } from 'app/shared/model/modulo.model';

type EntityResponseType = HttpResponse<IModulo>;
type EntityArrayResponseType = HttpResponse<IModulo[]>;

@Injectable({ providedIn: 'root' })
export class ModuloService {
  public resourceUrl = SERVER_API_URL + 'api/modulos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/modulos';

  constructor(protected http: HttpClient) {}

  create(modulo: IModulo): Observable<EntityResponseType> {
    return this.http.post<IModulo>(this.resourceUrl, modulo, { observe: 'response' });
  }

  update(modulo: IModulo): Observable<EntityResponseType> {
    return this.http.put<IModulo>(this.resourceUrl, modulo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModulo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModulo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModulo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
