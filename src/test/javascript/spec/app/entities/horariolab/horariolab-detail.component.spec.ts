/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { HorariolabDetailComponent } from 'app/entities/horariolab/horariolab-detail.component';
import { Horariolab } from 'app/shared/model/horariolab.model';

describe('Component Tests', () => {
  describe('Horariolab Management Detail Component', () => {
    let comp: HorariolabDetailComponent;
    let fixture: ComponentFixture<HorariolabDetailComponent>;
    const route = ({ data: of({ horariolab: new Horariolab(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [HorariolabDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HorariolabDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HorariolabDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.horariolab).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
