/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { FinanciamientoDetailComponent } from 'app/entities/financiamiento/financiamiento-detail.component';
import { Financiamiento } from 'app/shared/model/financiamiento.model';

describe('Component Tests', () => {
  describe('Financiamiento Management Detail Component', () => {
    let comp: FinanciamientoDetailComponent;
    let fixture: ComponentFixture<FinanciamientoDetailComponent>;
    const route = ({ data: of({ financiamiento: new Financiamiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [FinanciamientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FinanciamientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FinanciamientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.financiamiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
