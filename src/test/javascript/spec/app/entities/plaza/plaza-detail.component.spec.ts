/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { PlazaDetailComponent } from 'app/entities/plaza/plaza-detail.component';
import { Plaza } from 'app/shared/model/plaza.model';

describe('Component Tests', () => {
  describe('Plaza Management Detail Component', () => {
    let comp: PlazaDetailComponent;
    let fixture: ComponentFixture<PlazaDetailComponent>;
    const route = ({ data: of({ plaza: new Plaza(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [PlazaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlazaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlazaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plaza).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
