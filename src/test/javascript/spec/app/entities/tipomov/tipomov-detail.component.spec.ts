/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { TipomovDetailComponent } from 'app/entities/tipomov/tipomov-detail.component';
import { Tipomov } from 'app/shared/model/tipomov.model';

describe('Component Tests', () => {
  describe('Tipomov Management Detail Component', () => {
    let comp: TipomovDetailComponent;
    let fixture: ComponentFixture<TipomovDetailComponent>;
    const route = ({ data: of({ tipomov: new Tipomov(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [TipomovDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipomovDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipomovDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipomov).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
