import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFinanciamiento } from 'app/shared/model/financiamiento.model';

type EntityResponseType = HttpResponse<IFinanciamiento>;
type EntityArrayResponseType = HttpResponse<IFinanciamiento[]>;

@Injectable({ providedIn: 'root' })
export class FinanciamientoService {
  public resourceUrl = SERVER_API_URL + 'api/financiamientos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/financiamientos';

  constructor(protected http: HttpClient) {}

  create(financiamiento: IFinanciamiento): Observable<EntityResponseType> {
    return this.http.post<IFinanciamiento>(this.resourceUrl, financiamiento, { observe: 'response' });
  }

  update(financiamiento: IFinanciamiento): Observable<EntityResponseType> {
    return this.http.put<IFinanciamiento>(this.resourceUrl, financiamiento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFinanciamiento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFinanciamiento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFinanciamiento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
