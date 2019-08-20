/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { EscuelaDetailComponent } from 'app/entities/escuela/escuela-detail.component';
import { Escuela } from 'app/shared/model/escuela.model';

describe('Component Tests', () => {
  describe('Escuela Management Detail Component', () => {
    let comp: EscuelaDetailComponent;
    let fixture: ComponentFixture<EscuelaDetailComponent>;
    const route = ({ data: of({ escuela: new Escuela(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [EscuelaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EscuelaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EscuelaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.escuela).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
